package controllers;

import daos.DaoManager;
import models.Admin;
import service.PasswordService;
import util.AddAdminSubmitData;
import util.AdminSubmitData;
import util.EditAdminSubmitData;
import views.AddEditAdminView;
import views.BaseView;

public class EditAdminController extends AdminController {

    public EditAdminController(AppController appCtl) {
        super(appCtl);
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminListView();
    }

    @Override
    public void handleSubmitBtnClick(AdminSubmitData asd) {
        this.adminSubmitData = asd;

        String passwordHash = hashPassword();

        Admin admin = adminSubmitData.getAdmin(passwordHash);
        admin.setId(asd.getId());

        try {
            if (adminSubmitData.getPassword().isEmpty()) {

                DaoManager.getAdminDao().updateWithoutPassword(admin);
            } else {
                DaoManager.getAdminDao().update(admin);
            }
        } catch (Exception e) {
            aeav.displayError("Fout tijdens aanpassen van beheerder.");
            e.printStackTrace();
            return;
        }

        appCtl.switchToAdminListView();

        appCtl.getActiveView().displayPopup("Beheerder aangepast.");
    }

    public void fillFields(AdminSubmitData asd) {
        aeav.fillFields(asd);
    }
}
