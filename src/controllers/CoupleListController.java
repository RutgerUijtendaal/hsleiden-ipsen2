package controllers;

import views.CoupleListView;
import views.BaseView;

public class CoupleListController {

    AppController appCtl;
    CoupleListView clv;

    public CoupleListController(AppController appCtl) {
        this.appCtl = appCtl;
        clv = new CoupleListView(this);
    }

    public BaseView getView() {
        return clv; // TODO willen we dit zo?
    }

    public void handleSearchBtnClick() {
    }

    public void handleBackBtnClick() {
        appCtl.switchToAdminMenuView();
    }
}
