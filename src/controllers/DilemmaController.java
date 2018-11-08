package controllers;

import service.ImageService;
import util.DilemmaSubmitData;
import views.AddEditDilemmaView;

import java.io.IOException;

public abstract class DilemmaController {

    protected DilemmaSubmitData dilemmaSubmitData;

    protected AppController appController;
    protected AddEditDilemmaView addEditDilemmaView;
    protected ImageService imageService;

    public DilemmaController(AppController appController) {
        this.appController = appController;
        this.imageService = new ImageService();
    }

    public void clearFields() {
        addEditDilemmaView.clearFields();
    }

    public AddEditDilemmaView getView() { 
        return addEditDilemmaView;
    }

    public void setView(AddEditDilemmaView aedv) {
        this.addEditDilemmaView = aedv;
    }

    public void createView() {
        if (addEditDilemmaView == null) {
            addEditDilemmaView = new AddEditDilemmaView(this);
        }
    }

    public void handleBackBtnClick() {
        appController.switchToAdminMenuView();
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
