package views;

import controllers.MainMenuController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class MainMenuView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button parentBtn;
    private @FXML Button adminBtn;
    private @FXML Button signupBtn;
    private @FXML Button shutdownBtn;
    private @FXML Button adminListBtn;

    private MainMenuController mainMenuController;

    public MainMenuView(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
        rootFXML = super.loadFXML("../fxml/main_menu.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;
            
        super.setScaleTransitions(parentBtn, smallChange);
        super.setScaleTransitions(adminBtn, smallChange);
        super.setScaleTransitions(signupBtn, smallChange);
        super.setScaleTransitions(shutdownBtn, smallChange);

        super.doFadeOut(fillPane);
    }

    public Scene getViewScene() {
        return rootScene;
    }

    public void handleParentBtnClick() {
        mainMenuController.handleParentBtnClick();
    }

    public void handleAdminBtnClick() {
        mainMenuController.handleAdminBtnClick();
    }

    public void handleSignupBtnClick() {
        mainMenuController.handleSignupBtnClick();
    }

    public void handleShutdownBtnClick() {
        mainMenuController.handleShutdownBtnClick();
    }
}

