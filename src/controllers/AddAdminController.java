package controllers;

import daos.DaoManager;
import models.Admin;
import util.AdminSubmitData;

/**
 * One of the two children from AdminController
 * This class handles adding an admin and is paired with AddEditAdminView
 * 
 * @author Jordi Dorren
 */
public class AddAdminController extends AdminController {

    public AddAdminController(AppController appController) {
        super(appController);
    }

    public void handleBackBtnClick() {
        appController.switchToAdminListView();
    }

    /**
     * Check if account already exists, add it to database otherwise
     *
     * @see daos.AdminDao#save(models.DatabaseObject)
     * @see controllers.AdminController#hashPassword()
     * @param adminSubmitData the data that is to be submitted
     */
    @Override
    public void handleSubmitBtnClick(AdminSubmitData adminSubmitData) {
        this.adminSubmitData = adminSubmitData;

        if(DaoManager.getAdminDao().emailExists(this.adminSubmitData.getEmail())) {
            addEditAdminView.displayError("Beheerder account onder dit email bestaat al");
            return;
        }

        String passwordHash = hashPassword();

        Admin admin = this.adminSubmitData.getAdmin(passwordHash);

        try {
            DaoManager.getAdminDao().save(admin);
        } catch (Exception e) {
            addEditAdminView.displayError("Fout tijdens toevoegen van beheerder.");
            e.printStackTrace();
            return;
        }

        appController.switchToAdminMenuView();

        appController.getActiveView().displayPopup("Nieuwe beheerder toegevoegd.");
    }
}
