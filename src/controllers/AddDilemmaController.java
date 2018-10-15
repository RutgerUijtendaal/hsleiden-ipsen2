package controllers;

import util.DilemmaSubmitData;
import views.AddDilemmaView;
import views.BaseView;

public class AddDilemmaController {

    AppController appCtl;
    AddDilemmaView adv;

    public AddDilemmaController(AppController appCtl) {
        this.appCtl = appCtl;
        adv = new AddDilemmaView(this);
    }

    public BaseView getView() { return adv; }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }

    public void handleSubmitBtnClick(DilemmaSubmitData dilemmaSubmitData) {

    }
}

