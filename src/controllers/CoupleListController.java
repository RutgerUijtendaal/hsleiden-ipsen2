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

    public void deleteCouple(int couple_id, models.Parent parent1, models.Parent parent2) {
        CoupleDao coupleDao = DaoManager.getCoupleDao();
        ParentDao parentDao = DaoManager.getParentDao();
        //coupleDao.deleteById(couple_id);
        //parentDao.delete(parent1);
        //parentDao.delete(parent2);
        clv.deleteCurrentlySelectedRow();
        clv.switchToSingleNotice();
        clv.displayPopup("Ouderpaar is verwijdered.");
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
