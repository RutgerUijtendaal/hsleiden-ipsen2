package views;

import controllers.AddAdminController;
import controllers.AdminController;
import controllers.EditAdminController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.AddAdminSubmitData;

public class AddEditAdminView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button submitBtn;
    private @FXML Button backBtn;

    private @FXML TextField email;
    private @FXML PasswordField password;

    private @FXML CheckBox isStatistics;
    private @FXML CheckBox isAddEdit;

    private AdminController ac;

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
    }

    public void handleSubmitBtnClick() {
        String aEmail = email.getText();
        String aPassword = password.getText();
        Boolean aIsStatistics = isStatistics.isSelected();
        Boolean aIsAddEdit = isAddEdit.isSelected();

        AddAdminSubmitData addAdminSubmitData = new AddAdminSubmitData(aEmail, aPassword, aIsStatistics, aIsAddEdit);

        if(addAdminSubmitData.dataIsValid()) {
            ac.handleSubmitBtnClick(addAdminSubmitData);
        } else {
            displayError(addAdminSubmitData.errorMessage);
        }
    }

    public void setController(AdminController ac) {
        if (ac instanceof AddAdminController) {
        } else {
        }
        this.ac = ac;
    }

    public void fillFields(AddAdminSubmitData addAdminSubmitData) {
        email.setText(addAdminSubmitData.getEmail());
        isStatistics.setSelected(addAdminSubmitData.getStatistics());
        isAddEdit.setSelected(addAdminSubmitData.getAddEdit());
    }

    public void handleBackBtnClick() {
        ac.handleBackBtnClick();
    }
}
