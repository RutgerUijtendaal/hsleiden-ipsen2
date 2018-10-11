package models;

import models.Couple;
import models.Child;
import models.Parent;
import models.Dilemma;
import models.Answer;

public class AnswerDilemmaModel {

    private int currentAnswer;
    private Couple couple;
    private Child child;
    private Parent parent;
    private Dilemma dilemma;
    private Answer answer;

    public AnswerDilemmaModel(int currentAnswer, Couple couple, Child child, Parent parent, Dilemma dilemma, Answer answer) {
        this.currentAnswer = currentAnswer;
        this.couple = couple;
        this.child = child;
        this.parent = parent;
        this.dilemma = dilemma;
        this.answer = answer;
    }

    public void setCouple(Couple couple) {
        this.couple = couple;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public void setDilemma(Dilemma dilemma) {
        this.dilemma = dilemma;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public void setCurrentAnswer(int currentAnswer) {
        this.currentAnswer = currentAnswer;
    }

    public int getCurrentAnswer() {
        return currentAnswer;
    }

    public Couple getCouple() {
        return couple;
    }

    public Child getChild() {
        return child;
    }

    public Dilemma getDilemma() {
        return dilemma;
    }

    public Answer getAnswer() {
        return answer;
    }

}

