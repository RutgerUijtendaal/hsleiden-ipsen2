package controllers;

import views.BaseView;
import views.MainMenuView;

public class MainMenuController {
    
    AppController appController;
    MainMenuView mainMenuView;

    public MainMenuController(AppController appController) {
        this.appController = appController;
        mainMenuView = new MainMenuView(this);
    }

    public BaseView getView() {
        return mainMenuView;
    }

    public void handleParentBtnClick() {
        appController.switchToLoginView();
    }

    public void handleAdminBtnClick() {
        appController.switchToAdminLoginView();
    }

    public void handleSignupBtnClick() {
        appController.switchToAddCoupleView();
    }

    public void handleShutdownBtnClick() {
        appController.shutdown();
    }
}

