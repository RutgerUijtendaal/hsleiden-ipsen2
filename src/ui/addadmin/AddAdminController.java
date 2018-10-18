package ui.addadmin;

import data.exceptions.DatabaseException;
import ui.AppController;
import data.DaoManager;
import models.database.Admin;
import service.PasswordService;
import models.submitdata.AddAdminSubmitData;
import ui.BaseView;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class AddAdminController {

    AppController appCtl;
    AddAdminView aav;
    AddAdminSubmitData addAdminSubmitData;

    public AddAdminController(AppController appCtl) {
        this.appCtl = appCtl;
        this.aav = new AddAdminView(this);
    }

    public BaseView getView() {
        return this.aav;
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

    public void handleSubmitBtnClick(AddAdminSubmitData aasd) {
        this.addAdminSubmitData = aasd;

        if(DaoManager.getAdminDao().emailExists(addAdminSubmitData.getEmail())) {
            aav.displayError("Beheerder account onder dit email bestaat al");
            return;
        }

        String passwordHash = hashPassword();
        System.out.println(passwordHash.length());

        Admin admin = addAdminSubmitData.getAdmin(passwordHash);

        try {
            DaoManager.getAdminDao().save(admin);
        } catch (DatabaseException e) {
            aav.displayError("Fout tijdens toevoegen van beheerder.");
            return;
        }

        appCtl.switchToAdminMenuView();

        appCtl.getActiveView().displayPopup("Nieuwe beheerder toegevoegd.");
    }

    private String hashPassword() {
        String passHash = null;
        try {
            passHash = PasswordService.generatePasswordHash(addAdminSubmitData.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return passHash;
    }
}
