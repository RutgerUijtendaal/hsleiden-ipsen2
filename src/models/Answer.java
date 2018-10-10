package models;

public class Answer {

    private int id;
    private String text;
    private String url;

    public Answer(int id, String text, String url) {
        this.id = id;
        this.text = text;
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

}
