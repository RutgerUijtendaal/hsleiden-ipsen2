package models;

public class Dilemma {

    private int id;
    private short weekNr;
    private String theme;
    private String feedback;

    public Dilemma(short weekNr, String theme, String feedback) {
        this.weekNr = weekNr;
        this.theme = theme;
        this.feedback = feedback;
    }

    public Dilemma(int id, short weekNr, String theme, String feedback) {
        this.id = id;
        this.weekNr = weekNr;
        this.theme = theme;
        this.feedback = feedback;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getWeekNr() {
        return weekNr;
    }

    public void setWeekNr(short weekNr) {
        this.weekNr = weekNr;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Dilemma{" +
                "id=" + id +
                ", weekNr=" + weekNr +
                ", theme='" + theme + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
