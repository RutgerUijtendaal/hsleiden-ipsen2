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

    private AdminLoginController adminLoginController;

    public AdminLoginView(AdminLoginController adminLoginController) {
        this.adminLoginController = adminLoginController;
        rootFXML = super.loadFXML("../fxml/admin_login.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;

        super.setScaleTransitions(submitBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);
        super.setScaleTransitions(email, smallChange);
        email.setText("admin@admin.com");
        password.setText("admin");
        super.setScaleTransitions(password, smallChange);
    }

    public Scene getViewScene() {
        return rootScene;
    }

    public void handleSubmitBtnClick() {
        String aEmail = email.getText();
        String aPassword = password.getText();

        AdminLoginSubmitData adminLoginSubmitData = new AdminLoginSubmitData(aEmail, aPassword);

        if(adminLoginSubmitData.dataIsValid()) {
            adminLoginController.handleSubmitBtnClick(adminLoginSubmitData);
        } else {
            displayError(adminLoginSubmitData.errorMessage);
        }

    }

    public void handleBackBtnClick() {
        adminLoginController.handleBackBtnClick();
    }
}
