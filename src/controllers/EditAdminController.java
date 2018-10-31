package controllers;

import daos.DaoManager;
import models.Admin;
import service.PasswordService;
import util.AddAdminSubmitData;
import views.AddEditAdminView;
import views.BaseView;

public class EditAdminController extends AdminController {

    public EditAdminController(AppController appCtl) {
        super(appCtl);
    }

    public void handleSubmitBtnClick(AddAdminSubmitData aasd) {
        this.addAdminSubmitData = aasd;

        if(DaoManager.getAdminDao().emailExists(addAdminSubmitData.getEmail())) {
            aeav.displayError("Beheerder account onder dit email bestaat al");
            return;
        }

        String passwordHash = hashPassword();

        Admin admin = addAdminSubmitData.getAdmin(passwordHash);

        try {
            DaoManager.getAdminDao().save(admin);
        } catch (Exception e) {
            aeav.displayError("Fout tijdens toevoegen van beheerder.");
            return;
        }

        appCtl.switchToAdminMenuView();

        appCtl.getActiveView().displayPopup("Nieuwe beheerder toegevoegd.");
    }

    public void fillFields(AddAdminSubmitData aasd) {
        aeav.fillFields(aasd);
    }
}
