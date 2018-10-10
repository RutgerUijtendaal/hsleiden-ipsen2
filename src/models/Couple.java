package models;

import java.util.Date;

public class Couple {

    private Date signupDate;
    private int parentId1;
    private int parentId2;

    public Couple(Date signupDate, int parentId1, int parentId2) {
        this.signupDate = signupDate;
        this.parentId1 = parentId1;
        this.parentId2 = parentId2;
    }

    public Date getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(Date signupDate) {
        this.signupDate = signupDate;
    }

    public int getParentId1() {
        return parentId1;
    }

    public void setParentId1(int parentId1) {
        this.parentId1 = parentId1;
    }

    public int getParentId2() {
        return parentId2;
    }

    public void setParentId2(int parentId2) {
        this.parentId2 = parentId2;
    }
}
