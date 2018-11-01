package views;

import controllers.AddAdminController;
import controllers.AdminController;
import controllers.EditAdminController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import util.AddAdminSubmitData;

public class AddEditAdminView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Label topLabel;

    private @FXML Button submitBtn;
    private @FXML Button backBtn;

    private @FXML TextField email;
    private @FXML PasswordField password;

    private @FXML RadioButton isStatistics;
    private @FXML RadioButton isAddEdit;
    private @FXML RadioButton isPersonalInfo;

    private @FXML ToggleGroup rights;

    private AdminController ac;
    private int currentAdminId;

    public AddEditAdminView(AdminController ac) {
        this.ac = ac;
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
            if (ac instanceof EditAdminController) {
                addAdminSubmitData.setId(currentAdminId);
            }
            ac.handleSubmitBtnClick(addAdminSubmitData);
        } else {
            displayError(addAdminSubmitData.errorMessage);
        }
    }

    public void setController(AdminController ac) {
        emptyFields();
        if (ac instanceof AddAdminController) {
            topLabel.setText("Beheerder toevoegen");
        } else if (ac instanceof EditAdminController) {
            topLabel.setText("Beheerder aanpassen");
        }
        this.ac = ac;
    }

    public void fillFields(AddAdminSubmitData addAdminSubmitData) {
        email.setText(addAdminSubmitData.getEmail());
        currentAdminId = addAdminSubmitData.getId();
        int rightsId = addAdminSubmitData.getRightsId();
        switch (rightsId) {
            case 3:
                isStatistics.setSelected(true);
                isAddEdit.setSelected(true);
                isPersonalInfo.setSelected(true);
                break;
            case 2:
                isStatistics.setSelected(true);
                isAddEdit.setSelected(true);
                isPersonalInfo.setSelected(false);
                break;
            default:
                isStatistics.setSelected(true);
                isAddEdit.setSelected(false);
                isPersonalInfo.setSelected(false);
        }
    }

    public void emptyFields() {
        email.clear();
        password.clear();
        isStatistics.setSelected(false);
        isAddEdit.setSelected(false);
        isPersonalInfo.setSelected(false);
    }

    public void handleBackBtnClick() {
        ac.handleBackBtnClick();
    }
}
