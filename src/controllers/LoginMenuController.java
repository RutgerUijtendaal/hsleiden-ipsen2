package controllers;

import javafx.scene.Scene;
import views.LoginMenuView;

public class LoginMenuController {

    AppController appCtl;
    LoginMenuView lmv;

    public LoginMenuController(AppController appCtl) {
        this.appCtl = appCtl;
        this.lmv = new LoginMenuView(this);
    }

    public Scene getViewScene() {
        return this.lmv.getViewScene();
    }

    public void handleBackBtnClick() {
        appCtl.switchToMainMenuView();
    }

    public void handleSubmitBtnClick() {
        lmv.displayError("SUBMITTING UNDER CONSTRUCTION");
    }
}
