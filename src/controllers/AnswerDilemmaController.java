package controllers;

import daos.*;
import exceptions.FailedToReadFromResultSetException;
import javafx.scene.Scene;
import models.*;
import org.joda.time.DateTime;
import org.joda.time.Period;
import views.AnswerDilemmaView;

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

        int weekNumber = calculateChildAgeInWeeks(this.child);
        getDilemmaBasedonWeekNumber(weekNumber);
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
        DateTime currentDate = DateTime.now();

        int weeksBetween = (new Period(childDate, currentDate)).getWeeks();

        if (child.getIsBorn())
            weeksBetween += 25;

        return weeksBetween;
    }

    public void getDilemmaBasedonWeekNumber(int weekNumber) {
        try {
            dilemma = dilemmaDao.getByWeekNr(weekNumber);
            answers = answerDao.getByDilemma(dilemma);

            adv.setDilemmaContent(dilemma);
            adv.setAnswers(answers);
        } catch (FailedToReadFromResultSetException exception) {
            adv.noDilemmaAvailable();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

