package models;

public class Answer implements DatabaseObject<Answer> {

    private int id;
    private int dilemma_id;
    private String url;
    private String text;

    public Answer(int dilemma_id, String url, String text) {
        this.dilemma_id = dilemma_id;
        this.url = url;
        this.text = text;
    }

    public Answer(int id, int dilemma_id, String url, String text) {
        this.id = id;
        this.dilemma_id = dilemma_id;
        this.url = url;
        this.text = text;
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

    public void setDilemma_id(int dilemma_id) {
        this.dilemma_id = dilemma_id;
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

    public int getDilemma_id() {
        return dilemma_id;
    }

    public boolean hasImage() {
        return this.url != null;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", dilemma_id=" + dilemma_id +
                ", url='" + url + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
