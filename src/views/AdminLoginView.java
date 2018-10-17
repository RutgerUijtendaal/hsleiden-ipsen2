package views;

import controllers.AdminLoginController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.AdminLoginSubmitData;

public class AdminLoginView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button submitBtn;
    private @FXML Button backBtn;

    private @FXML TextField email;
    private @FXML PasswordField password;

    private AdminLoginController alc;

    public AdminLoginView(AdminLoginController alc) {
        this.alc = alc;
        rootFXML = super.loadFXML("../fxml/admin_login.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;

        super.setScaleTransitions(submitBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);
        super.setScaleTransitions(email, smallChange);
        super.setScaleTransitions(password, smallChange);
    }

    public Scene getViewScene() {
        return rootScene;
    }

    public void handleSubmitBtnClick() {
        System.out.println("running handleSubmitBtnClick from AdminLoginView");

        String aEmail = email.getText();
        String aPassword = password.getText();

        AdminLoginSubmitData adminLoginSubmitData = new AdminLoginSubmitData(aEmail, aPassword);

        if(adminLoginSubmitData.dataIsValid()) {
            alc.handleSubmitBtnClick(adminLoginSubmitData);
        } else {
            displayError(adminLoginSubmitData.errorMessage);
        }

    }

    public void handleBackBtnClick() {
        System.out.println("running handleBackBtnClick from AdminLoginView");
        alc.handleBackBtnClick();
    }
}