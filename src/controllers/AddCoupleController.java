package controllers;

import daos.DaoManager;
import util.CoupleSubmitData;
import views.AddCoupleView;

import javafx.scene.Scene;
import javafx.scene.Parent;
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
        DaoManager.getParentDao().save(coupleSubmitData.getParentOne());
        DaoManager.getParentDao().save(coupleSubmitData.getParentTwo());
        //Todo primary keys from parents need to be here to add as fks
        DaoManager.getCoupleDao().save(coupleSubmitData.getCouple(1, 2));
        //Todo primary key of copule needs to be here to add as fk
        DaoManager.getChildDao().save(coupleSubmitData.getChild(1));

        acv.displayPopup("Work in progress.");
    }
}
