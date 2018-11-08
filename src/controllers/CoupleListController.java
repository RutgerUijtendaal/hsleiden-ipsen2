package controllers;

import daos.CoupleDao;
import daos.CoupleListDao;
import daos.DaoManager;
import daos.ParentDao;
import models.CoupleListModel;
import models.Right;
import views.BaseView;
import views.CoupleListView;

import java.util.List;

public class CoupleListController {

    AppController appController;
    CoupleListView coupleListView;

    public CoupleListController(AppController appController) {
        this.appController = appController;
        coupleListView = new CoupleListView(this);
    }

    public BaseView getView() {
        return coupleListView; // TODO willen we dit zo?
    }

    public void loadCouples() {
        CoupleListDao coupleListDao = DaoManager.getCoupleListDao();
        List<CoupleListModel> allCouples = coupleListDao.getAll();
        coupleListView.addCouples(allCouples);
    }

    public void handleBackBtnClick() {
        appController.switchToAdminMenuView();
    }

    public void deleteCouple(CoupleListModel coupleListModel) {
        int couple_id = coupleListModel.getCoupleId();
        models.Parent parent1 = coupleListModel.getParent1();
        models.Parent parent2 = coupleListModel.getParent2();
        CoupleDao coupleDao = DaoManager.getCoupleDao();
        ParentDao parentDao = DaoManager.getParentDao();
        coupleDao.deleteById(couple_id);
        parentDao.delete(parent1);
        parentDao.delete(parent2);
        coupleListView.deleteRow(coupleListModel);
        coupleListView.switchToSingleNotice();
        coupleListView.displayPopup("Ouderpaar is verwijdered.");
    }

    public void setRights(Right rights) {
        if(rights.isCanEditDilemma()) {
            coupleListView.setIsAdmin(true);
        }
    }
}
