package controllers;

import views.AdminMenuView;

import javafx.scene.Scene;
import javafx.scene.Parent;
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
        amv.displayError("DILEMMA'S UNDER CONSTRUCTION");
        appCtl.switchToDilemmaListView();
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
}

