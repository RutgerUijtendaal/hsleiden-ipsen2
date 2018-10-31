package controllers;

import models.Right;
import views.AdminMenuView;
import views.BaseView;

public class AdminMenuController {

    AppController appCtl;
    AdminMenuView amv;

    public AdminMenuController(AppController appCtl) {
        this.appCtl = appCtl;
        amv = new AdminMenuView(this);
    }

    public BaseView getView() {
        return amv; // TODO willen we dit zo?
    }

    public void handleDilemmaBtnClick() {
        appCtl.switchToDilemmaListView();
    }

    public void handleAdminListBtnClick() {
        appCtl.switchToAdminListView();
    }

    public void handleStatisticBtnClick() {
        amv.displayError("STATISTICS UNDER CONSTRUCTION");
    }

    public void handleParentBtnClick() {
        appCtl.switchToCoupleListView();
    }

    public void handleBackBtnClick() {
        appCtl.switchToMainMenuView();
    }

    public void handleAddDilemmaBtnClick() { appCtl.switchToAddDilemmaView(); }

    public void handleAddAdminBtnClick() { appCtl.switchToAddAdminView(); }

    public void setRights(Right rights) {
        if(rights.isCanViewStatistics()) {
            amv.displayModeratorButtons();
        }

        if(rights.isCanEditDilemma()) {
            amv.displayAdminButtons();
        }
    }
}

