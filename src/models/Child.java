package models;

import java.util.Date;

public class Child {

    private int coupleId;
    private Date date;
    private boolean isBorn;

    public Child (int coupleId, Date date, boolean isBorn) {
        this.coupleId = coupleId;
        this.date = date;
        this.isBorn = isBorn;
    }

    public void setCoupleId(int coupleId) {
        this.coupleId = coupleId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setIsBorn(boolean isBorn) {
        this.isBorn = isBorn;
    }

    public int getCoupleId() {
        return coupleId;
    }

    public Date getDate() {
        return date;
    }

    public boolean isBorn() {
        return isBorn;
    }
}
