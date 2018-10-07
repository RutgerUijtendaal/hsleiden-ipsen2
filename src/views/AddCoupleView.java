package views;

import controllers.AddCoupleController;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;

public class AddCoupleView extends BaseView {

    private Scene rootScene;

    private @FXML Parent rootFXML;

    private @FXML Button backBtn;
    private @FXML Button submitBtn;

    private @FXML TextField name1;
    private @FXML TextField name2;

    private @FXML TextField email1;
    private @FXML TextField email2;

    private @FXML TextField birthDate;
    private @FXML CheckBox isBorn;

    private @FXML ImageView logoD;
    private @FXML ImageView logoU;
    private @FXML ImageView logoB;
    private @FXML ImageView logoI;
    private @FXML ImageView logoO;


    private AddCoupleController acc;

    public AddCoupleView(AddCoupleController acc) {
        this.acc = acc;
        rootFXML = super.loadFXML("../fxml/add_couple.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;
        double bigChange = 1.1;

        super.setScaleTransitions(backBtn, smallChange);
        super.setScaleTransitions(submitBtn, smallChange);

        super.setScaleTransitions(name1, smallChange);
        super.setScaleTransitions(name2, smallChange);
        super.setScaleTransitions(email1, smallChange);
        super.setScaleTransitions(email2, smallChange);
        super.setScaleTransitions(birthDate, smallChange);
            
        super.setScaleTransitions(logoD, bigChange);
        super.setScaleTransitions(logoU, bigChange);
        super.setScaleTransitions(logoB, bigChange);
        super.setScaleTransitions(logoI, bigChange);
        super.setScaleTransitions(logoO, bigChange);

        super.setScaleTransitions(isBorn, smallChange);

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
        acc.handleSubmitBtnClick();
    }
}

