package models;

public class Right {

    private int id;
    private boolean can_edit_dilemma;
    private boolean can_view_statistics;

    public Right(boolean can_edit_dilemma, boolean can_view_statistics) {
        this.can_edit_dilemma = can_edit_dilemma;
        this.can_view_statistics = can_view_statistics;
    }

    public Right(int id, boolean can_edit_dilemma, boolean can_view_statistics){
        this.id = id;
        this.can_edit_dilemma = can_edit_dilemma;
        this.can_view_statistics = can_view_statistics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getCan_edit_dilemma() {
        return can_edit_dilemma;
    }

    public void setCan_edit_dilemma(boolean can_edit_dilemma) {
        this.can_edit_dilemma = can_edit_dilemma;
    }

    public boolean getCan_view_statistics() {
        return can_view_statistics;
    }

    public void setCan_view_statistics(boolean can_view_statistics) {
        this.can_view_statistics = can_view_statistics;
    }

    @Override
    public String toString() {
        return "Right{" +
                "id=" + id +
                ", can_edit_dilemma=" + can_edit_dilemma +
                ", can_view_statistics=" + can_view_statistics +
                '}';
    }
}
