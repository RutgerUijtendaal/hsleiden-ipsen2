package controllers;

import javafx.scene.Scene;
import views.AnswerDilemmaView;

public class AnswerDilemmaController {
    AppController appCtl;
    AnswerDilemmaView adv;

    public AnswerDilemmaController(AppController appCtl) {
        this.appCtl = appCtl;
        this.adv = new AnswerDilemmaView(this);
    }

    public Scene getViewScene() {
        return this.adv.getViewScene();
    }
}

