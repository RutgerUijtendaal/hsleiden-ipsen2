package controllers;

import views.MainMenuView;

import javafx.scene.Scene;

public class MainMenuController {
    
    MainMenuView mmv;

    public MainMenuController() {
        mmv = new MainMenuView();
        mmv.registerController(this);
    }

    public Scene getViewScene() {
        return mmv.getViewScene(); // TODO willen we dit zo?
    }
}

