package models;

import java.util.Date;

public class Result {

    private int id;
    private Parent parent;
    private Answer answer;
    private Date answeredDate;
    private Date sentDate;

    public Result(int id, Parent parent, Answer answer, Date answeredDate, Date sentDate) {
        this.id = id;
        this.parent = parent;
        this.answer = answer;
        this.answeredDate = answeredDate;
        this.sentDate = sentDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Date getAnsweredDate() {
        return answeredDate;
    }

    public void setAnsweredDate(Date answeredDate) {
        this.answeredDate = answeredDate;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }
}
