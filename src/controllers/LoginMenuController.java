package controllers;

import views.BaseView;
import views.LoginMenuView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(mailingAdres);
        if (matcher.find()) {
            appCtl.sendMail(mailingAdres, "Test", "Test");
        } else {
            lmv.displayError("Geen geldig email adres");
        }
    }

}
