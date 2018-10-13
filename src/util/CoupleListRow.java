package util;

import models.CoupleListModel;

import javafx.scene.layout.HBox;

public class CoupleListRow extends HBox {

    private CoupleListModel couple;

    public CoupleListRow(CoupleListModel couple) {
        this.couple = couple;
    }

    public CoupleListModel getCouple() {
        return couple;
    }

    public void setCouple(CoupleListModel couple) {
        this.couple = couple;
    }

}
