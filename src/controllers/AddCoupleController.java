package controllers;

import daos.DaoManager;
import models.Parent;
import util.CoupleSubmitData;
import views.AddCoupleView;
import views.BaseView;

/**
 * Controller class behind the AddCoupleView
 * Handles adding a new couple to the database
 *
 * @author Rutger Uijtendaal, Jordi Dorren
 */
public class AddCoupleController {
    
    private AppController appController;
    private AddCoupleView addCoupleView;
    private CoupleSubmitData coupleSubmitData;

    public AddCoupleController(AppController appController) {
        this.appController = appController;
        addCoupleView = new AddCoupleView(this);
    }

    public BaseView getView() {
        return addCoupleView;
    }

    public void handleBackBtnClick() {
        appController.switchToMainMenuView();
    }

    /**
     * Check if the parents are already in the system
     * Otherwise add them to the system
     *
     * @see controllers.AddCoupleController#trySubmitCouple()
     * @param coupleSubmitData the data that is to be submitted
     */
    public void handleSubmitBtnClick(CoupleSubmitData coupleSubmitData) {
        this.coupleSubmitData = coupleSubmitData;

        // Check if a parent is already registered
        for(Parent parent : this.coupleSubmitData.getParents()) {
            if(DaoManager.getParentDao().emailExists(parent.getEmail())){
                addCoupleView.displayError("Email: " + parent.getEmail() + " is al geregistreerd.");
                return;
            }
        }

        if(!trySubmitCouple()) {
            // TODO exception throwing from Dao
            addCoupleView.displayError("Fout tijdens het opslaan.");
            return;
        }

        appController.switchToMainMenuView();

        appController.getActiveView().displayPopup("U bent toegevoegd.");

    }

    /**
     * Call the DAOS to save the new couple in the system
     *
     * @see daos.CoupleDao#save()
     * @see daos.ChildDao#save()
     * @see daos.ParentDao#save()
     * @return true if saving went properly
     */
    private boolean trySubmitCouple() {
        int parentOneKey = DaoManager.getParentDao().save(coupleSubmitData.getParentOne());
        int parentTwoKey = DaoManager.getParentDao().save(coupleSubmitData.getParentTwo());
        int coupleKey = DaoManager.getCoupleDao().save(coupleSubmitData.getCouple(parentOneKey, parentTwoKey));
        DaoManager.getChildDao().save(coupleSubmitData.getChild(coupleKey));
        return true;
    }
}
