package controllers;

import daos.DaoManager;
import daos.DilemmaDao;
import models.Dilemma;
import models.StatisticModel;
import views.BaseView;
import views.StatisticView;

import java.util.ArrayList;
import java.util.List;

public class StatisticController {

    private final AppController appController;
    private final StatisticView statisticView;
    private final StatisticModel statisticModel;

    public StatisticController(AppController appController) {
        this.appController = appController;
        statisticModel = new StatisticModel();
        statisticModel.setData(DaoManager.getDilemmaDao().getAll(), DaoManager.getParentDao().getAll(), DaoManager.getCoupleDao().getAll(), DaoManager.getChildDao().getAll(), DaoManager.getAnswerDao().getAll(), DaoManager.getResultDao().getAll());
        statisticModel.initFilter();
        statisticView = new StatisticView(this);
        addDilemmasToView();
    }

    public BaseView getView() {
        return statisticView;
    }

    private void addDilemmasToView() {
        DilemmaDao dilemmaDao = DaoManager.getDilemmaDao();
        List<Dilemma> dilemmaList = dilemmaDao.getAll();
        statisticView.addDilemmaToList(dilemmaList);
    }

    public void resetModel() {
        statisticModel.resetFilters();
    }

    public void filterByDilemma(Dilemma dilemma) {
        List<Dilemma> dilemmas = new ArrayList<>();
        dilemmas.add(dilemma);
        statisticModel.filterByDilemma(dilemmas);
    }

    public StatisticModel getStatisticModel() {
        return statisticModel;
    }
}

