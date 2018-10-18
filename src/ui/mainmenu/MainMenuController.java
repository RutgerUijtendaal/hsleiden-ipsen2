package ui.mainmenu;

import ui.AppController;
import ui.BaseView;
import ui.mainmenu.MainMenuView;

public class MainMenuController {
    
    AppController appCtl;
    MainMenuView mmv;

    public MainMenuController(AppController appCtl) {
        this.appCtl = appCtl;
        mmv = new MainMenuView(this);
    }

    public BaseView getView() {
        return mmv;
    }

    public void handleParentBtnClick() {
        appCtl.switchToLoginView();
    }

    public void handleAdminBtnClick() {
        appCtl.switchToAdminLoginView();
    }

    public void handleSignupBtnClick() {
        appCtl.switchToAddCoupleView();
    }

    public void handleShutdownBtnClick() {
        appCtl.shutdown();
    }
}

