package controllers;

import daos.DaoManager;
import daos.DilemmaDao;
import models.Dilemma;
import models.Right;
import views.BaseView;
import views.DilemmaListView;

import java.util.List;

/**
 * Handles logic behind DilemmaListView
 *
 * @author Jordi Dorren
 * @author Stefan de Keijzer
 */
public class DilemmaListController {

    private final AppController appController;
    private final DilemmaListView dilemmaListView;

    public DilemmaListController(AppController appController) {
        this.appController = appController;
        dilemmaListView = new DilemmaListView(this);
        loadDilemmas();
    }

    public BaseView getView() {
        return dilemmaListView; // TODO willen we dit zo?
    }

    /**
     * Loads dilemmas from DilemmaDao and hands them to DilemmaListView
     *
     * @see daos.DilemmaDao#getAll()
     * @see views.DilemmaListView#addDillemas(List<Dilemma>)
     */
    public void loadDilemmas() {
        DilemmaDao dilemmaDao = DaoManager.getDilemmaDao();
        List<Dilemma> allDillemas = dilemmaDao.getAll();

        dilemmaListView.addDillemas(allDillemas);
    }

    public void handleBackBtnClick() {
        appController.switchToAdminMenuView();
    }

    public void handleAddDilemmaBtnClick() { appController.switchToAddDilemmaView(); }

    /**
     * Handles deleting a dilemma from the database
     * based on a given dilemma object
     *
     * Afterwards tells the DilemmaListView to delete
     * that particular row from its filtered list
     *
     * @param dilemma is the object that needs to be deleted from the database
     * @see daos.DilemmaDao#delete(models.DatabaseObject)
     * @see views.DilemmaListView#deleteRow(models.Dilemma)
     */
    public void deleteDilemma(Dilemma dilemma) {
        DilemmaDao dilemmaDao = DaoManager.getDilemmaDao();

        try {
            dilemmaDao.delete(dilemma);
        } catch (Exception e) {
            dilemmaListView.displayPopup("Fout tijdens het verwijderen van dilemma.");
            return;
        }

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
