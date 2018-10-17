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

    public AddDilemmaController(AppController appCtl) {
        super(appCtl);
    }

    @Override
    public void handleSubmitBtnClick(DilemmaSubmitData dilemmaSubmitData) {
        System.out.println("click from:  " + this);
    }

}

    /*
    AppController appCtl;
    AddDilemmaView adv;
    ImageService imageService;

    public AddDilemmaController(AppController appCtl) {
        this.appCtl = appCtl;
        adv = new AddDilemmaView(this);
        imageService = new ImageService();
    }


    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

    public void handleSubmitBtnClick(DilemmaSubmitData dilemmaSubmitData) {
        if (submitDilemma(dilemmaSubmitData, false)) {
            appCtl.switchToAdminMenuView();
            appCtl.getActiveView().displayPopup("Dilemma toegevoegd");
        } else {
            adv.displayError("Fout tijdens het toevoegen van dilemma");
        }

    }

    public void handleAlterBtnClick(DilemmaSubmitData dilemmaSubmitData) {
        if (submitDilemma(dilemmaSubmitData, true)) {
            appCtl.switchToAdminMenuView();
            appCtl.getActiveView().displayPopup("Dilemma aangepast");
        } else {
            adv.displayError("Fout tijdens het aanpassen van dilemma");
        }

    }

    public void fillFields(Dilemma dilemma) {
        AnswerDao answerDao = DaoManager.getAnswerDao();
        System.out.println(dilemma);
        Answer[] answers = answerDao.getByDilemmaId(dilemma.getId());
        adv.fillFields(dilemma, answers);
    }

    private boolean submitDilemma(DilemmaSubmitData dilemmaSubmitData, boolean updating) {
        // Set the image URLs to null. If no images are set null is entered into the database.
        String imageOneUrl = null;
        String imageTwoUrl = null;

        // If the dilemma has pictures upload them to web and save their url.
        if (dilemmaSubmitData.hasPictures) {
            try {
                imageOneUrl = imageService.saveAnswerImage(dilemmaSubmitData.getAOnePicture(), dilemmaSubmitData.getWeekNr(), "A");
                imageTwoUrl = imageService.saveAnswerImage(dilemmaSubmitData.getATwoPicture(), dilemmaSubmitData.getWeekNr(), "B");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            return false;
        }

        int dilemmaId;
        if (updating) {
            dilemmaId = dilemmaSubmitData.getDilemmaId();
            DaoManager.getDilemmaDao().update(dilemmaSubmitData.getDilemma());
            System.out.println("nigga we updating dilemma");
            DaoManager.getAnswerDao().update(dilemmaSubmitData.getAnswerA(dilemmaId, imageOneUrl));
            System.out.println("nigga we updating answer1");
            DaoManager.getAnswerDao().update(dilemmaSubmitData.getAnswerB(dilemmaId, imageTwoUrl));
            System.out.println("nigga we updating answer2");
        } else {
            dilemmaId = DaoManager.getDilemmaDao().save(dilemmaSubmitData.getDilemma());
            DaoManager.getAnswerDao().save(dilemmaSubmitData.getAnswerA(dilemmaId, imageOneUrl));
            DaoManager.getAnswerDao().save(dilemmaSubmitData.getAnswerB(dilemmaId, imageTwoUrl));
        }
	
        return true;
    }

    public void switchToCreateMode() {
        adv.clearFields();
        adv.switchToCreateButton();
    }

    public void switchToAlterMode(Dilemma dilemma) {
        adv.clearFields();
        AnswerDao answerDao = DaoManager.getAnswerDao();
        System.out.println(dilemma);
        Answer[] answers = answerDao.getByDilemmaId(dilemma.getId());
        adv.switchToAlterButton();
        adv.fillFields(dilemma, answers);
    }

    */

