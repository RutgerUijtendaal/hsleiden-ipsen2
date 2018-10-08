package views;

import controllers.AdminMenuController;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;

public class AdminMenuView extends BaseView {

    private Scene rootScene;

    private @FXML Parent rootFXML;

    private @FXML Button dilemmaBtn;
    private @FXML Button statisticBtn;
    private @FXML Button parentBtn;
    private @FXML Button backBtn;

    private @FXML ImageView logoD;
    private @FXML ImageView logoU;
    private @FXML ImageView logoB;
    private @FXML ImageView logoI;
    private @FXML ImageView logoO;

    private AdminMenuController amc;

    public AdminMenuView(AdminMenuController amc) {
        this.amc = amc;
        rootFXML = super.loadFXML("../fxml/admin_screen.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;
        double bigChange = 1.1;
            
        super.setScaleTransitions(dilemmaBtn, smallChange);
        super.setScaleTransitions(statisticBtn, smallChange);
        super.setScaleTransitions(parentBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);

        super.setScaleTransitions(logoD, bigChange);
        super.setScaleTransitions(logoU, bigChange);
        super.setScaleTransitions(logoB, bigChange);
        super.setScaleTransitions(logoI, bigChange);
        super.setScaleTransitions(logoO, bigChange);
    }

    public Scene getViewScene() {
        return rootScene;
    }

    public void handleDilemmaBtnClick() {
        System.out.println("running handleDilemmaBtnClick from AdminMenuView");
        amc.handleDilemmaBtnClick();
    }

    public void handleStatisticBtnClick() {
        System.out.println("running handleStatisticBtnClick from AdminMenuView");
        amc.handleStatisticBtnClick();
    }

    public void handleBackBtnClick() {
        System.out.println("running handleBackBtnClick from AdminMenuView");
        amc.handleBackBtnClick();
    }

    public void handleParentBtnClick() {
        System.out.println("running handleParentBtnClick from AdminMenuView");
        amc.handleParentBtnClick();
    }

}
