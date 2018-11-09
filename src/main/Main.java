package main;

import controllers.AppController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Main class to run the program
 *
 * @author Bas de Bruyn
 * @author Stefan de Keijzer
 * @author Jordi Dorren
 * @author Rutger Uijtendaal
 * @author Danny van Tol
 */
public class Main extends Application {

    AppController appController;

    @Override
    public void init() {
    }

    @Override
    public void start(Stage appStage) {
        Font.loadFont(this.getClass().getResourceAsStream("/resources/helveticaneue.ttf"), 10);

        appController = new AppController(appStage);

        appStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/resources/d.png")));

        appStage.setTitle("DUBIO");
        appStage.setMaximized(true);
        appStage.setWidth(1280);
        appStage.setHeight(720);
        appStage.show();
    }

    @Override
    public void stop(){
        appController.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

