package controllers;

import service.PasswordService;
import util.AdminSubmitData;
import views.AddEditAdminView;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Parent-class to AddAdminController and EditAdminController
 * This abstract class houses functions that both adding and editting
 * admins in the system need
 *
 * @author Jordi Dorren, Rutger Uijtendaal
 */
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

    /**
     * Hashes a the password from the AdminSubmitData
     *
     * @see service.PasswordService#generatePasswordHash(String)
     * @return hashed password string
     */
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
