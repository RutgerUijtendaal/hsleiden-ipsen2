package views;

import controllers.StatisticController;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import models.Answer;
import models.Dilemma;
import models.Result;
import models.StatisticModel;

import java.util.List;

@SuppressWarnings("unchecked")
public class StatisticView extends BaseView {

    private @FXML Parent rootFXML;
    private @FXML ComboBox externeContentDilemmaList;
    private @FXML ComboBox antwoordenDilemmaList;
    private @FXML ComboBox tijdstipDilemmaList;
    private @FXML ComboBox terugKoppelingList;
    private @FXML BarChart tijdstipChart;
    private @FXML PieChart antwoordenChart;
    ObservableList<PieChart.Data> antwoordenChartList;

    private final StatisticController statisticController;

    public StatisticView(StatisticController statisticController) {
        this.statisticController = statisticController;
        rootFXML = super.loadFXML("../fxml/statistics.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);
        antwoordenChartList = FXCollections.observableArrayList();
        antwoordenChart.setData(antwoordenChartList);
        externeContentDilemmaList.valueProperty().addListener((ChangeListener<Dilemma>) (observableDilemma, oldDilemma, newDilemma) -> {
            statisticController.resetModel();
            statisticController.filterByDilemma(newDilemma);
        });
        applyStyling();
        makeSyncable();
    }

    private void applyStyling() {
        externeContentDilemmaList.setCellFactory(lv -> createListCell());
        externeContentDilemmaList.setButtonCell(createListCell());
        antwoordenDilemmaList.setCellFactory(lv -> createListCell());
        antwoordenDilemmaList.setButtonCell(createListCell());
        tijdstipDilemmaList.setCellFactory(lv -> createListCell());
        tijdstipDilemmaList.setButtonCell(createListCell());
        terugKoppelingList.setCellFactory(lv -> createListCell());
        terugKoppelingList.setButtonCell(createListCell());
        tijdstipChart.getYAxis().setLabel("Aantal");
        tijdstipChart.getXAxis().setLabel("Uur");
        tijdstipChart.setAnimated(false);
        tijdstipChart.setBarGap(0);
        tijdstipChart.setCategoryGap(0);
        tijdstipChart.setLegendVisible(false);
    }

    private ListCell<Dilemma> createListCell() {
        return new ListCell<Dilemma>() {
            @Override
            protected void updateItem(Dilemma dilemma, boolean empty) {
                super.updateItem(dilemma, empty);

                if (empty || dilemma == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(dilemma.getTheme());
                }
            }
        };
    }

    public void addDilemmaToList(List<Dilemma> dilemmaList) {
        externeContentDilemmaList.getItems().setAll(dilemmaList);
        antwoordenDilemmaList.getItems().setAll(dilemmaList);
        tijdstipDilemmaList.getItems().setAll(dilemmaList);
        terugKoppelingList.getItems().setAll(dilemmaList);
        externeContentDilemmaList.getSelectionModel().selectFirst();
    }

    private void makeSyncable() {
        terugKoppelingList.selectionModelProperty().bindBidirectional(externeContentDilemmaList.selectionModelProperty());
        externeContentDilemmaList.selectionModelProperty().bindBidirectional(antwoordenDilemmaList.selectionModelProperty());
        antwoordenDilemmaList.selectionModelProperty().bindBidirectional(tijdstipDilemmaList.selectionModelProperty());
    }

    public void modelUpdated(StatisticModel statisticModel) {
        updateAnswerChart(statisticModel);
        updatetijdstipChart(statisticModel);
    }

    private void updatetijdstipChart(StatisticModel statisticModel) {
        List<Result> results = statisticModel.getFilteredResults();
        tijdstipChart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        for (int hour = 0; hour < 24; hour++) {
            int aantal = 0;
            for (Result result: results) {
                if (result.getAnsweredTime().getHours() == hour) {
                    aantal++;
                }
                series.getData().add(new XYChart.Data(Integer.toString(hour), aantal));
            }
        }
        tijdstipChart.getData().add(series);
    }

    private void updateAnswerChart(StatisticModel statisticModel) {
        List<Answer> answers = statisticModel.getFilteredAnswers();
        List<Result> results = statisticModel.getFilteredResults();
        antwoordenChartList.clear();
        for (Answer answer: answers) {
            PieChart.Data data = new PieChart.Data(answer.getText(), results.stream().filter(result -> result.getAnswer_id() == answer.getId()).count());
            antwoordenChartList.add(data);
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                //TODO Apply some kind of filter
                System.out.println("TODO CREATE FILTER WITH Dilemma:" + answer.getId() + " AND ANSWER: " + answer.getId());
            });
            super.setScaleTransitions(data.getNode(), 1.05);
        }
    }

    public void handleBackBtnClick() {
        statisticController.handleBackBtnClick();
    }
}

