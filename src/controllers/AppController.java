package controllers;

import controllers.MainMenuController;

import javafx.stage.Stage;

public class AppController {

    private Stage appStage;

    private MainMenuController mmc;

    public AppController(Stage appStage) {
        this.appStage = appStage;
        switchToMainMenuView();
    }

    public void switchToMainMenuView() {
    }

    public void switchToAnswerDilemmaView(String email) {
    }

    public void switchToAdminView() {
    }

    public void switchToDilemmaListView() {
    }

    public void switchToAddDilemmaView() {
    }

    public void switchToEditDilemmaView() {
    }
}
