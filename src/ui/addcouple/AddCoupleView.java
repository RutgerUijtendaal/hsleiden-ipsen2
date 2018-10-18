package ui.addcouple;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import ui.BaseView;
import models.submitdata.CoupleSubmitData;

import java.time.LocalDate;

public class AddCoupleView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button backBtn;
    private @FXML Button submitBtn;

    private @FXML TextField name1;
    private @FXML TextField name2;

    private @FXML TextField email1;
    private @FXML TextField email2;

    private @FXML TextField phone1;
    private @FXML TextField phone2;

    private @FXML DatePicker birthDate;
    private @FXML CheckBox isBorn;

    private AddCoupleController acc;

    public AddCoupleView(AddCoupleController acc) {
        this.acc = acc;
        rootFXML = super.loadFXML("add_couple.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

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

        // We change the birthData field prompt based on if the checkbox is checked.
        isBorn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue == true) {
                    birthDate.setPromptText("Geboortedatum");
                } else {
                    birthDate.setPromptText("Uitgerekende datum");
                }
            }
        });
    }

    public Scene getViewScene() {
        return rootScene;
    }

    public void handleBackBtnClick() {
        System.out.println("running handleBackBtnClick from AddCoupleView");
        acc.handleBackBtnClick();
    }

    public void handleSubmitBtnClick() {
        System.out.println("running handleSubmitBtnClick from AddCoupleView");

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
            acc.handleSubmitBtnClick(coupleSubmitData);
        } else {
            // If there's an error with the data get the error message and display it.
            displayError(coupleSubmitData.errorMessage);
        }
    }
}

