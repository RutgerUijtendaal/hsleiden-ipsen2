package controllers;

import daos.CoupleDao;
import daos.CoupleListDao;
import daos.DaoManager;
import daos.ParentDao;
import models.CoupleListModel;
import models.Right;
import views.BaseView;
import views.CoupleListView;

import java.util.List;

/**
 * The logic class behind CoupleListView
 *
 * @author Jordi Dorren
 * @author Stefan de Keijzer
 */
public class CoupleListController {

    private final AppController appController;
    private final CoupleListView coupleListView;

    public CoupleListController(AppController appController) {
        this.appController = appController;
        coupleListView = new CoupleListView(this);
    }

    public BaseView getView() {
        return coupleListView; // TODO willen we dit zo?
    }

    /**
     * Loads all the couples from the database
     * and gives them to the CoupleListView
     *
     * @see daos.CoupleDao#getAll()
     * @see views.CoupleListView#addCouples(List<models.Couple>)
     */
    public void loadCouples() {
        CoupleListDao coupleListDao = DaoManager.getCoupleListDao();
        List<CoupleListModel> allCouples = coupleListDao.getAll();
        coupleListView.addCouples(allCouples);
    }

    public void handleBackBtnClick() {
        appController.switchToAdminMenuView();
    }

    /**
     * Handles the deleting of a couple from the database
     * first deletes the parents associated to that couple
     * and deleted the couple itself afterwards
     *
     * After a succesfull deleting of the couple
     * this method tells its CoupleListView to delete
     * that couple from the filtered list that resides in
     * CoupleListView
     *
     * @param coupleListModel the couple data which should be deleted
     * @see daos.CoupleDao#deleteById(int)
     * @see daos.ParentDao#delete(models.DatabaseObject)
     * @see daos.ParentDao#delete(models.DatabaseObject)
     * @see views.CoupleListView#deleteRow(CoupleListModel)
     */
    public void deleteCouple(CoupleListModel coupleListModel) {
        int couple_id = coupleListModel.getCoupleId();
        models.Parent parent1 = coupleListModel.getParent1();
        models.Parent parent2 = coupleListModel.getParent2();

        CoupleDao coupleDao = DaoManager.getCoupleDao();
        ParentDao parentDao = DaoManager.getParentDao();
        try {
            coupleDao.deleteById(couple_id);
            parentDao.delete(parent1);
            parentDao.delete(parent2);
        } catch (Exception e) {
            coupleListView.displayError("Fout tijdens verwijderen van ouderpaar.");
            return;
        }

        coupleListView.deleteRow(coupleListModel);
        coupleListView.switchToSingleNotice();
        coupleListView.displayPopup("Ouderpaar is verwijdered.");
    }

    public void setRights(Right rights) {
        if(rights.isCanEditDilemma()) {
            coupleListView.setIsAdmin(true);
        }
    }
}
