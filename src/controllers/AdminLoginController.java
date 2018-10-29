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

    AppController appCtl;
    AdminLoginView alv;
    AdminLoginSubmitData adminLoginSubmitData;

    public AdminLoginController(AppController appCtl) {
        this.appCtl = appCtl;
        this.alv = new AdminLoginView(this);
    }

    public BaseView getView() {
        return this.alv;
    }

    public void handleBackBtnClick() {
        appCtl.switchToMainMenuView();
    }

    public void handleSubmitBtnClick(AdminLoginSubmitData alsd) {
        this.adminLoginSubmitData = alsd;

        if(!DaoManager.getAdminDao().emailExists(adminLoginSubmitData.getEmail())) {
            alv.displayError("Wachtwoord of e-mail niet correct.");
        }

        Admin admin = DaoManager.getAdminDao().getByEmail(adminLoginSubmitData.getEmail());
        if(admin == null) {
            alv.displayError("Wachtwoord of e-amil niet correct.");
            return;
        }
        Right rights = DaoManager.getRightDao().getById(admin.getRights_id());

        if(!isValidPassword(admin)) {
            alv.displayError("Wachtwoord of e-amil niet correct.");
            return;
        }

        appCtl.setAdmin(admin);
        appCtl.setRights(rights);

        appCtl.switchToAdminMenuView();
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
