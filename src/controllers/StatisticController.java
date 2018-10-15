package controllers;

import daos.DaoManager;
import daos.DilemmaDao;
import models.Dilemma;
import views.BaseView;
import views.StatisticView;

import java.util.List;

public class StatisticController {

    private final AppController appController;
    private final StatisticView statisticView;

    public StatisticController(AppController appController) {
        this.appController = appController;
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
}

