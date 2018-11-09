package controllers;

import service.ImageService;
import util.DilemmaSubmitData;
import views.AddEditDilemmaView;

import java.io.IOException;

/**
 * The abstract super-class of AddDilemmaController and EditDilemmaController
 * houses methods that both these controllers use
 *
 * @author Rutger Uijtendaal
 * @author Jordi Dorren
 */
public abstract class DilemmaController {

    DilemmaSubmitData dilemmaSubmitData;

    final AppController appController;
    AddEditDilemmaView addEditDilemmaView;
    final ImageService imageService;

    DilemmaController(AppController appController) {
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

    /**
     * Tries to upload pictures to the Apache Webserver
     *
     * @see service.ImageService#saveAnswerImage(java.io.File, int)
     * @returns true if it has succefully uploaded both pictures
     */
    boolean tryUploadPictures() {
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
