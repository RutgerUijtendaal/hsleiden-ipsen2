package models;

import java.util.Date;

public class Couple {

    private int id;
    private Date signupDate;
    private int parent1_id;
    private int parent2_id;

    public Couple(int id, Date signupDate, int parent1_id, int parent2_id) {
        this.id = id;
        this.signupDate = signupDate;
        this.parent1_id = parent1_id;
        this.parent2_id = parent2_id;
    }

    public Date getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(Date signupDate) {
        this.signupDate = signupDate;
    }

    public int getParent1_id() {
        return parent1_id;
    }

    public void setParent1_id(int parent1_id) {
        this.parent1_id = parent1_id;
    }

    public int getParent2_id() {
        return parent2_id;
    }

    public void setParent2_id(int parent2_id) {
        this.parent2_id = parent2_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
