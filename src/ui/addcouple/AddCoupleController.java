package ui.addcouple;

import ui.AppController;
import data.DaoManager;
import models.database.Parent;
import models.submitdata.CoupleSubmitData;

import ui.BaseView;

public class AddCoupleController {

    AppController appCtl;
    AddCoupleView acv;
    CoupleSubmitData coupleSubmitData;

    public AddCoupleController(AppController appCtl) {
        this.appCtl = appCtl;
        acv = new AddCoupleView(this);
    }

    public BaseView getView() {
        return acv;
    }

    public void handleBackBtnClick() {
        appCtl.switchToMainMenuView();
    }

    public void handleSubmitBtnClick(CoupleSubmitData csd) {
        this.coupleSubmitData = csd;

        // Check if a parent is already registered
        for(Parent parent : coupleSubmitData.getParents()) {
            if(DaoManager.getParentDao().emailExists(parent.getEmail())){
                acv.displayError("Email: " + parent.getEmail() + " is al geregistreerd.");
                return;
            }
        }

        if(!trySubmitCouple()) {
            // TODO exception throwing from Dao
            acv.displayError("Fout tijdens het opslaan.");
            return;
        }

        appCtl.switchToMainMenuView();

        appCtl.getActiveView().displayPopup("U bent toegevoegd.");

    }

    private boolean trySubmitCouple() {
        int parentOneKey = DaoManager.getParentDao().save(coupleSubmitData.getParentOne());
        int parentTwoKey = DaoManager.getParentDao().save(coupleSubmitData.getParentTwo());
        int coupleKey = DaoManager.getCoupleDao().save(coupleSubmitData.getCouple(parentOneKey, parentTwoKey));
        DaoManager.getChildDao().save(coupleSubmitData.getChild(coupleKey));
        return true;
    }
}
