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

    private void doCompleteSearchAndFill(List<Dilemma> allDillemas) {
        for (Dilemma currDilemma : allDillemas) {
            dlv.addSingleRow(currDilemma);
        }
    }

    public void handleSearchBtnClick(String email) {

        dlv.clearListData();

        if (email.isEmpty()) {

            DilemmaDao dilemmaDao = DaoManager.getDilemmaDao();
            List<Dilemma> allDillemas = dilemmaDao.getAll();
            dilemmaDao = null;

            doCompleteSearchAndFill(allDillemas);

        } else {

            //TODO

        }
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

}
