package models;

public class CoupleListModel {

    private int coupleId;
    private Parent parent1;
    private Parent parent2;

    public CoupleListModel(int coupleId, Parent parent1, Parent parent2) {
        this.coupleId = coupleId;
        this.parent1 = parent1;
        this.parent2 = parent2;
    }

    public int getCoupleId() {
        return coupleId;
    }

    public void setCoupleId(int coupleId) {
        this.coupleId = coupleId;
    }

    public Parent getParent1() {
        return parent1;
    }

    public void setParent1(Parent parent1) {
        this.parent1 = parent1;
    }
    
    public Parent getParent2() {
        return parent2;
    }

    public void setParent2(Parent parent2) {
        this.parent2 = parent2;
    }
}
