package views;

import controllers.AdminMenuController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 * View for the admin menu
 * @Rutger Uijtendaal
 */
public class AdminMenuView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button dilemmaBtn;
    private @FXML Button statisticBtn;
    private @FXML Button parentBtn;
    private @FXML Button backBtn;
    private @FXML Button adminListBtn;

    private AdminMenuController adminMenuController;

    public AdminMenuView(AdminMenuController adminMenuController) {
        this.adminMenuController = adminMenuController;
        rootFXML = super.loadFXML("../fxml/admin_screen.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);
        applyTransitions();
        dilemmaBtn.setVisible(false);
        statisticBtn.setVisible(false);
        parentBtn.setVisible(false);
        adminListBtn.setVisible(false);
    }

    /**
     * Applies the transitions to all the elements
     */
    private void applyTransitions() {
        double smallChange = 1.05;
        super.setScaleTransitions(dilemmaBtn, smallChange);
        super.setScaleTransitions(statisticBtn, smallChange);
        super.setScaleTransitions(parentBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);
        super.setScaleTransitions(adminListBtn, smallChange);
    }

    /**
     * Handles the button from the fxml file
     */
    public void handleDilemmaBtnClick() {
        adminMenuController.handleDilemmaBtnClick();
    }

    /**
     * Handles the button from the fxml file
     */
    public void handleStatisticBtnClick() {
        adminMenuController.handleStatisticBtnClick();
    }

    /**
     * Handles the button from the fxml file
     */
    public void handleBackBtnClick() {
        adminMenuController.handleBackBtnClick();
    }

    /**
     * Handles the button from the fxml file
     */
    public void handleParentBtnClick() {
        adminMenuController.handleParentBtnClick();
    }

    /**
     * Handles the button from the fxml file
     */
    public void handleAdminListBtnClick() {
        adminMenuController.handleAdminListBtnClick();
    }

    /**
     * Displays the buttons accessible for the students
     */
    public void displayStudentButtons() {
        statisticBtn.setVisible(true);
        dilemmaBtn.setVisible(true);
    }

    /**
     * Displays the buttons accessible for the employees
     */
    public void displayEmployeeButtons() {
        parentBtn.setVisible(true);
    }

    /**
     * Displays the buttons accessible for the moderators
     */
    public void displayModeratorButtons() {
        adminListBtn.setVisible(true);
    }

}
