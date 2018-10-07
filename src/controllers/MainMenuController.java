package controllers;

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

    public Scene getViewScene() {
        return mmv.getViewScene(); // TODO willen we dit zo?
    }

    public void handleParentBtnClick() {
        mmv.displayPopup("OUDER UNDER CONSTRUCTION");
    }

    public void handleAdminBtnClick() {
        appCtl.switchToAdminMenuView();
    }

    public void handleSignupBtnClick() {
        appCtl.switchToAddCoupleView();
    }
}

