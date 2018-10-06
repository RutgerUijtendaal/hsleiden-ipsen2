package views;

import controllers.MainMenuController;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;

public class MainMenuView extends BaseView {

    private Scene rootScene;

    private @FXML Parent rootFXML;

    private @FXML Button parentBtn;
    private @FXML Button adminBtn;
    private @FXML Button signupBtn;

    MainMenuController mmc;

    public MainMenuView(MainMenuController mmc) {
        this.mmc = mmc;
        rootFXML = super.loadFXML("../fxml/main_menu.fxml");
        rootScene = new Scene(rootFXML, 1280, 800);
        rootScene.getStylesheets().add(this.getClass().getResource("../resources/basic.css").toExternalForm());
    }

    public Scene getViewScene() {
        return rootScene;
    }

    public void handleParentBtnClick() {
        System.out.println("running handleParentBtnClick from MainMenuView");
    }

    public void handleAdminBtnClick() {
        System.out.println("running handleAdminBtnClick from MainMenuView");
    }

    public void handleSignupBtnClick() {
        System.out.println("running handleSignupBtnClick from MainMenuView");
    }
}

