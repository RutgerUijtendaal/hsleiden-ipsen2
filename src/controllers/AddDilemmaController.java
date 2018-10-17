package controllers;

import daos.AnswerDao;
import daos.DaoManager;
import util.DilemmaSubmitData;
import service.ImageService;
import views.AddEditDilemmaView;
import views.BaseView;
import models.Dilemma;
import models.Answer;

import java.io.IOException;

public class AddDilemmaController extends DilemmaController {

    ImageService imageService;
    DilemmaSubmitData dilemmaSubmitData;

    public AddDilemmaController(AppController appCtl) {
        super(appCtl);
        imageService = new ImageService();
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

    public void handleSubmitBtnClick(DilemmaSubmitData dsd) {
        dilemmaSubmitData = dsd;

        // If a dilemma for weekNr already exists
        if(DaoManager.getDilemmaDao().dilemmaExists(Short.parseShort(dsd.getWeekNr()))) {
            aedv.displayError("Dilemma van week " + dsd.getWeekNr() + " bestaat al.");
            return;
        }

        if(!trySubmitDilemma()) {
            aedv.displayError("Fout tijdens het opslaan van dilemma");
            return;
        }

        // If the dilemma has pictures upload them to web and save their url.
        if(dilemmaSubmitData.hasPictures) {
            if(!tryUploadPictures()) {
                aedv.displayError("Fout tijdens het uploaden van plaatjes");
                return;
            }
        }

        aedv.displayPopup("Dilemma toegevoegd.");
    }

    public void fillFields(Dilemma dilemma) {
        AnswerDao answerDao = DaoManager.getAnswerDao();
        System.out.println(dilemma);
        Answer[] answers = answerDao.getByDilemmaId(dilemma.getId());
        aedv.fillFields(dilemma, answers);
    }


    private boolean trySubmitDilemma() {
        int dilemmaId = DaoManager.getDilemmaDao().save(dilemmaSubmitData.getDilemma());
        int answerOneId = DaoManager.getAnswerDao().save(dilemmaSubmitData.getAnswerA(dilemmaId));
        int answerTwoId = DaoManager.getAnswerDao().save(dilemmaSubmitData.getAnswerB(dilemmaId));

        // Save the ID in submit data to use with possible image uploads
        dilemmaSubmitData.setaOneId(answerOneId);
        dilemmaSubmitData.setaTwoId(answerTwoId);

        return true;
    }

    private boolean tryUploadPictures() {
        try {
            imageService.saveAnswerImage(dilemmaSubmitData.getAOnePicture(), dilemmaSubmitData.getaOneId());
            imageService.saveAnswerImage(dilemmaSubmitData.getATwoPicture(), dilemmaSubmitData.getaTwoId());
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

}

