package models;

public class Dilemma {

    private int id;
    private short weekNr;
    private String theme;
    private String imageUrl;
    private int[] answer_ids;

    public Dilemma(short weekNr, String theme, String imageUrl, int[] answer_ids) {
        this.weekNr = weekNr;
        this.theme = theme;
        this.imageUrl = imageUrl;
        this.answer_ids = answer_ids;
    }

    public Dilemma(int id, short weekNr, String theme, String imageUrl, int[] answer_ids) {
        this.id = id;
        this.weekNr = weekNr;
        this.theme = theme;
        this.imageUrl = imageUrl;
        this.answer_ids = answer_ids;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int[] getAnswer_ids() {
        return answer_ids;
    }

    public void setAnswer_ids(int[] answer_ids) {
        this.answer_ids = answer_ids;
    }

}
