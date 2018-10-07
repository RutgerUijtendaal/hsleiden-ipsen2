package controllers;

import views.AdminMenuView;

import javafx.scene.Scene;
import javafx.scene.Parent;

public class AdminMenuController {

    AppController appCtl;
    AdminMenuView amv;

    public AdminMenuController(AppController appCtl) {
        this.appCtl = appCtl;
        amv = new AdminMenuView(this);
    }

    public Scene getViewScene() {
        return amv.getViewScene(); // TODO willen we dit zo?
    }

    public void handleDilemmaBtnClick() {
        amv.displayError("DILEMMA'S UNDER CONSTRUCTION");
    }

    public void handleStatisticBtnClick() {
        amv.displayError("STATISTICS UNDER CONSTRUCTION");
    }

    public void handleBackBtnClick() {
        appCtl.switchToMainMenuView();
    }
}

