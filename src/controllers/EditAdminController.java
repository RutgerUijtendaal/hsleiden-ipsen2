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

    public void handleBackBtnClick() {
        appCtl.switchToAdminListView();
    }

    public void handleSubmitBtnClick(AddAdminSubmitData aasd) {
        this.addAdminSubmitData = aasd;

        String passwordHash = hashPassword();

        Admin admin = addAdminSubmitData.getAdmin(passwordHash);
        admin.setId(aasd.getId());

        try {
            DaoManager.getAdminDao().update(admin);
        } catch (Exception e) {
            aeav.displayError("Fout tijdens toevoegen van beheerder.");
            return;
        }

        appCtl.switchToAdminListView();

        appCtl.getActiveView().displayPopup("Beheerder aangepast.");
    }

    public void fillFields(AddAdminSubmitData aasd) {
        aeav.fillFields(aasd);
    }
}
