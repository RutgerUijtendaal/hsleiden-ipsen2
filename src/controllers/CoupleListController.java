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
        handleSearchBtnClick("");
    }

    public BaseView getView() {
        return clv; // TODO willen we dit zo?
    }

    private void doCompleteSearchAndFill(List<CoupleListModel> allCouples) {
        if (allCouples != null) {
            for (CoupleListModel currCouple : allCouples) {
                clv.addSingleRow(currCouple);
            }
        }
    }

    public void deleteCouple(int couple_id, int parent_id1, int parent_id2) {
        System.out.printf("gotta purge couple: %d, with parent_id1: %d, and parent_id2: %d\n", couple_id, parent_id1, parent_id2);
        CoupleDao coupleDao = DaoManager.getCoupleDao();
        ParentDao parentDao = DaoManager.getParentDao();

    }

    public void handleSearchBtnClick(String email) {

        clv.clearListData();

        if (email.isEmpty()) {

            CoupleListDao coupleListDao = DaoManager.getCoupleListDao();
            List<CoupleListModel> allCouples = coupleListDao.getAll();

            doCompleteSearchAndFill(allCouples);

        } else {

            CoupleListDao coupleListDao = DaoManager.getCoupleListDao();
            List<CoupleListModel> foundCoupleListModels = coupleListDao.getByEmail(email);

            doCompleteSearchAndFill(foundCoupleListModels);

        }
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

}
