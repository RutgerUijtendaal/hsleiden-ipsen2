package controllers;

import daos.*;
import models.Dilemma;
import views.CoupleListView;
import views.BaseView;
import models.Parent;
import models.Couple;
import views.DilemmaListView;

import java.util.List;

public class DilemmaListController {

    AppController appCtl;
    DilemmaListView dlv;

    public DilemmaListController(AppController appCtl) {
        this.appCtl = appCtl;
        dlv = new DilemmaListView(this);
        handleSearchBtnClick("");
    }

    public BaseView getView() {
        return dlv; // TODO willen we dit zo?
    }

    public void handleSearchBtnClick(String theme) {

        dlv.clearListData();

        if (theme.isEmpty()) {

            DilemmaDao dilemmaDao = DaoManager.getDilemmaDao();
            List<Dilemma> allDillemas = dilemmaDao.getAll();

            dlv.addDillemas(allDillemas);

        } else {

            DilemmaDao dilemmaDao = DaoManager.getDilemmaDao();
            List<Dilemma> foundDillemas = dilemmaDao.getByTheme(theme);

            dlv.addDillemas(foundDillemas);

        }
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

}
