package models.database;

public class Right implements DatabaseObject<Right> {

    private int id;
    private boolean canEditDilemma;
    private boolean canViewStatistics;

    public Right(boolean can_edit_dilemma, boolean can_view_statistics) {
        this.canEditDilemma = can_edit_dilemma;
        this.canViewStatistics = can_view_statistics;
    }

    public Right(int id, boolean can_edit_dilemma, boolean can_view_statistics){
        this.id = id;
        this.canEditDilemma = can_edit_dilemma;
        this.canViewStatistics = can_view_statistics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCanEditDilemma() {
        return canEditDilemma;
    }

    public void setCanEditDilemma(boolean canEditDilemma) {
        this.canEditDilemma = canEditDilemma;
    }

    public boolean isCanViewStatistics() {
        return canViewStatistics;
    }

    public void setCanViewStatistics(boolean canViewStatistics) {
        this.canViewStatistics = canViewStatistics;
    }

    @Override
    public String toString() {
        return "Right{" +
                "id=" + id +
                ", can_edit_dilemma=" + canEditDilemma +
                ", can_view_statistics=" + canViewStatistics +
                '}';
    }
}
