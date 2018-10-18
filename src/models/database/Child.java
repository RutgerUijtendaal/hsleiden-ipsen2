package models.database;

import java.sql.Date;

public class Child implements DatabaseObject<Child> {

    private int id;
    private int couple_id;
    private Date date;
    private boolean isBorn;

    public Child(int couple_id, Date date, boolean isBorn) {
        this.couple_id = couple_id;
        this.date = date;
        this.isBorn = isBorn;
    }

    public Child (int id, int couple_id, Date date, boolean isBorn) {
        this.id = id;
        this.couple_id = couple_id;
        this.date = date;
        this.isBorn = isBorn;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setCouple_id(int couple_id) {
        this.couple_id = couple_id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setIsBorn(boolean isBorn) {
        this.isBorn = isBorn;
    }

    public int getId(){
        return id;
    }

    public int getCouple_id() {
        return couple_id;
    }

    public Date getDate() {
        return date;
    }

    public boolean getIsBorn() {
        return isBorn;
    }



    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", couple_id=" + couple_id +
                ", date=" + date +
                ", isBorn=" + isBorn +
                '}';
    }
}
