package controllers;

import daos.AnswerDao;
import daos.DaoManager;
import util.DilemmaSubmitData;
import service.ImageService;
import views.AddDilemmaView;
import views.BaseView;
import models.Dilemma;
import models.Answer;

import java.io.IOException;

public class AddDilemmaController {

    AppController appCtl;
    AddDilemmaView adv;
    ImageService imageService;
    DilemmaSubmitData dilemmaSubmitData;

    public AddDilemmaController(AppController appCtl) {
        this.appCtl = appCtl;
        adv = new AddDilemmaView(this);
        imageService = new ImageService();
    }

    public BaseView getView() { return adv; }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

    public void handleSubmitBtnClick(DilemmaSubmitData dsd) {
        dilemmaSubmitData = dsd;

        // If the dilemma has pictures upload them to web and save their url.
        if(dilemmaSubmitData.hasPictures) {
            if(!tryUploadPictures()) {
                adv.displayError("Fout tijdens het uploaden van plaatjes");
            }
        }

        if(!trySubmitDilemma()) {
            adv.displayError("Fout tijdens het opslaan van dilemma");
        }

        adv.displayPopup("Dilemma toegevoegd.");
    }

    public void fillFields(Dilemma dilemma) {
        AnswerDao answerDao = DaoManager.getAnswerDao();
        System.out.println(dilemma);
        Answer[] answers = answerDao.getByDilemmaId(dilemma.getId());
        adv.fillFields(dilemma, answers);
    }


    private boolean tryUploadPictures() {
        try {
            dilemmaSubmitData.setAOneUrl(imageService.saveAnswerImage(dilemmaSubmitData.getAOnePicture(), dilemmaSubmitData.getWeekNr(), "A"));
            dilemmaSubmitData.setATwoUrl(imageService.saveAnswerImage(dilemmaSubmitData.getATwoPicture(), dilemmaSubmitData.getWeekNr(), "B"));
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean trySubmitDilemma() {
        int dilemmaId = DaoManager.getDilemmaDao().save(dilemmaSubmitData.getDilemma());
        DaoManager.getAnswerDao().save(dilemmaSubmitData.getAnswerA(dilemmaId));
        DaoManager.getAnswerDao().save(dilemmaSubmitData.getAnswerB(dilemmaId));
        return true;
    }

}

