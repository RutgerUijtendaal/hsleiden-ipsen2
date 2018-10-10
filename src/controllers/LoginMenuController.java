package controllers;

import views.BaseView;
import views.LoginMenuView;

public class LoginMenuController {

    AppController appCtl;
    LoginMenuView lmv;

    public LoginMenuController(AppController appCtl) {
        this.appCtl = appCtl;
        this.lmv = new LoginMenuView(this);
    }

    public BaseView getView() {
        return this.lmv;
    }

    public void handleBackBtnClick() {
        appCtl.switchToMainMenuView();
    }

    public void handleSubmitBtnClick() {
        lmv.displayError("SUBMITTING UNDER CONSTRUCTION");
        appCtl.sendMail("dubiogroep9@gmail.com", "Test", "Test");
    }

}
