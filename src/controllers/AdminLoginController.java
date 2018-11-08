package controllers;

import daos.DaoManager;
import models.Admin;
import models.Right;
import service.PasswordService;
import util.AdminLoginSubmitData;
import views.AdminLoginView;
import views.BaseView;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Allows admins to log in, handles the login behind AdminLoginView
 *
 * @author Rutger Uijtendaal
 */
public class AdminLoginController {

    AppController appController;
    AdminLoginView adminLoginView;
    AdminLoginSubmitData adminLoginSubmitData;

    public AdminLoginController(AppController appController) {
        this.appController = appController;
        this.adminLoginView = new AdminLoginView(this);
    }

    public BaseView getView() {
        return this.adminLoginView;
    }

    public void handleBackBtnClick() {
        appController.switchToMainMenuView();
    }

    /**
     * Handles the logic from logging in as an admin
     * checks if the admin exists, if not return
     * otherwise checks his or her password, gets his rights
     * and logs him or her in
     *
     * @param AdminLoginSubmitData admin data that the system should try to login with
     * @see daos.AdminDao#getByEmail()
     * @see daos.AdminDao#emailExists()
     * @see controllers.AdminLoginController#isValidPassword()
     * @see daos.RightDao#getById()
     * @see controllers.AppController#setAdmin()
     * @see controllers.AppController#setRights()
     */
    public void handleSubmitBtnClick(AdminLoginSubmitData alsd) {
        this.adminLoginSubmitData = alsd;

        if(!DaoManager.getAdminDao().emailExists(adminLoginSubmitData.getEmail())) {
            adminLoginView.displayError("Wachtwoord of e-mail niet correct.");
            return;
        }

        Admin admin = DaoManager.getAdminDao().getByEmail(adminLoginSubmitData.getEmail());
        if(admin == null) {
            adminLoginView.displayError("Wachtwoord of e-mail niet correct.");
            return;
        }

        Right rights = DaoManager.getRightDao().getById(admin.getRights_id());

        if(!isValidPassword(admin)) {
            adminLoginView.displayError("Wachtwoord of e-mail niet correct.");
            return;
        }

        appController.setAdmin(admin);
        appController.setRights(rights);

        appController.switchToAdminMenuView();
    }

    /**
     * Uses the PasswordService to compare the given password
     * to the password that is in the database
     *
     * @param Admin admin to compare the AdminLoginSubmitData to
     * @see service.PasswordService#isValidPassword()
     * @return true of the comparison is equal, false if not
     */
    private boolean isValidPassword(Admin admin) {
        try {
            if (PasswordService.isValidPassword(adminLoginSubmitData.getPassword(), admin.getPassword())) {
                return true;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return false;
    }
}
