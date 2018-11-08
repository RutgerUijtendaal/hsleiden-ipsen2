package views;

import controllers.LoginMenuController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Admin login View
 * @author Rutger Uijtendaal
 */
public class LoginMenuView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button submitBtn;
    private @FXML Button backBtn;

    private @FXML TextField email;

    private LoginMenuController loginMenuController;

    public LoginMenuView(LoginMenuController loginMenuController) {
        this.loginMenuController = loginMenuController;
        rootFXML = super.loadFXML("../fxml/login.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;

        super.setScaleTransitions(submitBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);
        super.setScaleTransitions(email, smallChange);
    }

    /**
     * Handles submit button
     */
    public void handleSubmitBtnClick() {
        String mailingAdres = email.getText();
        email.clear();
        loginMenuController.handleSubmitBtnClick(mailingAdres);
    }

    /**
     * Handles Back button
     */
    public void handleBackBtnClick() {
        loginMenuController.handleBackBtnClick();
    }

}
