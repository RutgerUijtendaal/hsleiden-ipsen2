package controllers;

import daos.DaoManager;
import models.Admin;
import service.PasswordService;
import util.AddAdminSubmitData;
import util.AdminSubmitData;
import views.AddEditAdminView;
import views.BaseView;

public class EditAdminController extends AdminController {

    public EditAdminController(AppController appCtl) {
        super(appCtl);
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminListView();
    }

    public void handleSubmitBtnClick(AdminSubmitData aasd) {
        this.adminSubmitData = aasd;

        String passwordHash = hashPassword();

        Admin admin = adminSubmitData.getAdmin(passwordHash);
        admin.setId(aasd.getId());

        try {
            System.out.println(adminSubmitData.getPassword());
            if (adminSubmitData.getPassword().isEmpty()) {

                DaoManager.getAdminDao().updateWithoutPassword(admin);
            } else {
                DaoManager.getAdminDao().update(admin);
            }
        } catch (Exception e) {
            e.printStackTrace();
            aeav.displayError("Fout tijdens aanpassen van beheerder.");
            return;
        }

        appCtl.switchToAdminListView();

        appCtl.getActiveView().displayPopup("Beheerder aangepast.");
    }

    public void fillFields(AdminSubmitData aasd) {
        aeav.fillFields(aasd);
    }
}
