package controllers;

import models.Right;
import views.AdminMenuView;
import views.BaseView;

/**
 * Handles the logic behind the AdminMenuView
 * also tells the AdminMenuView which buttons to hide
 * 
 * @author Jordi Dorren
 * @author Rutger Uijtendaal
 * @author Stefan de Keijzer
 */
public class AdminMenuController {

    private final AppController appController;
    private final AdminMenuView adminMenuView;

    public AdminMenuController(AppController appController) {
        this.appController = appController;
        adminMenuView = new AdminMenuView(this);
    }

    public BaseView getView() {
        return adminMenuView; // TODO willen we dit zo?
    }

    public void handleDilemmaBtnClick() {
        appController.switchToDilemmaListView();
    }

    public void handleAdminListBtnClick() {
        appController.switchToAdminListView();
    }

    public void handleStatisticBtnClick() {
        appController.switchToStatisticsView();
    }

    public void handleParentBtnClick() {
        appController.switchToCoupleListView();
    }

    public void handleBackBtnClick() {
        appController.switchToMainMenuView();
    }

    public void handleAddAdminBtnClick() { appController.switchToAddAdminView(); }

    /**
     * Sets what buttons to display in the view
     *
     * @param rights to which the view should display the buttons
     * @see views.AdminMenuView#displayStudentButtons()
     * @see views.AdminMenuView#displayEmployeeButtons()
     * @see views.AdminMenuView#displayModeratorButtons()
     */
    public void setRights(Right rights) {
        if(rights.isCanViewStatistics()) {
            adminMenuView.displayStudentButtons();
        }

        if(rights.isCanEditDilemma()) {
            adminMenuView.displayEmployeeButtons();
        }

        if(rights.isCanEditUserInfo()) {
            adminMenuView.displayModeratorButtons();
        }

    }
}

