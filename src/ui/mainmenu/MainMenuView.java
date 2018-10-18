package ui.mainmenu;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import ui.BaseView;

public class MainMenuView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button parentBtn;
    private @FXML Button adminBtn;
    private @FXML Button signupBtn;
    private @FXML Button shutdownBtn;

    private MainMenuController mmc;

    public MainMenuView(MainMenuController mmc) {
        this.mmc = mmc;
        rootFXML = super.loadFXML("main_menu.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;
            
        super.setScaleTransitions(parentBtn, smallChange);
        super.setScaleTransitions(adminBtn, smallChange);
        super.setScaleTransitions(signupBtn, smallChange);
        super.setScaleTransitions(shutdownBtn, smallChange);
    }

    public Scene getViewScene() {
        return rootScene;
    }

    public void handleParentBtnClick() {
        System.out.println("running handleParentBtnClick from MainMenuView");
        mmc.handleParentBtnClick();
    }

    public void handleAdminBtnClick() {
        System.out.println("running handleAdminBtnClick from MainMenuView");
        mmc.handleAdminBtnClick();
    }

    public void handleSignupBtnClick() {
        System.out.println("running handleSignupBtnClick from MainMenuView");
        mmc.handleSignupBtnClick();
    }

    public void handleShutdownBtnClick() {
        mmc.handleShutdownBtnClick();
    }
}

