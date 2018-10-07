package controllers;

import views.AddCoupleView;

import javafx.scene.Scene;
import javafx.scene.Parent;

public class AddCoupleController {
    
    AppController appCtl;
    AddCoupleView acv;

    public AddCoupleController(AppController appCtl) {
        this.appCtl = appCtl;
        acv = new AddCoupleView(this);
    }

    public Scene getViewScene() {
        return acv.getViewScene(); // TODO willen we dit zo?
    }

    public void handleBackBtnClick() {
        appCtl.switchToMainMenuView();
    }

    public void handleSubmitBtnClick() {
        acv.displayError("SUBMITTING UNDER CONSTRUCTION");
    }
}
