package views;

import controllers.LoginMenuController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginMenuView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button submitBtn;
    private @FXML Button backBtn;

    private @FXML TextField email;

    private LoginMenuController lmc;

    public LoginMenuView(LoginMenuController lmc) {
        this.lmc = lmc;
        rootFXML = super.loadFXML("../fxml/login.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;

        super.setScaleTransitions(submitBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);
        super.setScaleTransitions(email, smallChange);
    }

    public Scene getViewScene() {
        return rootScene;
    }

    public void handleSubmitBtnClick() {
        String mailingAdres = email.getText();
        email.clear();
        lmc.handleSubmitBtnClick(mailingAdres);
    }

    public void handleBackBtnClick() {
        lmc.handleBackBtnClick();
    }

}
