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
        loadDilemmas();
    }

    public BaseView getView() {
        return dlv; // TODO willen we dit zo?
    }

    public void loadDilemmas() {
        DilemmaDao dilemmaDao = DaoManager.getDilemmaDao();
        List<Dilemma> allDillemas = dilemmaDao.getAll();

        dlv.addDillemas(allDillemas);
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

}
