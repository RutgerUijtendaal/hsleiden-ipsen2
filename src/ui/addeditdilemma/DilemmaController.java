package ui.addeditdilemma;

import service.ImageService;
import ui.AppController;
import ui.addeditdilemma.AddEditDilemmaView;
import models.submitdata.DilemmaSubmitData;

import java.io.IOException;

public abstract class DilemmaController {

    protected DilemmaSubmitData dilemmaSubmitData;

    protected AppController appCtl;
    protected AddEditDilemmaView aedv;

    public DilemmaController(AppController appCtl) {
        this.appCtl = appCtl;
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
            ImageService.saveAnswerImage(dilemmaSubmitData.getAOnePicture(), dilemmaSubmitData.getaOneId());
            ImageService.saveAnswerImage(dilemmaSubmitData.getATwoPicture(), dilemmaSubmitData.getaTwoId());
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }
}
