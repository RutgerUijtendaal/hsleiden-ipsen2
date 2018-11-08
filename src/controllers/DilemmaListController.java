package controllers;

import daos.DaoManager;
import daos.DilemmaDao;
import models.Dilemma;
import models.Right;
import views.BaseView;
import views.DilemmaListView;

import java.util.List;

public class DilemmaListController {

    AppController appController;
    DilemmaListView dilemmaListView;

    public DilemmaListController(AppController appController) {
        this.appController = appController;
        dilemmaListView = new DilemmaListView(this);
        loadDilemmas();
    }

    public BaseView getView() {
        return dilemmaListView; // TODO willen we dit zo?
    }

    public void loadDilemmas() {
        DilemmaDao dilemmaDao = DaoManager.getDilemmaDao();
        List<Dilemma> allDillemas = dilemmaDao.getAll();

        dilemmaListView.addDillemas(allDillemas);
    }

    public void handleBackBtnClick() {
        appController.switchToAdminMenuView();
    }

    public void handleAddDilemmaBtnClick() { appController.switchToAddDilemmaView(); }

    public void deleteDilemma(Dilemma dilemma) {
        DilemmaDao dilemmaDao = DaoManager.getDilemmaDao();
        dilemmaDao.delete(dilemma);
        dilemmaListView.deleteRow(dilemma);
        dilemmaListView.switchToSingleNotice();
        dilemmaListView.displayPopup("Dilemma is verwijdered.");
    }

    public void editDilemma(Dilemma dilemma) {
        appController.switchToEditDilemmaView(dilemma);
    }

    public void setRights(Right right) {
        if(right.isCanEditDilemma()) {
            dilemmaListView.setIsAdmin(true);
        }
    }

}
