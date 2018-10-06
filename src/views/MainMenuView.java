package views;

import controllers.MainMenuController;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;

public class MainMenuView extends BaseView {

    private Scene rootScene;

    private @FXML Parent rootFXML;

    private @FXML Button parentBtn;
    private @FXML Button adminBtn;
    private @FXML Button signupBtn;

    private @FXML ImageView logoD;
    private @FXML ImageView logoU;
    private @FXML ImageView logoB;
    private @FXML ImageView logoI;
    private @FXML ImageView logoO;

    MainMenuController mmc;

    public MainMenuView(MainMenuController mmc) {
        this.mmc = mmc;
        rootFXML = super.loadFXML("../fxml/main_menu.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);
            
        super.setScaleTransitions(parentBtn);
        super.setScaleTransitions(adminBtn);
        super.setScaleTransitions(signupBtn);

        super.setScaleTransitions(logoD);
        super.setScaleTransitions(logoU);
        super.setScaleTransitions(logoB);
        super.setScaleTransitions(logoI);
        super.setScaleTransitions(logoO);
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
}

