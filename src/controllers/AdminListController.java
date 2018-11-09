package controllers;

import daos.AdminDao;
import daos.DaoManager;
import models.Admin;
import models.Right;
import util.AddAdminSubmitData;
import views.AdminListView;
import views.BaseView;

import java.util.List;

/**
 * Controller that matches with the AdminListView
 * Handles logic for deleting and showing admins
 *
 * @author Jordi Dorren, Stefan de Keijzer
 */
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

    /**
     * Loads admins from AdminDao and hands them to AdminListView
     *
     * @see daos.AdminDao#getAll()
     * @see views.AdminListView#addAdmins(List<Admin>)
     */
    public void loadAdmins() {
        AdminDao adminDao = DaoManager.getAdminDao();
        List<Admin> allAdmins = adminDao.getAll();
        adminListView.addAdmins(allAdmins);
    }

    public void handleBackBtnClick() {
        appController.switchToAdminMenuView();
    }

    public void handleAddAdminBtnClick() { appController.switchToAddAdminView(); }

    /**
     * Handles deleting an admin from the database
     * based on a given Admin object
     *
     * Afterwards tells the AdminListView to delete
     * that particular row from its filtered list
     *
     * NOTE: The admin object has to contain an id
     *
     * @param admin the object that needs to be deleted from the database
     * @see daos.AdminDao#deleteById(int)
     * @see views.AdminListView#deleteRow(Admin)
     */
    public void deleteAdmin(Admin admin) {
        int adminId = admin.getId();
        AdminDao adminDao = DaoManager.getAdminDao();
        adminDao.deleteById(adminId);
        adminListView.deleteRow(admin);
        adminListView.switchToSingleNotice();
        adminListView.displayPopup("Beheerder is verwijdered.");
    }

    /**
     * Handles creating AddAdminSubmitData switching to AddEditAdminView
     *
     * @param admin that needs to be editted
     * @see controllers.AppController#switchToEditAdminView(util.AdminSubmitData)
     */
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
