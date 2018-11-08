package controllers;

import daos.DaoManager;
import models.Admin;
import util.AdminSubmitData;

/**
 * One of the two children from AdminController
 * This class handles editting an admin and is paired with AddEditAdminView.
 * 
 * @author Jordi Dorren
 */
public class EditAdminController extends AdminController {

    public EditAdminController(AppController appController) {
        super(appController);
    }

    public void handleBackBtnClick() {
        appController.switchToAdminListView();
    }

    /**
     * Send new AdminSubmitData to the database
     * Overwrite the password if there is a new one.
     *
     * @see daos.AdminDao#save()
     * @param AdminSubmitData the data that is to be submitted.
     */
    @Override
    public void handleSubmitBtnClick(AdminSubmitData adminSubmitData) {
        this.adminSubmitData = adminSubmitData;

        String passwordHash = hashPassword();

        Admin admin = this.adminSubmitData.getAdmin(passwordHash);
        admin.setId(adminSubmitData.getId());

        try {
            if (this.adminSubmitData.getPassword().isEmpty()) {
                DaoManager.getAdminDao().updateWithoutPassword(admin);
            } else {
                DaoManager.getAdminDao().update(admin);
            }
        } catch (Exception e) {
            addEditAdminView.displayError("Fout tijdens aanpassen van beheerder.");
            e.printStackTrace();
            return;
        }

        appController.switchToAdminListView();

        appController.getActiveView().displayPopup("Beheerder aangepast.");
    }

    public void fillFields(AdminSubmitData adminSubmitData) {
        addEditAdminView.fillFields(adminSubmitData);
    }
}
