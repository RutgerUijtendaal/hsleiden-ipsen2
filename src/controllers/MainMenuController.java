package controllers;

import views.BaseView;
import views.MainMenuView;

/**
 * Basic menu class that allows for switching to different
 * view after starting the application
 *
 * @author Jordi Dorren
 */
public class MainMenuController {
    
    private final AppController appController;
    private final MainMenuView mainMenuView;

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

