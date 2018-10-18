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
        processAdminRights();
    }

    public BaseView getView() {
        return amv; // TODO willen we dit zo?
    }

    public void handleDilemmaBtnClick() {
        appCtl.switchToDilemmaListView();
    }

    public void handleStatisticBtnClick() {
        appCtl.switchToStatisticsView();
    }

    public void handleParentBtnClick() {
        appCtl.switchToCoupleListView();
    }

    public void handleBackBtnClick() {
        appCtl.switchToMainMenuView();
    }

    public void handleAddDilemmaBtnClick() { appCtl.switchToAddDilemmaView(); }

    public void handleAddAdminBtnClick() { appCtl.switchToAddAdminView(); }

    private void processAdminRights() {
        if(appCtl.getRights().isCanViewStatistics()) {
            amv.displayModeratorButtons();
        }

        if(appCtl.getRights().isCanEditDilemma()) {
            amv.displayAdminButtons();
        }
    }
}

