package controllers;

import daos.DaoManager;
import models.Answer;
import models.Dilemma;
import models.StatisticModel;
import views.BaseView;
import views.StatisticView;

import java.util.List;

/**
 * The class that handles all the logic from the statistics
 * this class heavily relies on the StatisticModel which it often
 * communicates with in order to show the user the statistics
 *
 * @author Stefan de Keijzer
 * @author Bas de Bruyn
 */
public class StatisticController {

    private final AppController appController;
    private final StatisticView statisticView;
    private final StatisticModel statisticModel;

    /**
     * Creates the StatisticController and with it pulls all
     * the necessary data from all daos which it needs in order
     * to make proper statistics
     *
     * @param appController in order to call switching back to other views and such
     * @see daos.DilemmaDao#getAll()
     * @see daos.AnswerDao#getAll()
     * @see daos.ParentDao#getAll()
     * @see daos.CoupleDao#getAll()
     * @see daos.ChildDao#getAll()
     * @see daos.ResultDao#getAll()
     * @see controllers.StatisticController#updateView()
     * @see controllers.StatisticController#addDilemmasToView()
     */
    public StatisticController(AppController appController) {
        this.appController = appController;
        statisticModel = new StatisticModel();
        try {
            statisticModel.setData(DaoManager.getDilemmaDao().getAll(), DaoManager.getParentDao().getAll(), DaoManager.getCoupleDao().getAll(), DaoManager.getChildDao().getAll(), DaoManager.getAnswerDao().getAll(), DaoManager.getResultDao().getAll());
        } catch (Exception e) {
            getView().displayError("Fout tijdens het inladen van statistieken.");
        }
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

    private void updateView() {
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

