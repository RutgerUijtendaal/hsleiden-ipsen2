package controllers;

import daos.AdminDao;
import daos.DaoManager;
import models.Admin;
import models.Right;
import util.AddAdminSubmitData;
import views.AdminListView;
import views.BaseView;

import java.util.List;

public class AdminListController {

    AppController appController;
    AdminListView adminListView;

    public AdminListController(AppController appController) {
        this.appController = appController;
        adminListView = new AdminListView(this);
    }

    public BaseView getView() {
        return adminListView; // TODO willen we dit zo?
    }

    public void deleteCouple(int couple_id, models.Parent parent1, models.Parent parent2) {
    }

    public void loadAdmins() {
        AdminDao adminDao = DaoManager.getAdminDao();
        List<Admin> allAdmins = adminDao.getAll();
        adminListView.addAdmins(allAdmins);
    }

    public void handleBackBtnClick() {
        appController.switchToAdminMenuView();
    }

    public void handleAddAdminBtnClick() { appController.switchToAddAdminView(); }

    public void deleteAdmin(Admin admin) {
        int adminId = admin.getId();
        AdminDao adminDao = DaoManager.getAdminDao();
        adminDao.deleteById(adminId);
        adminListView.deleteRow(admin);
        adminListView.switchToSingleNotice();
        adminListView.displayPopup("Beheerder is verwijdered.");
    }

    public void editAdmin(Admin admin) {
        AddAdminSubmitData addAdminSubmitData = new AddAdminSubmitData(admin.getEmail(), admin.getPassword(), admin.getRights_id());
        addAdminSubmitData.setId(admin.getId());
        appController.switchToEditAdminView(addAdminSubmitData);
    }

    public void setRights(Right rights) {
        if(rights.isCanEditDilemma()) {
            adminListView.setIsAdmin(true);
        }
    }
}
