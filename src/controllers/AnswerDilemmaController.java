package controllers;

import daos.AnswerDao;
import daos.DilemmaDao;
import javafx.scene.Scene;
import models.Answer;
import models.Dilemma;
import views.AnswerDilemmaView;

public class AnswerDilemmaController {
    AppController appCtl;
    AnswerDilemmaView adv;

    DilemmaDao dilemmaDao = new DilemmaDao();
    AnswerDao answerDao = new AnswerDao();

    Answer[] answers = new Answer[2];

    Answer chosen;

    public AnswerDilemmaController(AppController appCtl) {
        this.appCtl = appCtl;
        this.adv = new AnswerDilemmaView(this);

        Dilemma dilemma = dilemmaDao.getByPK(1);
        Answer[] answers = answerDao.getByDilemma(dilemma);
    }

    public Scene getViewScene() {
        return this.adv.getViewScene();
    }

    public void selectAnswer(int answer) {
        chosen = answers[answer - 1];
    }

    public void processAnswer() {
        if (chosen == null)
            adv.noAnswer();
    }
}

