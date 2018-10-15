package controllers;

import views.CoupleListView;
import daos.ConnectionFactory;
import daos.DaoManager;
import daos.CoupleListDao;
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

    public void handleSearchBtnClick(String email) {

        clv.clearListData();

        if (email.isEmpty()) {

            CoupleListDao coupleListDao = DaoManager.getCoupleListDao();
            List<CoupleListModel> allCouples = coupleListDao.getAll();
            coupleListDao = null;

            doCompleteSearchAndFill(allCouples);

        } else {

            List<CoupleListModel> foundCoupleListModels = DaoManager.getCoupleListDao().getByEmail(email);

            doCompleteSearchAndFill(foundCoupleListModels);

        }
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

}
