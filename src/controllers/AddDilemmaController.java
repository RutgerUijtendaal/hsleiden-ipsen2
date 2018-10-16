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

    public AddDilemmaController(AppController appCtl) {
        this.appCtl = appCtl;
        adv = new AddDilemmaView(this);
        imageService = new ImageService();
    }

    public BaseView getView() { return adv; }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

    public void handleSubmitBtnClick(DilemmaSubmitData dilemmaSubmitData) {
        if(submitDilemma(dilemmaSubmitData)) {
            adv.displayPopup("Dilemma toegevoegd");
            appCtl.switchToAdminMenuView();
        } else {
            adv.displayError("Fout tijdens het toevoegen van dilemma");
        }

    }

    public void fillFields(Dilemma dilemma) {
        AnswerDao answerDao = DaoManager.getAnswerDao();
        System.out.println(dilemma);
        Answer[] answers = answerDao.getByDilemmaId(dilemma.getId());
        adv.fillFields(dilemma, answers);
    }

    private boolean submitDilemma(DilemmaSubmitData dilemmaSubmitData) {
        // Set the image URLs to null. If no images are set null is entered into the database.
        String imageOneUrl = null;
        String imageTwoUrl = null;

        // If the dilemma has pictures upload them to web and save their url.
        if(dilemmaSubmitData.hasPictures) {
            try {
                imageOneUrl = imageService.saveAnswerImage(dilemmaSubmitData.getAOnePicture(), dilemmaSubmitData.getWeekNr(), "A");
                imageTwoUrl = imageService.saveAnswerImage(dilemmaSubmitData.getATwoPicture(), dilemmaSubmitData.getWeekNr(), "B");
            } catch (IOException exception) {
                exception.printStackTrace();
            } finally {
                return false;
            }
        }

        int dilemmaId = DaoManager.getDilemmaDao().save(dilemmaSubmitData.getDilemma());
        DaoManager.getAnswerDao().save(dilemmaSubmitData.getAnswerA(dilemmaId, imageOneUrl));
        DaoManager.getAnswerDao().save(dilemmaSubmitData.getAnswerB(dilemmaId, imageTwoUrl));
	
	return true;
    }

    public void switchToCreateMode() {
        adv.clearFields();
        adv.switchToCreateButton();
    }

    public void switchToAlterMode(Dilemma dilemma) {
        AnswerDao answerDao = DaoManager.getAnswerDao();
        System.out.println(dilemma);
        Answer[] answers = answerDao.getByDilemmaId(dilemma.getId());
        adv.switchToAlterButton();
        adv.fillFields(dilemma, answers);
    }

}

