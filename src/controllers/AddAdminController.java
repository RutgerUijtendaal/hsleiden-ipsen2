package controllers;

import daos.DaoManager;
import models.Admin;
import service.PasswordService;
import util.AddAdminSubmitData;
import util.AdminSubmitData;
import views.AddEditAdminView;
import views.BaseView;

public class AddAdminController extends AdminController {

    public AddAdminController(AppController appCtl) {
        super(appCtl);
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

    @Override
    public void handleSubmitBtnClick(AdminSubmitData asd) {
        this.adminSubmitData = asd;

        if(DaoManager.getAdminDao().emailExists(adminSubmitData.getEmail())) {
            aeav.displayError("Beheerder account onder dit email bestaat al");
            return;
        }

        String passwordHash = hashPassword();

        Admin admin = adminSubmitData.getAdmin(passwordHash);

        try {
            DaoManager.getAdminDao().save(admin);
        } catch (Exception e) {
            aeav.displayError("Fout tijdens toevoegen van beheerder.");
            return;
        }

        appCtl.switchToAdminMenuView();

        appCtl.getActiveView().displayPopup("Nieuwe beheerder toegevoegd.");
    }
}
