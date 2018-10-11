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
        handleSearchBtnClick("");
    }

    public BaseView getView() {
        return clv; // TODO willen we dit zo?
    }

    private void doCompleteSearchAndFill(List<models.Parent> allParents, List<Couple> allCouples) {


        for (Couple currCouple : allCouples) {

            models.Parent parent1 = null;
            models.Parent parent2 = null;

            int id1 = currCouple.getParent1_id();
            int id2 = currCouple.getParent2_id();

            for (Parent currParent : allParents) {
                int parentId = currParent.getId();
                if (parentId == id1 || parentId == id2) {
                    if (parent1 == null) {
                        parent1 = currParent;
                    } else {
                        parent2 = currParent;
                        break;
                    }
                }
            }

            clv.addSingleRow(parent1, parent2);
        }
    }

    public void handleSearchBtnClick(String email) {

        clv.clearListData();

        if (email.isEmpty()) {

            ParentDao parentDao = DaoManager.getParentDao();
            CoupleDao coupleDao = DaoManager.getCoupleDao();
            List<models.Parent> allParents = parentDao.getAll();
            List<Couple> allCouples = coupleDao.getAll();
            parentDao = null;
            coupleDao = null;

            doCompleteSearchAndFill(allParents, allCouples);

        } else {

            //TODO

        }
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

}
