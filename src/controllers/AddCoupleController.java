package controllers;

import daos.DaoManager;
import util.CoupleSubmitData;
import views.AddCoupleView;

import views.BaseView;

public class AddCoupleController {
    
    AppController appCtl;
    AddCoupleView acv;

    public AddCoupleController(AppController appCtl) {
        this.appCtl = appCtl;
        acv = new AddCoupleView(this);
    }

    public BaseView getView() {
        return acv; // TODO willen we dit zo?
    }

    public void handleBackBtnClick() {
        appCtl.switchToMainMenuView();
    }

    public void handleSubmitBtnClick(CoupleSubmitData coupleSubmitData) {
        if(submitCouple(coupleSubmitData)) {
            acv.displayPopup("U bent toegevoegd en zal binnenkort uw eerste dilemma ontvangen");
        } else {
            acv.displayError("Fout tijdens het toevoegen");
        }
    }

    private boolean submitCouple(CoupleSubmitData coupleSubmitData) {
        int parentOneKey = DaoManager.getParentDao().save(coupleSubmitData.getParentOne());
        int parentTwoKey = DaoManager.getParentDao().save(coupleSubmitData.getParentTwo());
        int coupleKey = DaoManager.getCoupleDao().save(coupleSubmitData.getCouple(parentOneKey, parentTwoKey));
        DaoManager.getChildDao().save(coupleSubmitData.getChild(coupleKey));
        return true;
    }
}
