package models;

public class Right implements DatabaseObject<Right> {

    private int id;
    private boolean canEditDilemma;
    private boolean canViewStatistics;
    private boolean canEditUserInfo;

    public Right(boolean can_edit_dilemma, boolean can_view_statistics, boolean canEditUserInfo) {
        this.canEditDilemma = can_edit_dilemma;
        this.canViewStatistics = can_view_statistics;
        this.canEditUserInfo = canEditUserInfo;
    }

    public Right(int id, boolean can_edit_dilemma, boolean can_view_statistics, boolean canEditUserInfo){
        this.id = id;
        this.canEditDilemma = can_edit_dilemma;
        this.canViewStatistics = can_view_statistics;
        this.canEditUserInfo = canEditUserInfo;
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

    public boolean isCanEditUserInfo() {
        return canEditUserInfo;
    }

    public void setCanEditUserInfo(boolean canEditUserInfo) {
        this.canEditUserInfo = canEditUserInfo;
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
