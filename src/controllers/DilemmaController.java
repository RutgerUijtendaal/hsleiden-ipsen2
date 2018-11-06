package controllers;

import service.ImageService;
import util.DilemmaSubmitData;
import views.AddEditDilemmaView;

import java.io.IOException;

public abstract class DilemmaController {

    protected DilemmaSubmitData dilemmaSubmitData;

    protected AppController appCtl;
    protected AddEditDilemmaView aedv;
    protected ImageService imageService;

    public DilemmaController(AppController appCtl) {
        this.appCtl = appCtl;
        this.imageService = new ImageService();
    }

    public void clearFields() {
        aedv.clearFields();
    }

    public AddEditDilemmaView getView() { 
        return aedv;
    }

    public void setView(AddEditDilemmaView aedv) {
        this.aedv = aedv;
    }

    public void createView() {
        if (aedv == null) {
            aedv = new AddEditDilemmaView(this);
        }
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

    public abstract void handleSubmitBtnClick(DilemmaSubmitData dilemmaSubmitData);

    protected boolean tryUploadPictures() {
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
