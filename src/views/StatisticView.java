package views;

import controllers.StatisticController;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import models.Dilemma;

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
        externeContentDilemmaList.valueProperty().addListener((ChangeListener<String>) (observableValue, oldValue, newValue) -> {
            System.out.println(newValue);
            if (newValue.equals("Dilemma 1")) {
                ObservableList<PieChart.Data> list =  FXCollections.observableArrayList(new PieChart.Data("Wel", 1), new PieChart.Data("Niet" , 1));
                externeContentChart.setData(list);
            }if (newValue.equals("Dilemma 2")) {
                ObservableList<PieChart.Data> list =  FXCollections.observableArrayList(new PieChart.Data("Wel", 10), new PieChart.Data("Niet" , 3));
                externeContentChart.setData(list);
            }
        });
    }

    public void addDilemmaToList(Dilemma dilemma) {
        externeContentDilemmaList.getItems().add(dilemma.getTheme());
        antwoordenDilemmaList.getItems().add(dilemma.getTheme());
        tijdstipDilemmaList.getItems().add(dilemma.getTheme());
        terugKoppelingList.getItems().add(dilemma.getTheme());
    }
}

