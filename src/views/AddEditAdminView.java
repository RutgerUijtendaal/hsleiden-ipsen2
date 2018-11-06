package views;

import controllers.AddAdminController;
import controllers.AdminController;
import controllers.EditAdminController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import util.AddAdminSubmitData;
import util.EditAdminSubmitData;
import util.AdminSubmitData;

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

    private @FXML Slider rightsSlider;

    private @FXML Label rightsText;

    private AdminController ac;

    private int currentAdminId;

    private int sliderValue;

    public AddEditAdminView(AdminController adminController) {
        this.ac = adminController;
        rootFXML = super.loadFXML("../fxml/add_admin.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;

        super.setScaleTransitions(submitBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);
        super.setScaleTransitions(email, smallChange);
        super.setScaleTransitions(password, smallChange);
        super.setScaleTransitions(rightsSlider, smallChange);

        rightsSlider.setValue(1.0);

        rightsSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                sliderValue = new_val.intValue();
                String rightsDecription = "";
                switch(sliderValue) {
                    case 1: rightsDecription = "Student";
                        break;
                    case 2: rightsDecription = "Personeel";
                        break;
                    case 3: rightsDecription = "Beheerder";
                        break;
                    default: rightsDecription = "Onbekend niveau";
                }
                rightsText.setText(rightsDecription);
            }
        });
    }

    public void handleSubmitBtnClick() {
        String aEmail = email.getText();
        String aPassword = password.getText();
        int rightsId = sliderValue;

        AdminSubmitData adminSubmitData = null;
        if (ac instanceof EditAdminController) {
            adminSubmitData = new EditAdminSubmitData(aEmail, aPassword, rightsId);
        } else if (ac instanceof AddAdminController) {
            adminSubmitData = new AddAdminSubmitData(aEmail, aPassword, rightsId);

        }

        if (adminSubmitData.dataIsValid()) {
            if (ac instanceof EditAdminController) {
                adminSubmitData.setId(currentAdminId);
            }
            ac.handleSubmitBtnClick(adminSubmitData);
        } else {
            displayError(adminSubmitData.errorMessage);
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

    public void fillFields(AdminSubmitData adminSubmitData) {
        email.setText(adminSubmitData.getEmail());
        currentAdminId = adminSubmitData.getId();
        int sliderValue = adminSubmitData.getRightsId();
        rightsSlider.setValue(sliderValue);
    }

    public void emptyFields() {
        email.clear();
        password.clear();
        rightsSlider.setValue(1.0);
    }

    public void handleBackBtnClick() {
        ac.handleBackBtnClick();
    }
}
