package controllers;

import controllers.MainMenuController;
import controllers.AddCoupleController;

import javafx.stage.Stage;

public class AppController {

    private Stage appStage;

    private MainMenuController mmc;
    private AddCoupleController acc;

    public AppController(Stage appStage) {
        this.appStage = appStage;
        switchToMainMenuView();
    }

    public void switchToMainMenuView() {
        if (mmc == null) {
            mmc = new MainMenuController(this);
        }
        appStage.setScene(mmc.getViewScene());
    }

    public void switchToAddCoupleView() {
        if (acc == null) {
            acc = new AddCoupleController(this);
        }
        appStage.setScene(acc.getViewScene());
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
