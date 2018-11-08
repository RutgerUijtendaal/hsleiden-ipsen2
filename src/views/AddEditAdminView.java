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
import util.AdminSubmitData;
import util.EditAdminSubmitData;

/**
 * Represent the view for Add and Edit admin view
 * @version 1.0
 * @author Jordi Dorren
 * @author Rutger Uijtendaal
 */
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

    private AdminController adminController;

    private int currentAdminId;

    private int sliderValue;

    public AddEditAdminView(AdminController adminController) {
        this.adminController = adminController;
        rootFXML = super.loadFXML("../fxml/add_admin.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        addTransitions();
        initSlider();
    }

    private void addTransitions() {
        double smallChange = 1.05;

        super.setScaleTransitions(submitBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);
        super.setScaleTransitions(email, smallChange);
        super.setScaleTransitions(password, smallChange);
        super.setScaleTransitions(rightsSlider, smallChange);
    }

    private void initSlider() {
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

    /**
     * Handles the submit button from the fxml file
     */
    public void handleSubmitBtnClick() {
        String aEmail = email.getText();
        String aPassword = password.getText();
        int rightsId = sliderValue;

        AdminSubmitData adminSubmitData = null;
        if (adminController instanceof EditAdminController) {
            adminSubmitData = new EditAdminSubmitData(aEmail, aPassword, rightsId);
        } else if (adminController instanceof AddAdminController) {
            adminSubmitData = new AddAdminSubmitData(aEmail, aPassword, rightsId);

        }

        if (adminSubmitData.dataIsValid()) {
            if (adminController instanceof EditAdminController) {
                adminSubmitData.setId(currentAdminId);
            }
            adminController.handleSubmitBtnClick(adminSubmitData);
        } else {
            displayError(adminSubmitData.errorMessage);
        }
    }

    /**
     * Sets the controller according to the actions required
     * @param adminController the controller needed for the required action
     */
    public void setController(AdminController adminController) {
        emptyFields();
        if (adminController instanceof AddAdminController) {
            topLabel.setText("Beheerder toevoegen");
        } else if (adminController instanceof EditAdminController) {
            topLabel.setText("Beheerder aanpassen");
        }
        this.adminController = adminController;
    }

    /**
     * Fills the fields with data from the database
     * @param adminSubmitData The data to fill the fields
     */
    public void fillFields(AdminSubmitData adminSubmitData) {
        email.setText(adminSubmitData.getEmail());
        currentAdminId = adminSubmitData.getId();
        int sliderValue = adminSubmitData.getRightsId();
        rightsSlider.setValue(sliderValue);
    }

    /**
     * Clear all the fields
     */
    public void emptyFields() {
        email.clear();
        password.clear();
        rightsSlider.setValue(1.0);
    }

    /**
     * Handles the back button from the fxml file
     */
    public void handleBackBtnClick() {
        adminController.handleBackBtnClick();
    }
}
