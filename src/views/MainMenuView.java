package views;

import controllers.MainMenuController;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class MainMenuView {

    @FXML Button parentBtn;
    @FXML Button adminBtn;
    @FXML Button signupBtn;

    MainMenuController mmc;

    public MainMenuView() {
        //TODO het hele FXML gebeurem lmao
    }

    public void registerController(MainMenuController mmc) {
        this.mmc = mmc;
    }

    public Scene getViewScene() {
        return parentBtn.getScene(); // TODO dit werkt maar ugly
    }
}

