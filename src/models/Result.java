package models;

import java.sql.Timestamp;

public class Result implements DatabaseObject<Result> {

    private int id;
    private int parent_id;
    private Integer answer_id;
    private Timestamp sentTime;
    private Timestamp answeredTime;

    public Result(int parent_id, Integer answer_id, Timestamp sentTime, Timestamp answeredTime) {
        this.parent_id = parent_id;
        this.answer_id = answer_id;
        this.sentTime = sentTime;
        this.answeredTime = answeredTime;
    }

    public Result(int id, int parent_id, Integer answer_id, Timestamp sentTime, Timestamp answeredTime) {
        this.id = id;
        this.parent_id = parent_id;
        this.answer_id = answer_id;
        this.sentTime = sentTime;
        this.answeredTime = answeredTime;
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

    public Integer getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(Integer answer_id) {
        this.answer_id = answer_id;
    }

    public Timestamp getAnsweredTime() {
        return answeredTime;
    }

    public void setAnsweredTime(Timestamp answeredTime) {
        this.answeredTime = answeredTime;
    }

    public Timestamp getSentTime() {
        return sentTime;
    }

    public void setSentTime(Timestamp sentTime) {
        this.sentTime = sentTime;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", parent_id=" + parent_id +
                ", answer_id=" + answer_id +
                ", sentTime=" + sentTime +
                ", answeredTime=" + answeredTime +
                '}';
    }
}
