package views;

import controllers.AdminMenuController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class AdminMenuView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button dilemmaBtn;
    private @FXML Button statisticBtn;
    private @FXML Button parentBtn;
    private @FXML Button backBtn;
    private @FXML Button adminListBtn;

    private AdminMenuController amc;

    public AdminMenuView(AdminMenuController amc) {
        this.amc = amc;
        rootFXML = super.loadFXML("../fxml/admin_screen.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;
            
        super.setScaleTransitions(dilemmaBtn, smallChange);
        super.setScaleTransitions(statisticBtn, smallChange);
        super.setScaleTransitions(parentBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);
        super.setScaleTransitions(adminListBtn, smallChange);

        dilemmaBtn.setVisible(false);
        statisticBtn.setVisible(false);
        parentBtn.setVisible(false);
        adminListBtn.setVisible(false);
    }

    public Scene getViewScene() {
        return rootScene;
    }

    public void handleDilemmaBtnClick() {
        amc.handleDilemmaBtnClick();
    }

    public void handleStatisticBtnClick() {
        amc.handleStatisticBtnClick();
    }

    public void handleBackBtnClick() {
        amc.handleBackBtnClick();
    }

    public void handleParentBtnClick() {
        amc.handleParentBtnClick();
    }

    public void handleAdminListBtnClick() {
        amc.handleAdminListBtnClick();
    }

    public void displayStudentButtons() {
        statisticBtn.setVisible(true);
        dilemmaBtn.setVisible(true);
    }

    public void displayEmployeeButtons() {
        parentBtn.setVisible(true);
    }

    public void displayModeratorButtons() {
        adminListBtn.setVisible(true);
    }

}
