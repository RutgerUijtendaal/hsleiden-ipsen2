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

    public void handleSubmitBtnClick(AdminLoginSubmitData alsd) {
        this.adminLoginSubmitData = alsd;

        if(!DaoManager.getAdminDao().emailExists(adminLoginSubmitData.getEmail())) {
            adminLoginView.displayError("Wachtwoord of e-mail niet correct.");
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
