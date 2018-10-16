package models;

import java.util.Date;

public class Result {

    private int id;
    private int parent_id;
    private int answer_id;
    private Date answeredDate;
    private Date sentDate;

    public Result(int parent_id, int answer_id, Date answeredDate, Date sentDate) {
        this.parent_id = parent_id;
        this.answer_id = answer_id;
        this.answeredDate = answeredDate;
        this.sentDate = sentDate;
    }

    public Result(int id, int parent_id, int answer_id, Date answeredDate, Date sentDate) {
        this.id = id;
        this.parent_id = parent_id;
        this.answer_id = answer_id;
        this.answeredDate = answeredDate;
        this.sentDate = sentDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
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

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", parent_id=" + parent_id +
                ", answer_id=" + answer_id +
                ", answeredDate=" + answeredDate +
                ", sentDate=" + sentDate +
                '}';
    }
}
