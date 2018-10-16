package controllers;

import daos.DaoManager;
import util.CoupleSubmitData;
import views.AddCoupleView;

import views.BaseView;

import java.sql.SQLException;

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
        try {
            saveCouple(coupleSubmitData);
        } catch(SQLException exception) {
            exception.printStackTrace();
            acv.displayPopup(exception.getMessage());
        }

    }

    private void saveCouple(CoupleSubmitData coupleSubmitData) throws SQLException {
        int parentOneId = DaoManager.getParentDao().saveWithReturnId(coupleSubmitData.getParentOne());
        int parentTwoId = DaoManager.getParentDao().saveWithReturnId(coupleSubmitData.getParentTwo());
        int coupleId = DaoManager.getCoupleDao().saveWithReturnId(coupleSubmitData.getCouple(parentOneId, parentTwoId));
        DaoManager.getChildDao().saveNew(coupleSubmitData.getChild(coupleId));
    }
}
