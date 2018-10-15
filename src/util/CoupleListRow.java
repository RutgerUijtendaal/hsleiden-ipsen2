package util;

import models.Parent;

import javafx.scene.layout.HBox;

public class CoupleListRow extends HBox {

    private models.Parent parent1;
    private models.Parent parent2;

    public CoupleListRow(models.Parent parent1, models.Parent parent2) {
        this.parent1 = parent1;
        this.parent2 = parent2;
    }

    public int getParent1Id() {
        return parent1.getId();
    }

    public int getParent2Id() {
        return parent2.getId();
    }
}
