package controllers;

import daos.*;
import exceptions.ReadFromResultSetException;
import models.*;
import org.joda.time.DateTime;
import org.joda.time.Period;
import views.AnswerDilemmaView;
import views.BaseView;

import java.sql.Timestamp;

public class AnswerDilemmaController {
    AppController appController;
    AnswerDilemmaView answerDilemmaView;

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

    public AnswerDilemmaController(AppController appController, Parent parent, Couple couple, Child child) {
        this.appController = appController;
        this.answerDilemmaView = new AnswerDilemmaView(this);

        this.parent = parent;

        int partnerId = (parent.getId() == couple.getParent1_id()) ? couple.getParent2_id() : couple.getParent1_id();
        this.partner = parentDao.getById(partnerId);

        this.couple =  couple;
        this.child = child;

        if(this.child.getIsBorn()) {
            this.answerDilemmaView.childIsBorn();
        }

        int weekNumber = calculateChildAgeInWeeks(this.child);
        getDilemmaBasedonWeekNumber(weekNumber);
    }

    public BaseView getView() {
        return this.answerDilemmaView; // TODO willen we dit zo?
    }

    public void goBack() {
        appController.switchToMainMenuView();
    }

    public void setChildBorn() {
        child.setIsBorn(true);

        ChildDao childDao = DaoManager.getChildDao();
        try {
            childDao.update(child);
        } catch (Exception e) {
            getView().displayError("Fout tijdens veranderen geboortestatus.");
        }

        appController.switchToMainMenuView();

        appController.getActiveView().displayPopup("Gefelicteerd! Een nieuwe Dilemma is onderweg.");
    }

    public void selectAnswer(int answer) {
        chosen = answers[answer - 1];
    }

    public void processAnswer() {
        if (chosen == null) {
            answerDilemmaView.noAnswer();
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Result result = resultDao.getByParentId(parent.getId());

            result.setAnswer_id(chosen.getId());
            result.setAnsweredTime(timestamp);

            resultDao.update(result);

            if (partnerHasSubmit()) {
                appController.sendMail(parent.getEmail(), "Dilemma beantwoord", dilemma.getFeedback());
                appController.sendMail(partner.getEmail(), "Dilemma beantwoord", dilemma.getFeedback());

                System.out.println("Sending feedback");
            }

            appController.switchToLoginView();
        }
    }

    public boolean partnerHasSubmit() {
        return resultDao.isDilemmaAnswered(partner.getId());
    }

    private int calculateChildAgeInWeeks(Child child) {
        DateTime childDate = new DateTime(child.getDate());
        DateTime currentDate = DateTime.now();

        int weeksBetween = (new Period(childDate, currentDate)).getWeeks() + 15;

        return weeksBetween;
    }

    public void getDilemmaBasedonWeekNumber(int weekNumber) {
        try {
            dilemma = dilemmaDao.getByWeekNr(weekNumber);
            answers = answerDao.getByDilemmaId(dilemma.getId());

            answerDilemmaView.setDilemmaContent(dilemma);
            answerDilemmaView.setAnswers(answers);
        } catch (ReadFromResultSetException exception) {
            answerDilemmaView.noDilemmaAvailable();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

