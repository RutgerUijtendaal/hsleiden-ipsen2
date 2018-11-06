package controllers;

import service.PasswordService;
import util.AdminSubmitData;
import views.AddEditAdminView;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public abstract class AdminController {

    AppController appController;
    AddEditAdminView addEditAdminView;
    AdminSubmitData adminSubmitData;

    public AdminController(AppController appController) {
        this.appController = appController;
    }

    public void createView() {
        this.addEditAdminView = new AddEditAdminView(this);
    }

    public AddEditAdminView getView() {
        return this.addEditAdminView;
    }

    public void setView(AddEditAdminView addEditAdminView) {
        this.addEditAdminView = addEditAdminView;
    }

    public abstract void handleSubmitBtnClick(AdminSubmitData adminSubmitData);

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
