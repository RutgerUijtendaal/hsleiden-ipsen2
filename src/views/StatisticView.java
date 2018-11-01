package views;

import controllers.StatisticController;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import models.Answer;
import models.Dilemma;
import models.Result;
import models.StatisticModel;
import org.joda.time.DateTime;
import org.joda.time.Hours;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unchecked")
public class StatisticView extends BaseView {

    private @FXML Parent rootFXML;
    private @FXML ComboBox antwoordenDilemmaList;
    private @FXML ComboBox tijdstipDilemmaList;
    private @FXML BarChart tijdstipChart;
    private @FXML BarChart reactiesnelheidChart;
    private @FXML PieChart antwoordenChart;
    ObservableList<PieChart.Data> antwoordenChartList;

    private final StatisticController statisticController;

    public StatisticView(StatisticController statisticController) {
        this.statisticController = statisticController;
        rootFXML = super.loadFXML("../fxml/statistics.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);
        antwoordenChartList = FXCollections.observableArrayList();
        antwoordenChart.setData(antwoordenChartList);
        antwoordenDilemmaList.valueProperty().addListener((ChangeListener<Dilemma>) (observableDilemma, oldDilemma, newDilemma) -> {
            List<Dilemma> dilemmas = new ArrayList<>();
            dilemmas.add(newDilemma);
            statisticController.resetModel();
            statisticController.filterByDilemma(dilemmas);
        });
        applyStyling();
        makeSyncable();
    }

    private void applyStyling() {
        antwoordenDilemmaList.setCellFactory(lv -> createListCell());
        antwoordenDilemmaList.setButtonCell(createListCell());
        tijdstipDilemmaList.setCellFactory(lv -> createListCell());
        tijdstipDilemmaList.setButtonCell(createListCell());
        tijdstipChart.getYAxis().setLabel("Aantal");
        tijdstipChart.getXAxis().setLabel("Uur");
        tijdstipChart.setAnimated(false);
        tijdstipChart.setBarGap(0);
        tijdstipChart.setCategoryGap(0);
        tijdstipChart.setLegendVisible(false);
        antwoordenChart.setLabelsVisible(true);
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
        antwoordenDilemmaList.getItems().setAll(dilemmaList);
        tijdstipDilemmaList.getItems().setAll(dilemmaList);
    }

    private void makeSyncable() {
        antwoordenDilemmaList.selectionModelProperty().bindBidirectional(tijdstipDilemmaList.selectionModelProperty());
    }

    public void modelUpdated(StatisticModel statisticModel) {
        updateAnswerChart(statisticModel);
        updateTimeChart(statisticModel);
        updateReactionTimeChart(statisticModel);
    }

    private void updateReactionTimeChart(StatisticModel statisticModel) {
        List<Result> results = statisticModel.getFilteredResults();
        reactiesnelheidChart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        HashMap mapResults = new HashMap();
        int maxHours = 0;
        for (Result result: results) {
            if (result.getAnsweredTime() != null) {
                DateTime sent = new DateTime(result.getSentTime());
                DateTime answered = new DateTime(result.getAnsweredTime());
                int hoursBetween = Hours.hoursBetween(sent, answered).getHours();
                System.out.println(hoursBetween + " between");
                if (mapResults.get(hoursBetween) == null) {
                    mapResults.put(hoursBetween, 1);
                    maxHours = hoursBetween > maxHours ? hoursBetween : maxHours;
                } else {
                    int amount = (int) mapResults.get(hoursBetween);
                    mapResults.replace(hoursBetween, amount + 1);
                }
//                XYChart.Data data = (XYChart.Data) series.getData().get(hour);
//                data.setYValue((int)data.getYValue() + 1);
//                series.getData().set(hour, data);
            }
            System.out.println(maxHours);
        }
        for (int hour = 0; hour <= maxHours; hour++) {
            int value = mapResults.get(hour) == null ? 0 : (int)mapResults.get(hour);
            series.getData().add(hour, new XYChart.Data(Integer.toString(hour), value));
        }
        System.out.println(series.getData().size());
        reactiesnelheidChart.getData().add(series);
    }

    private void updateTimeChart(StatisticModel statisticModel) {
        List<Result> results = statisticModel.getFilteredResults();
        tijdstipChart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        for (int hour = 0; hour < 24; hour++) {
            series.getData().add(hour, new XYChart.Data(Integer.toString(hour), 0));
        }
        for (Result result: results) {
            if (result.getAnsweredTime() != null) {
                int hour = result.getAnsweredTime().getHours();
                XYChart.Data data = (XYChart.Data) series.getData().get(hour);
                data.setYValue((int)data.getYValue() + 1);
                series.getData().set(hour, data);
            }
        }
    }

    private void updateAnswerChart(StatisticModel statisticModel) {
        List<Answer> answers = statisticModel.getFilteredAnswers();
        List<Result> results = statisticModel.getFilteredResults();
        antwoordenChartList.clear();
        for (Answer answer: answers) {
            PieChart.Data data = new PieChart.Data(answer.getText(), results.stream().filter(result -> result.getAnswer_id() == answer.getId()).count());
            if (data.getPieValue() > 0) {
                antwoordenChartList.add(data);
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    //TODO Apply some kind of filter
                    ArrayList<Answer> answersFitler = new ArrayList<>();
                    answersFitler.add(answer);
                    statisticController.filterByAnswers(answersFitler);
                    System.out.println("TODO CREATE FILTER WITH Dilemma:" + answer.getId() + " AND ANSWER: " + answer.getId());
                });
                super.setScaleTransitions(data.getNode(), 1.05);
            }
        }
    }

    public void handleBackBtnClick() {
        statisticController.handleBackBtnClick();
    }
}

