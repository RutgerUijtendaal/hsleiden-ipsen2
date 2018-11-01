package controllers;
import service.PasswordService;
import views.AddEditAdminView;
import views.BaseView;
import util.AdminSubmitData;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public abstract class AdminController {

    AppController appCtl;
    AddEditAdminView aeav;
    AdminSubmitData adminSubmitData;

    public AdminController(AppController appCtl) {
        this.appCtl = appCtl;
    }

    public void createView() {
        this.aeav = new AddEditAdminView(this);
    }

    public AddEditAdminView getView() {
        return this.aeav;
    }

    public void setView(AddEditAdminView aeav) {
        this.aeav = aeav;
    }

    public abstract void handleSubmitBtnClick(AdminSubmitData aasd);
    public abstract void handleBackBtnClick();

    protected String hashPassword() {
        String passHash = null;
        try {
            passHash = PasswordService.generatePasswordHash(adminSubmitData.getPassword());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return passHash;
    }

}
