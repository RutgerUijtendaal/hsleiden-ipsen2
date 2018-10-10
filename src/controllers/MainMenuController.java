package controllers;

import views.BaseView;
import views.MainMenuView;

import javafx.scene.Scene;
import javafx.scene.Parent;

public class MainMenuController {
    
    AppController appCtl;
    MainMenuView mmv;

    public MainMenuController(AppController appCtl) {
        this.appCtl = appCtl;
        mmv = new MainMenuView(this);
    }

    public BaseView getView() {
        return mmv;
    }

    public void handleParentBtnClick() {
        appCtl.switchToLoginView();
    }

    public void handleAdminBtnClick() {
        appCtl.switchToAdminMenuView();
    }

    public void handleSignupBtnClick() {
        appCtl.switchToAddCoupleView();
    }

    public void handleShutdownBtnClick() {
        appCtl.shutdown();
    }
}

