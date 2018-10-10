package views;

import controllers.LoginMenuController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class LoginMenuView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button submitBtn;
    private @FXML Button backBtn;

    private @FXML TextField email;

    private @FXML ImageView logoD;
    private @FXML ImageView logoU;
    private @FXML ImageView logoB;
    private @FXML ImageView logoI;
    private @FXML ImageView logoO;

    private LoginMenuController lmc;

    public LoginMenuView(LoginMenuController lmc) {
        this.lmc = lmc;
        rootFXML = super.loadFXML("../fxml/login.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;
        double bigChange = 1.1;

        super.setScaleTransitions(submitBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);
        super.setScaleTransitions(email, smallChange);

        super.setScaleTransitions(logoD, bigChange);
        super.setScaleTransitions(logoU, bigChange);
        super.setScaleTransitions(logoB, bigChange);
        super.setScaleTransitions(logoI, bigChange);
        super.setScaleTransitions(logoO, bigChange);
    }

    public Scene getViewScene() {
        return rootScene;
    }

    public void handleSubmitBtnClick() {
        System.out.println("running handleSubmitBtnClick from LoginMenuView");
        String mailingAdres = email.getText();
        email.clear();
        lmc.handleSubmitBtnClick(mailingAdres);
    }

    public void handleBackBtnClick() {
        System.out.println("running handleBackBtnClick from LoginMenuView");
        lmc.handleBackBtnClick();
    }

}
