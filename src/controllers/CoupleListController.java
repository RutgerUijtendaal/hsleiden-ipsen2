package controllers;

import views.CoupleListView;
import daos.ConnectionFactory;
import daos.DaoManager;
import daos.ParentDao;
import daos.CoupleDao;
import views.BaseView;
import models.Parent;
import models.Couple;

import java.util.List;

public class CoupleListController {

    AppController appCtl;
    CoupleListView clv;

    public CoupleListController(AppController appCtl) {
        this.appCtl = appCtl;
        clv = new CoupleListView(this);
    }

    public BaseView getView() {
        return clv; // TODO willen we dit zo?
    }

    public void handleSearchBtnClick(String email) {

        clv.clearListData();

        if (email.isEmpty()) {

            ParentDao parentDao = DaoManager.getParentDao();
            CoupleDao coupleDao = DaoManager.getCoupleDao();
            List<Parent> allParents = parentDao.getAll();
            List<Couple> allCouples = coupleDao.getAll();
            parentDao = null;
            coupleDao = null;

            models.Parent parent1 = null;
            models.Parent parent2 = null;

            for (Couple currCouple : allCouples) {
                int id1 = currCouple.getParentId1();
                int id2 = currCouple.getParentId2();
                for (Parent currParent : allParents) {
                    int parentId = currParent.getId();
                    if (parentId == id1 || parentId == id2) {
                        if (parent1 == null) {
                            parent1 = currParent;
                        } else {
                            parent2 = currParent;
                        }
                    }
                }
                clv.addSingleRow(parent1, parent2);
                parent1 = null;
                parent2 = null;
            }

        } else {

        }
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

}
