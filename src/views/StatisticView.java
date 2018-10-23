package views;

import controllers.StatisticController;
import daos.DaoManager;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import models.Answer;
import models.Dilemma;

import java.util.List;

public class StatisticView extends BaseView {

    private @FXML Parent rootFXML;
    private @FXML ComboBox externeContentDilemmaList;
    private @FXML ComboBox antwoordenDilemmaList;
    private @FXML ComboBox tijdstipDilemmaList;
    private @FXML ComboBox terugKoppelingList;
    private @FXML ComboBox tijdStipEenheid;
    private @FXML BarChart tijdstipChart;
    private @FXML PieChart externeContentChart;

    private final StatisticController statisticController;

    public StatisticView(StatisticController statisticController) {
        this.statisticController = statisticController;
        rootFXML = super.loadFXML("../fxml/statistics.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);
        tijdStipEenheid.getItems().add("Dag");
        tijdStipEenheid.getItems().add("Uur");
        externeContentDilemmaList.valueProperty().addListener((ChangeListener<Dilemma>) (observableValue, oldValue, newValue) -> {
            System.out.println(newValue.getId() + ":ID");
            System.out.println(externeContentDilemmaList.getValue());
            ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
            System.out.println(newValue.getId());
            Answer[] answers = DaoManager.getAnswerDao().getByDilemmaId(newValue.getId());
            externeContentChart.setData(list);
            for (Answer answer : answers) {
                PieChart.Data data = new PieChart.Data(answer.getText(), (int)(Math.random()*10));
                System.out.println(data.getNode());
                list.add(data);
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    //TODO Apply some kind of filter
                    System.out.println("TODO CREATE FILTER WITH Dilemma:" + newValue.getId() + " AND ANSWER: " + answer.getId());
                });
            }
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
}

