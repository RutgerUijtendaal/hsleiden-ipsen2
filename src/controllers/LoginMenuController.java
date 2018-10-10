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

    public void handleSubmitBtnClick(String mailingAdres) {
        lmv.displayError("SUBMITTING UNDER CONSTRUCTION");
        //TODO proper subject and content
        appCtl.sendMail(mailingAdres, "Test", "Test");
    }

}
