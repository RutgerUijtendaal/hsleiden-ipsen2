package controllers;

import views.CoupleListView;
import daos.ConnectionFactory;
import daos.DaoManager;
import daos.CoupleListDao;
import daos.ParentDao;
import daos.CoupleDao;
import views.BaseView;
import models.Parent;
import models.Couple;
import models.CoupleListModel;

import java.util.List;

public class CoupleListController {

    AppController appCtl;
    CoupleListView clv;

    public CoupleListController(AppController appCtl) {
        this.appCtl = appCtl;
        clv = new CoupleListView(this);
        processAdminRights();
    }

    public BaseView getView() {
        return clv; // TODO willen we dit zo?
    }

    public void deleteCouple(int couple_id, models.Parent parent1, models.Parent parent2) {
    }

    public void loadCouples() {
        CoupleListDao coupleListDao = DaoManager.getCoupleListDao();
        List<CoupleListModel> allCouples = coupleListDao.getAll();
        clv.addCouples(allCouples);
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

    public void deleteCouple(CoupleListModel coupleListModel) {
        int couple_id = coupleListModel.getCoupleId();
        models.Parent parent1 = coupleListModel.getParent1();
        models.Parent parent2 = coupleListModel.getParent2();
        CoupleDao coupleDao = DaoManager.getCoupleDao();
        ParentDao parentDao = DaoManager.getParentDao();
        //coupleDao.deleteById(couple_id);
        //parentDao.delete(parent1);
        //parentDao.delete(parent2);
        clv.deleteRow(coupleListModel);
        clv.switchToSingleNotice();
        clv.displayPopup("Ouderpaar is verwijdered.");
    }

    private void processAdminRights() {
        if(appCtl.getRights().isCanEditDilemma()) {
            clv.setIsAdmin(true);
        }
    }
}
