package models;

import daos.DaoManager;

import java.util.List;

public class StatisticModel {

    private DaoManager daoManager;
    private List<Result> results;

    public StatisticModel(DaoManager daoManager, List<Result> results) {
        this.daoManager = daoManager;
        this.results = results;
    }

    public DaoManager getDaoManager() {
        return daoManager;
    }

    public void setDaoManager(DaoManager daoManager) {
        this.daoManager = daoManager;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "StatisticModel{" +
                "daoManager=" + daoManager +
                ", results=" + results +
                '}';
    }
}
