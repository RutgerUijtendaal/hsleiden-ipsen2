package controllers;

import daos.AdminDao;
import daos.CoupleDao;
import daos.CoupleListDao;
import daos.DaoManager;
import daos.ParentDao;
import models.Admin;
import models.CoupleListModel;
import models.Right;
import util.AddAdminSubmitData;
import views.BaseView;
import views.AdminListView;

import java.util.List;

public class AdminListController {

    AppController appCtl;
    AdminListView alv;

    public AdminListController(AppController appCtl) {
        this.appCtl = appCtl;
        alv = new AdminListView(this);
    }

    public BaseView getView() {
        return alv; // TODO willen we dit zo?
    }

    public void deleteCouple(int couple_id, models.Parent parent1, models.Parent parent2) {
    }

    public void loadAdmins() {
        AdminDao adminDao = DaoManager.getAdminDao();
        List<Admin> allAdmins = adminDao.getAll();
        alv.addAdmins(allAdmins);
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

    public void deleteAdmin(Admin admin) {
        int adminId = admin.getId();
        AdminDao adminDao = DaoManager.getAdminDao();
        System.out.println(adminId);
        //adminDao.deleteById(adminId);
        alv.deleteRow(admin);
        alv.switchToSingleNotice();
        alv.displayPopup("Beheerder is verwijdered.");
    }

    public void editAdmin(Admin admin) {
        AddAdminSubmitData aasd = new AddAdminSubmitData(admin.getEmail(), admin.getPassword(), false, false);
        aasd.setId(admin.getId());
        aasd.setRightsFromId(admin.getRights_id());
        appCtl.switchToEditAdminView(aasd);
    }

    public void setRights(Right rights) {
        if(rights.isCanEditDilemma()) {
            alv.setIsAdmin(true);
        }
    }
}
