package controllers;

import daos.*;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import models.*;
import org.joda.time.DateTime;
import views.AnswerDilemmaView;

import javax.swing.text.html.ImageView;

public class AnswerDilemmaController {
    AppController appCtl;
    AnswerDilemmaView adv;

    ParentDao parentDao = new ParentDao();
    CoupleDao coupleDao = new CoupleDao();
    ChildDao childDao = new ChildDao();
    DilemmaDao dilemmaDao = new DilemmaDao();
    AnswerDao answerDao = new AnswerDao();

    Parent parent;
    Couple couple;
    Child child;

    Dilemma dilemma;
    Answer[] answers = new Answer[2];

    Answer chosen;

    public AnswerDilemmaController(AppController appCtl, String email) {
        this.appCtl = appCtl;
        this.adv = new AnswerDilemmaView(this);

        parent = parentDao.getByEmail(email);
        couple = coupleDao.getByParent(parent);
        child = childDao.getByCouple(couple);

        if (child == null) {
            appCtl.switchToLoginView();
        }

        dilemma = dilemmaDao.getById(3);
        answers = answerDao.getByDilemma(dilemma);

        adv.setDilemmaContent(dilemma);
        adv.setAnswers(answers);
    }

    public Scene getViewScene() {
        return this.adv.getViewScene();
    }

    public void selectAnswer(int answer) {
        chosen = answers[answer - 1];
    }

    public void processAnswer() {
        if (chosen == null) {
            adv.noAnswer();
        } else {

        }
    }

    private int calculateChildAgeInWeeks(Child child) {
        DateTime childDate = new DateTime(child.getDate());
        DateTime currentDate = new DateTime();

        System.out.println(currentDate);

        return 0;
    }
}

