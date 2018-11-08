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

/**
 * Handles the login logic for logging in parents
 * to allow them to answer a dilemma based on their
 * profile in the system
 *
 * @author Stefan de Keijzer
 */
public class LoginMenuController {

    private AppController appController;
    private LoginMenuView loginMenuView;

    private ParentDao parentDao = DaoManager.getParentDao();
    private CoupleDao coupleDao = DaoManager.getCoupleDao();
    private ChildDao childDao = DaoManager.getChildDao();
    private ResultDao resultDao = DaoManager.getResultDao();

    public LoginMenuController(AppController appController) {
        this.appController = appController;
        this.loginMenuView = new LoginMenuView(this);
    }

    public BaseView getView() {
        return this.loginMenuView;
    }

    public void handleBackBtnClick() {
        appController.switchToMainMenuView();
    }

    /**
     * Tries to do the logging in followed by
     * sending an email to the user telling
     * him or her that there is a new dilemma waiting
     * for them
     *
     * @param String the email adress to which the mail needs to be sent
     * @see daos.ParentDao#getByEmail()
     * @see daos.CoupleDao#getByParent()
     * @see daos.ChildDao#getByCouple()
     * @see controllers.AppController#sendMail()
     * @see controllers.LoginMenuController#makeNewResultRecord()
     */
    public void handleSubmitBtnClick(String email) {
        //TODO proper subject and content
        Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email);
        if (matcher.find()) {
            try {
                models.Parent parent = parentDao.getByEmail(email);
                Couple couple = coupleDao.getByParent(parent);
                Child child = childDao.getByCouple(couple);

                appController.switchToAnswerDilemmaView(parent, couple, child);
                appController.sendMail(email, "Test", "Test");

                makeNewResultRecord(parent);
            } catch (ReadFromResultSetException exception) {
                loginMenuView.displayPopup("Inloggen mislukt");
            }
        } else {
            loginMenuView.displayError("Geen geldig email adres");
        }
    }

    /**
     * Creates the initial result that only contains the timestamp
     * when the dilemma was opened plus who opened it
     *
     * @param Parent the parent who opened the dilemma
     * @see daos.ResultDao#save()
     */
    private void makeNewResultRecord(models.Parent parent){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Result result = new Result(parent.getId(), null, timestamp, null);
        resultDao.save(result);
    }
}
