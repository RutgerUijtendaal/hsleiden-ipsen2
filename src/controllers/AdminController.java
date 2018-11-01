package controllers;
import service.PasswordService;
import views.AddEditAdminView;
import views.BaseView;
import util.AddAdminSubmitData;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public abstract class AdminController {

    AppController appCtl;
    AddEditAdminView aeav;
    AddAdminSubmitData addAdminSubmitData;

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

    public abstract void handleSubmitBtnClick(AddAdminSubmitData aasd);
    public abstract void handleBackBtnClick();

    protected String hashPassword() {
        String passHash = null;
        try {
            passHash = PasswordService.generatePasswordHash(addAdminSubmitData.getPassword());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return passHash;
    }

}
