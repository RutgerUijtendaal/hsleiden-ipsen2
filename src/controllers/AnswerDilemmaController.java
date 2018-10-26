package controllers;

import daos.*;
import exceptions.FailedToReadFromResultSetException;
import javafx.scene.Scene;
import models.*;
import org.joda.time.DateTime;
import org.joda.time.Period;
import views.AnswerDilemmaView;

import java.sql.Timestamp;

public class AnswerDilemmaController {
    AppController appCtl;
    AnswerDilemmaView adv;

    String email;


    ParentDao parentDao = DaoManager.getParentDao();
    DilemmaDao dilemmaDao = DaoManager.getDilemmaDao();
    AnswerDao answerDao = DaoManager.getAnswerDao();
    ResultDao resultDao = DaoManager.getResultDao();

    Parent parent;
    Parent partner;
    Couple couple;
    Child child;

    Dilemma dilemma;
    Answer[] answers = new Answer[2];

    Answer chosen;

    public AnswerDilemmaController(AppController appCtl, Parent parent, Couple couple, Child child) {
        this.appCtl = appCtl;
        this.adv = new AnswerDilemmaView(this);

        this.parent = parent;

        int partnerId = (parent.getId() == couple.getParent1_id()) ? couple.getParent2_id() : couple.getParent1_id();
        this.partner = parentDao.getById(partnerId);

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
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Result result = resultDao.getByParentId(parent.getId());

            result.setAnswer_id(chosen.getId());
            result.setAnsweredTime(timestamp);

            resultDao.update(result);

            System.out.println(partnerHasSubmit());

            if (partnerHasSubmit()) {
                // TODO SEND feedback mail
                System.out.println("Sending feedback");
            }

            appCtl.switchToLoginView();
        }
    }

    public boolean partnerHasSubmit() {
        return resultDao.isDilemmaAnswered(partner.getId());
    }

    private int calculateChildAgeInWeeks(Child child) {
        DateTime childDate = new DateTime(child.getDate());
        DateTime currentDate = DateTime.now();

        int weeksBetween = (new Period(childDate, currentDate)).getWeeks() + 25;

        return weeksBetween;
    }

    public void getDilemmaBasedonWeekNumber(int weekNumber) {
        try {
            dilemma = dilemmaDao.getByWeekNr(weekNumber);
            answers = answerDao.getByDilemmaId(dilemma.getId());

            adv.setDilemmaContent(dilemma);
            adv.setAnswers(answers);
        } catch (FailedToReadFromResultSetException exception) {
            adv.noDilemmaAvailable();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

