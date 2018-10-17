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

    DilemmaSubmitData dilemmaSubmitData;

    public AddDilemmaController(AppController appCtl) {
        super(appCtl);
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

    @Override
    public void handleSubmitBtnClick(DilemmaSubmitData dsd) {
        System.out.println("click from: " + this);
        
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

        appCtl.switchToAdminMenuView();

        appCtl.getActiveView().displayPopup("Dilemma toegevoegd.");

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

