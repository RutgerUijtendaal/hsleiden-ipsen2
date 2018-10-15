package controllers;

import views.BaseView;
import views.StatisticView;

public class StatisticController {

    private final AppController appController;
    private final StatisticView statisticView;

    public StatisticController(AppController appController) {
        this.appController = appController;
        statisticView = new StatisticView(this);
    }

    public BaseView getView() {
        return statisticView;
    }
}

