package controllers;

import daos.*;
import exceptions.ReadFromResultSetException;
import models.Child;
import models.Couple;
import models.Parent;
import models.Result;
import views.BaseView;
import views.LoginMenuView;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuController {

    AppController appCtl;
    LoginMenuView lmv;

    ParentDao parentDao = DaoManager.getParentDao();
    CoupleDao coupleDao = DaoManager.getCoupleDao();
    ChildDao childDao = DaoManager.getChildDao();
    ResultDao resultDao = DaoManager.getResultDao();

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

    public void handleSubmitBtnClick(String email) {
        //TODO proper subject and content
        Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email);
        if (matcher.find()) {
            try {
                Parent parent = parentDao.getByEmail(email);
                Couple couple = coupleDao.getByParent(parent);
                Child child = childDao.getByCouple(couple);

                appCtl.switchToAnswerDilemmaView(parent, couple, child);
                appCtl.sendMail(email, "Test", "Test");

                makeNewResultRecord(parent);
            } catch (ReadFromResultSetException exception) {
                lmv.displayPopup("Inloggen mislukt");
            }
        } else {
            lmv.displayError("Geen geldig email adres");
        }
    }

    private void makeNewResultRecord(Parent parent){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Result result = new Result(parent.getId(), null, timestamp, null);
        resultDao.save(result);
    }
}
