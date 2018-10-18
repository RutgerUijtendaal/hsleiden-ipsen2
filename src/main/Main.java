package main;

import ui.AppController;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {

    AppController appCtl;

    @Override
    public void init() {
    }

    @Override
    public void start(Stage appStage) {
        Font.loadFont("../resources/helveticaneue.ttf", 10);

        appCtl = new AppController(appStage);

        appStage.getIcons().add(new Image(this.getClass().getResourceAsStream("../resources/d.png")));

        appStage.setTitle("DUBIO");
        appStage.setMaximized(true);
        appStage.setWidth(1280);
        appStage.setHeight(720);
        appStage.show();
    }

    @Override
    public void stop(){
        appCtl.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

