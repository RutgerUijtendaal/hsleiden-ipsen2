package main;

import controllers.AppController;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void init() {
    }

    @Override
    public void start(Stage appStage) {

        AppController appCtl = new AppController(appStage);

        appStage.setTitle("DUBIO");
        appStage.setMaximized(true);
        appStage.setWidth(1280);
        appStage.setHeight(720);
        appStage.show();
    }

    @Override
    public void stop(){
    }

    public static void main(String[] args) {
        launch(args);
    }
}

