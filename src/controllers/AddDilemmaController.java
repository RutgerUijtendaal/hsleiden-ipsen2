package controllers;

import daos.DaoManager;
import util.DilemmaSubmitData;
import util.ImageService;
import views.AddDilemmaView;
import views.BaseView;

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
        String imageOneUrl = null;
        String imageTwoUrl = null;

        if(dilemmaSubmitData.hasPictures) {
            imageOneUrl = imageService.saveAnswerImage(dilemmaSubmitData.getAOnePicture(), dilemmaSubmitData.getWeekNr(), "A");
            imageTwoUrl =imageService.saveAnswerImage(dilemmaSubmitData.getATwoPicture(), dilemmaSubmitData.getWeekNr(), "B");
        }

        DaoManager.getDilemmaDao().save(dilemmaSubmitData.getDilemma());
        // TODO getDilemmaId
        DaoManager.getAnswerDao().save(dilemmaSubmitData.getAnswerA(123, imageOneUrl));
        DaoManager.getAnswerDao().save(dilemmaSubmitData.getAnswerA(456, imageTwoUrl));

    }

}

