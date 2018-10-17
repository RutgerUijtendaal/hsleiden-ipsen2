package controllers;

import service.ImageService;
import views.AddEditDilemmaView;
import views.BaseView;
import controllers.AppController;
import util.DilemmaSubmitData;

public abstract class DilemmaController {

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
}
