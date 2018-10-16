package controllers;

import daos.DaoManager;
import util.DilemmaSubmitData;
import service.ImageService;
import views.AddDilemmaView;
import views.BaseView;

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
                adv.displayError("Niet gelukt om plaatjes te uploaden");
            }
        }

        DaoManager.getDilemmaDao().save(dilemmaSubmitData.getDilemma());
        // TODO getDilemmaId
        DaoManager.getAnswerDao().save(dilemmaSubmitData.getAnswerA(123, imageOneUrl));
        DaoManager.getAnswerDao().save(dilemmaSubmitData.getAnswerB(123, imageTwoUrl));

    }

}

