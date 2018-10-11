package models;

public class Right {

    private int right_id;
    private boolean can_edit_dilemma;
    private boolean can_view_statistics;

    Right(int right_id, boolean can_edit_dilemma, boolean can_view_statistics){
        this.right_id = right_id;
        this.can_edit_dilemma = can_edit_dilemma;
        this.can_view_statistics = can_view_statistics;
    }

    public int getRight_id() {
        return right_id;
    }

    public void setRight_id(int right_id) {
        this.right_id = right_id;
    }

    public boolean isCan_edit_dilemma() {
        return can_edit_dilemma;
    }

    public void setCan_edit_dilemma(boolean can_edit_dilemma) {
        this.can_edit_dilemma = can_edit_dilemma;
    }

    public boolean isCan_view_statistics() {
        return can_view_statistics;
    }

    public void setCan_view_statistics(boolean can_view_statistics) {
        this.can_view_statistics = can_view_statistics;
    }
}
