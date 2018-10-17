package controllers;

import daos.*;
import exceptions.FailedToReadFromResultSetException;
import javafx.scene.Scene;
import models.*;
import org.joda.time.DateTime;
import views.AnswerDilemmaView;
import views.LoginMenuView;

public class AnswerDilemmaController {
    AppController appCtl;
    AnswerDilemmaView adv;

    String email;


    DilemmaDao dilemmaDao = DaoManager.getDilemmaDao();
    AnswerDao answerDao = DaoManager.getAnswerDao();

    Parent parent;
    Couple couple;
    Child child;

    Dilemma dilemma;
    Answer[] answers = new Answer[2];

    Answer chosen;

    public AnswerDilemmaController(AppController appCtl, Parent parent, Couple couple, Child child) {
        this.appCtl = appCtl;
        this.adv = new AnswerDilemmaView(this);

        this.parent = parent;
        this.couple =  couple;
        this.child = child;

        this.email = email;
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

    public void collectData() {
        try {


            dilemma = dilemmaDao.getById(3);
            answers = answerDao.getByDilemma(dilemma);

            adv.setDilemmaContent(dilemma);
            adv.setAnswers(answers);
        } catch (FailedToReadFromResultSetException exception) {
            exception.printStackTrace();
            returnToLoginViewWithMessage("Inloggen mislukt");
        }
    }

    public void returnToLoginViewWithMessage(String message) {
        System.out.println("Returning to LoginMenuView");
        appCtl.switchToLoginView();

        LoginMenuView loginMenuView = (LoginMenuView) appCtl.getActiveView();
        loginMenuView.displayPopup(message);

        System.out.println(appCtl.getActiveView());
    }
}

