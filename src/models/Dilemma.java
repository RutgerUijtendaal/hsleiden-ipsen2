package models;

public class Dilemma {

    private int id;
    private short weekNr;
    private String text;
    private String imageUrl;
    private Answer []answers;

    public Dilemma(int id, short weekNr, String text, String imageUrl, Answer[] answers) {
        this.id = id;
        this.weekNr = weekNr;
        this.text = text;
        this.imageUrl = imageUrl;
        this.answers = answers;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public void setAnswers(Answer[] answers) {
        this.answers = answers;
    }

}
