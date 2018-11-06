package controllers;

import daos.DaoManager;
import models.Parent;
import util.CoupleSubmitData;
import views.AddCoupleView;
import views.BaseView;

public class AddCoupleController {
    
    AppController appController;
    AddCoupleView addCoupleView;
    CoupleSubmitData coupleSubmitData;

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

    private boolean trySubmitCouple() {
        int parentOneKey = DaoManager.getParentDao().save(coupleSubmitData.getParentOne());
        int parentTwoKey = DaoManager.getParentDao().save(coupleSubmitData.getParentTwo());
        int coupleKey = DaoManager.getCoupleDao().save(coupleSubmitData.getCouple(parentOneKey, parentTwoKey));
        DaoManager.getChildDao().save(coupleSubmitData.getChild(coupleKey));
        return true;
    }
}
