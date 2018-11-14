package views;

import controllers.AddCoupleController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import util.AdminSubmitData;
import util.CoupleSubmitData;

import java.time.LocalDate;

/**
 * Represent the view for Add Couple
 * @version 1.0
 * @author Jordi Dorren
 * @author Stefan de Keijzer
 * @author Rutger Uijtendaal
 * @author Bas de Bruyn
 */
public class AddCoupleView extends BaseView {

    private @FXML
    final Parent rootFXML;

    private @FXML Button backBtn;
    private @FXML Button submitBtn;
    private @FXML Button privacyBtn;

    private @FXML TextField name1;
    private @FXML TextField name2;

    private @FXML TextField email1;
    private @FXML TextField email2;

    private @FXML TextField phone1;
    private @FXML TextField phone2;

    private @FXML DatePicker birthDate;
    private @FXML CheckBox isBorn;

    private final AddCoupleController addCoupleController;

    public AddCoupleView(AddCoupleController addCoupleController) {
        this.addCoupleController = addCoupleController;
        rootFXML = super.loadFXML("/fxml/add_couple.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);
        addTransitions();
        // We change the birthData field prompt based on if the checkbox is checked.
        isBorn.setOnAction(e -> {
            if(isBorn.isSelected()){
                birthDate.setPromptText("Geboortedatum");
            } else {
                birthDate.setPromptText("Uitgerekende datum");
            }
        });

        populateDemoFields();
    }

    private void populateDemoFields() {
        name1.setText("Rutger Uijtendaal");
        name2.setText("Jordi Dorren");

        email1.setText("rutger.uijtendaal@gmail.com");
        email2.setText("jordidorren@gmail.com");

        phone1.setText("0652240271");
        phone2.setText("0652240274");

        isBorn.setSelected(false);
        birthDate.setValue(LocalDate.now().plusWeeks(2));
    }

    /**
     * Adds transitions
     */
    private void addTransitions() {
        double smallChange = 1.05;

        super.setScaleTransitions(backBtn, smallChange);
        super.setScaleTransitions(submitBtn, smallChange);

        super.setScaleTransitions(name1, smallChange);
        super.setScaleTransitions(name2, smallChange);
        super.setScaleTransitions(email1, smallChange);
        super.setScaleTransitions(email2, smallChange);
        super.setScaleTransitions(phone1, smallChange);
        super.setScaleTransitions(phone2, smallChange);
        super.setScaleTransitions(birthDate, smallChange);

        super.setScaleTransitions(isBorn, smallChange);
    }

    /**
     * Handles the Back Button
     */
    public void handleBackBtnClick() {
        addCoupleController.handleBackBtnClick();
    }

    /**
     *
     * Submits the data in the field to the controller
     * @see controllers.AddAdminController#handleSubmitBtnClick(AdminSubmitData)
     *
     */
    public void handleSubmitBtnClick() {
        // Collect all values
        String pOneName = name1.getText();
        String pTwoName = name2.getText();

        String pOneEmail = email1.getText();
        String pTwoEmail = email2.getText();

        String pOnePhone = phone1.getText();
        String pTwoPhone = phone2.getText();

        LocalDate cDate = birthDate.getValue();
        Boolean cIsBorn = isBorn.isSelected();

        // Create a Couple data Obj to hold and validate all the data.
        CoupleSubmitData coupleSubmitData = new CoupleSubmitData(pOneName, pTwoName, pOneEmail, pTwoEmail, pOnePhone, pTwoPhone, cDate, cIsBorn);
        // CoupleData validates itself.
        if (coupleSubmitData.dataIsValid()) {
            addCoupleController.handleSubmitBtnClick(coupleSubmitData);
        } else {
            // If there's an error with the data get the error message and display it.
            displayError(coupleSubmitData.errorMessage);
        }
    }

    /**
     * Handles the privacy button
     */
    public void handlePrivacyBtnClick() {
        displayPopup("Privacy verklaring");
    }
}

