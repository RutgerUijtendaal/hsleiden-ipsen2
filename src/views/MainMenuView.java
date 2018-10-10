package views;

import controllers.MainMenuController;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;

public class MainMenuView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button parentBtn;
    private @FXML Button adminBtn;
    private @FXML Button signupBtn;

    private @FXML ImageView logoD;
    private @FXML ImageView logoU;
    private @FXML ImageView logoB;
    private @FXML ImageView logoI;
    private @FXML ImageView logoO;

    private MainMenuController mmc;

    public MainMenuView(MainMenuController mmc) {
        this.mmc = mmc;
        rootFXML = super.loadFXML("../fxml/main_menu.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;
        double bigChange = 1.1;
            
        super.setScaleTransitions(parentBtn, smallChange);
        super.setScaleTransitions(adminBtn, smallChange);
        super.setScaleTransitions(signupBtn, smallChange);

        super.setScaleTransitions(logoD, bigChange);
        super.setScaleTransitions(logoU, bigChange);
        super.setScaleTransitions(logoB, bigChange);
        super.setScaleTransitions(logoI, bigChange);
        super.setScaleTransitions(logoO, bigChange);
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

