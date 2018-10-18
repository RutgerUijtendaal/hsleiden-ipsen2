package ui.couplelist;

import models.database.Parent;
import ui.AppController;
import data.DaoManager;
import data.daos.CoupleListDao;
import data.daos.ParentDao;
import data.daos.CoupleDao;
import ui.BaseView;
import models.database.CoupleListModel;

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

    public void deleteCouple(int couple_id, Parent parent1, Parent parent2) {
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
        Parent parent1 = coupleListModel.getParent1();
        Parent parent2 = coupleListModel.getParent2();
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
