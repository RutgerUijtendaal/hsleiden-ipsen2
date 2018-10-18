package models.database;

import data.DaoManager;

import java.util.List;

public class DilemmaListModel {

    private DaoManager daoManager;
    private List<Dilemma> dilemmas;

    public DilemmaListModel(DaoManager daoManager, List<Dilemma> dilemmas) {
        this.daoManager = daoManager;
        this.dilemmas = dilemmas;
    }

    public DaoManager getDaoManager() {
        return daoManager;
    }

    public void setDaoManager(DaoManager daoManager) {
        this.daoManager = daoManager;
    }

    public List<Dilemma> getDilemmaList() {
        return dilemmas;
    }

    public void setDilemmas(List<Dilemma> dilemmas) {
        this.dilemmas = dilemmas;
    }

    @Override
    public String toString() {
        return "DilemmaListModel{" +
                "daoManager=" + daoManager +
                ", dilemmas=" + dilemmas +
                '}';
    }
}
