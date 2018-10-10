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
        if (email.isEmpty()) {

            ParentDao parentDao = DaoManager.getParentDao();
            CoupleDao coupleDao = DaoManager.getCoupleDao();
            List<Parent> allParents = parentDao.getAll();
            List<Couple> allCouples = coupleDao.getAll();
            parentDao = null;
            coupleDao = null;

            String tmpEmail1 = null;
            String tmpEmail2 = null;

            for (Couple currCouple : allCouples) {
                int id1 = currCouple.getParentId1();
                int id2 = currCouple.getParentId2();
                for (Parent currParent : allParents) {
                    int parentId = currParent.getId();
                    if (parentId == id1 || parentId == id2) {
                        if (tmpEmail1 == null) {
                            tmpEmail1 = currParent.getEmail();
                        } else {
                            tmpEmail2 = currParent.getEmail();
                        }
                    }
                }
            }

            clv.addSingleRow(tmpEmail1, tmpEmail2);

        } else {

        }
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

}
