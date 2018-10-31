package views;

import controllers.AddAdminController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import util.AddAdminSubmitData;

public class AddAdminView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button submitBtn;
    private @FXML Button backBtn;

    private @FXML TextField email;
    private @FXML PasswordField password;

    private @FXML RadioButton isStatistics;
    private @FXML RadioButton isAddEdit;
    private @FXML RadioButton isPersonalInfo;

    private @FXML ToggleGroup rights;

    private AddAdminController aac;

    public AddAdminView(AddAdminController aac) {
        this.aac = aac;
        rootFXML = super.loadFXML("../fxml/add_admin.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;

        super.setScaleTransitions(submitBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);
        super.setScaleTransitions(email, smallChange);
        super.setScaleTransitions(password, smallChange);
        super.setScaleTransitions(isStatistics, smallChange);
        super.setScaleTransitions(isAddEdit, smallChange);
        super.setScaleTransitions(isPersonalInfo, smallChange);
    }

    public void handleSubmitBtnClick() {
        String aEmail = email.getText();
        String aPassword = password.getText();
        int rightsId = Integer.valueOf(rights.getSelectedToggle().getUserData().toString());

        AddAdminSubmitData addAdminSubmitData = new AddAdminSubmitData(aEmail, aPassword, rightsId);

        if(addAdminSubmitData.dataIsValid()) {
            aac.handleSubmitBtnClick(addAdminSubmitData);
        } else {
            displayError(addAdminSubmitData.errorMessage);
        }
    }

    public void handleBackBtnClick() {
        aac.handleBackBtnClick();
    }
}
