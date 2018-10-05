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
        if (mmc == null) {
            mmc = new MainMenuController();
        }
        //appStage.setScene(mmc.getViewScene());
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
