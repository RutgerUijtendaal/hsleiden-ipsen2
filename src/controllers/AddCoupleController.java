package controllers;

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

    public void handleSubmitBtnClick() {
        acv.displayError("SUBMITTING UNDER CONSTRUCTION");
    }
}
