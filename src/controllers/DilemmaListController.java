package controllers;

import daos.DaoManager;
import daos.DilemmaDao;
import models.Dilemma;
import models.Right;
import views.BaseView;
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

    public void handleAddDilemmaBtnClick() { appCtl.switchToAddDilemmaView(); }

    public void deleteDilemma(Dilemma dilemma) {
        DilemmaDao dilemmaDao = DaoManager.getDilemmaDao();
        //dilemmaDao.delete(dilemma);
        dlv.deleteRow(dilemma);
        dlv.switchToSingleNotice();
        dlv.displayPopup("Dilemma is verwijdered.");
    }

    public void editDilemma(Dilemma dilemma) {
        appCtl.switchToEditDilemmaView(dilemma);
    }

    public void setRights(Right right) {
        if(right.isCanEditDilemma()) {
            dlv.setIsAdmin(true);
        }
    }

}
