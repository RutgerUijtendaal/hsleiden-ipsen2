package controllers;

import daos.DaoManager;
import models.Answer;
import models.Dilemma;
import models.StatisticModel;
import views.BaseView;
import views.StatisticView;

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
        statisticView.initReactionSpeedChart();
        updateView();
    }

    public BaseView getView() {
        return statisticView;
    }

    private void addDilemmasToView() {
        statisticView.addDilemmaToList(statisticModel.getFilteredDilemmas());
    }

    public void resetModel() {
        statisticModel.resetFilters();
    }

    public void filterByDilemma(List<Dilemma> dilemmas) {
        statisticModel.filterByDilemma(dilemmas);
        updateView();
    }

    public StatisticModel getStatisticModel() {
        return statisticModel;
    }

    public void updateView() {
        statisticView.modelUpdated(statisticModel);
    }

    public void handleBackBtnClick() {
        appController.switchToAdminMenuView();
    }

    public void handleResetFiltersButtonClick() {
        statisticModel.resetFilters();
        updateView();
    }

    public void filterByAnswers(List<Answer> answers) {
        statisticModel.filterByAnswer(answers);
        updateView();
    }

    public void filterByBorn(boolean born) {
        statisticModel.filterByBronStatus(born);
        updateView();
    }

    public void filterByHour(int hour) {
        statisticModel.filterByHour(hour);
        updateView();
    }
}

