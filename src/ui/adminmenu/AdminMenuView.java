package ui.adminmenu;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import ui.BaseView;

public class AdminMenuView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button dilemmaBtn;
    private @FXML Button addDilemmaBtn;
    private @FXML Button statisticBtn;
    private @FXML Button parentBtn;
    private @FXML Button backBtn;
    private @FXML Button addAdminBtn;

    private AdminMenuController amc;

    public AdminMenuView(AdminMenuController amc) {
        this.amc = amc;
        rootFXML = super.loadFXML("admin_screen.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;
            
        super.setScaleTransitions(dilemmaBtn, smallChange);
        super.setScaleTransitions(statisticBtn, smallChange);
        super.setScaleTransitions(parentBtn, smallChange);
        super.setScaleTransitions(addDilemmaBtn, smallChange);
        super.setScaleTransitions(addAdminBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);

        dilemmaBtn.setVisible(false);
        statisticBtn.setVisible(false);
        parentBtn.setVisible(false);
        addDilemmaBtn.setVisible(false);
        addAdminBtn.setVisible(false);
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

    public void handleAddDilemmaBtnClick() {
        System.out.println("running handleAddDilemmaBtnClick from AdminView");
        amc.handleAddDilemmaBtnClick();
    }

    public void handleAddAdminBtnClick() {
        System.out.println("running handleAddAdminBtnClick from AdminView");
        amc.handleAddAdminBtnClick();
    }

    public void displayAdminButtons() {
        addDilemmaBtn.setVisible(true);
        addAdminBtn.setVisible(true);
        backBtn.setTranslateX(210);
    }

    public void displayModeratorButtons() {
        dilemmaBtn.setVisible(true);
        statisticBtn.setVisible(true);
        parentBtn.setVisible(true);
        backBtn.setTranslateX(0);
    }

}
