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
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import models.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class StatisticView extends BaseView {

    private @FXML Parent rootFXML;
    private @FXML ComboBox externeContentDilemmaList;
    private @FXML ComboBox antwoordenDilemmaList;
    private @FXML ComboBox tijdstipDilemmaList;
    private @FXML ComboBox terugKoppelingList;
    private @FXML ComboBox reactionSpeedList;
    private @FXML BarChart tijdstipChart;
    private @FXML BarChart reactieSnelheidChart;
    private @FXML Slider reactieSnelheidSlider;
    private @FXML PieChart antwoordenChart;
    private @FXML PieChart periodeChart;
    private @FXML PieChart ingeschrevenChart;
    ObservableList<PieChart.Data> antwoordenChartList;
    private XYChart.Series reactieSnelheidSeries;

    private final StatisticController statisticController;
    private Collection<Couple> collect;

    public StatisticView(StatisticController statisticController) {
        this.statisticController = statisticController;
        rootFXML = super.loadFXML("../fxml/statistics.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);
        makeDilemmaList();
        applyStyling();
        makeSyncable();
    }

    private void makeDilemmaList() {
        antwoordenChartList = FXCollections.observableArrayList();
        antwoordenChart.setData(antwoordenChartList);
        antwoordenDilemmaList.valueProperty().addListener((ChangeListener<Dilemma>) (observableDilemma, oldDilemma, newDilemma) -> {
            List<Dilemma> dilemmas = new ArrayList<>();
            dilemmas.add(newDilemma);
            dilemmas.remove(null);
            statisticController.resetModel();
            statisticController.filterByDilemma(dilemmas);
        });
    }

    private void applyStyling() {
        externeContentDilemmaList.setCellFactory(lv -> createListCell());
        externeContentDilemmaList.setButtonCell(createListCell());
        antwoordenDilemmaList.setCellFactory(lv -> createListCell());
        antwoordenDilemmaList.setButtonCell(createListCell());
        tijdstipDilemmaList.setCellFactory(lv -> createListCell());
        tijdstipDilemmaList.setButtonCell(createListCell());
        reactionSpeedList.setCellFactory(lv -> createListCell());
        reactionSpeedList.setButtonCell(createListCell());
        terugKoppelingList.setCellFactory(lv -> createListCell());
        terugKoppelingList.setButtonCell(createListCell());
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
        externeContentDilemmaList.getItems().setAll(dilemmaList);
        antwoordenDilemmaList.getItems().setAll(dilemmaList);
        tijdstipDilemmaList.getItems().setAll(dilemmaList);
        reactionSpeedList.getItems().setAll(dilemmaList);
        terugKoppelingList.getItems().setAll(dilemmaList);
    }

    private void makeSyncable() {
        terugKoppelingList.selectionModelProperty().bindBidirectional(externeContentDilemmaList.selectionModelProperty());
        externeContentDilemmaList.selectionModelProperty().bindBidirectional(antwoordenDilemmaList.selectionModelProperty());
        antwoordenDilemmaList.selectionModelProperty().bindBidirectional(tijdstipDilemmaList.selectionModelProperty());
        tijdstipDilemmaList.selectionModelProperty().bindBidirectional(reactionSpeedList.selectionModelProperty());
    }

    public void modelUpdated(StatisticModel statisticModel) {
        updateAnswerChart(statisticModel);
        updatetijdstipChart(statisticModel);
        List<Integer> data = getReactionSpeedList(statisticModel);
        int maxValue = getMaxVal(data);
        updateReactieSnelheidSlider(maxValue);
        setReactieSnelheidData(data, maxValue);
        updatePeriode(statisticModel);
        updateIngeschreven(statisticModel);
        reactieSnelheidSlider.setValue(maxValue);
    }

    private void updatePeriode(StatisticModel statisticModel) {
        List<Child> childeren = statisticModel.getFilteredChildren();
        periodeChart.getData().clear();
        PieChart.Data zwangerData = new PieChart.Data("Zwanger" , 0);
        periodeChart.getData().add(0, zwangerData);
        zwangerData.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            statisticController.filterByBorn(false);
        });
        PieChart.Data geborenData = new PieChart.Data("Geboren", 0);
        periodeChart.getData().add(1, geborenData);
        geborenData.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            statisticController.filterByBorn(true);
        });
        for (Child child: childeren) {
            if (child.getIsBorn()) {
                geborenData.setPieValue(geborenData.getPieValue() + 1);
            } else {
                zwangerData.setPieValue(zwangerData.getPieValue() + 1);
            }

        }
    }

    private void updateIngeschreven(StatisticModel statisticModel) {
        List<Child> childeren = statisticModel.getFilteredChildren();
        List<Couple> couples = statisticModel.getFilteredCouples();
        ingeschrevenChart.getData().clear();
        PieChart.Data voorGeboorte = new PieChart.Data("Voor Geboorte" , 0);
        ingeschrevenChart.getData().add(0, voorGeboorte);
        PieChart.Data naGeboorte = new PieChart.Data("Na Geboorte", 0);
        ingeschrevenChart.getData().add(1, naGeboorte);
        for (Child child: childeren) {
            Couple couple = couples.stream().filter(couple1 -> couple1.getId() == child.getCouple_id()).collect(Collectors.toList()).get(0);
            if (couple.getSignupDate().before(child.getDate()) && child.getIsBorn()) {
                naGeboorte.setPieValue(naGeboorte.getPieValue() + 1);
            } else {
                voorGeboorte.setPieValue(voorGeboorte.getPieValue() + 1);
            }

        }
    }

    private void updatetijdstipChart(StatisticModel statisticModel) {
        List<Result> results = statisticModel.getFilteredResults();
        tijdstipChart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        for (int hour = 0; hour < 24; hour++) {
            int aantal = 0;
            for (Result result: results) {
                if (result.getAnsweredTime()!= null && result.getAnsweredTime().getHours() == hour) {
                    aantal++;
                }
                XYChart.Data data = new XYChart.Data(Integer.toString(hour), aantal);
                series.getData().add(data);
            }
        }
        tijdstipChart.getData().add(series);
        for (Object object: series.getData()) {
            XYChart.Data data = (XYChart.Data) object;
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                statisticController.filterByHour(Integer.valueOf((String) data.getXValue()));
            });
        }
    }

    private void setReactieSnelheidData(List<Integer> data, int xMaxValue){
        reactieSnelheidSeries.getData().clear();

        int [] amount = new int[xMaxValue];

        for(Integer value : data){
            if(value <= xMaxValue) {
                amount[value - 1] += 1;
            }
        }

        for (int i = 0; i < xMaxValue; i++) {
            String strValue = String.valueOf(i + 1);
            reactieSnelheidSeries.getData().add(new XYChart.Data(strValue, amount[i]));
        }

        ((NumberAxis)reactieSnelheidChart.getYAxis()).setUpperBound(getMaxVal(amount)+1);
        ((NumberAxis)reactieSnelheidChart.getYAxis()).setTickUnit((int)(getMaxVal(amount)/ 10.0 + 1));
    }

    private void setReactieSnelheidRange(int range){
        XYChart.Series series = new XYChart.Series();
        series.getData().addAll(reactieSnelheidSeries.getData().subList(0, range));
        reactieSnelheidChart.getData().remove(0);
        reactieSnelheidChart.getData().add(series);
    }

    private List<Integer> getReactionSpeedList(StatisticModel statisticModel){
        List<Integer> data = new ArrayList<>();
        List<Result> results = statisticModel.getFilteredResults();
        for(Result result : results){
            if(result.getAnsweredTime() != null) {
                long time = result.getAnsweredTime().getTime() - result.getSentTime().getTime();
                int hours = (int)TimeUnit.HOURS.convert(time, TimeUnit.MILLISECONDS) + 1;
                data.add(hours);
            }
        }
        return data;
    }

    public void initReactionSpeedChart(){
        reactieSnelheidChart.setAnimated(false);
        reactieSnelheidChart.autosize();
        reactieSnelheidChart.setBarGap(0);
        reactieSnelheidChart.setCategoryGap(0);
        reactieSnelheidChart.getXAxis().setLabel("reactie snelheid (uur)");
        reactieSnelheidChart.getYAxis().setLabel("aantal");
        reactieSnelheidChart.setLegendVisible(false);
        reactieSnelheidSeries = new XYChart.Series();
        reactieSnelheidChart.getData().add(reactieSnelheidSeries);
    }

    private void updateReactieSnelheidSlider(int maxValue){
        reactieSnelheidSlider.setValue(0);
        if(maxValue == 0){
            reactieSnelheidChart.setVisible(false);
            reactieSnelheidSlider.setVisible(false);
        } else if(maxValue == 1){
            reactieSnelheidSlider.setVisible(false);
        } else {
            reactieSnelheidChart.setVisible(true);
            reactieSnelheidSlider.setVisible(true);
            reactieSnelheidSlider.setMajorTickUnit((int) (maxValue / 10.0) > 0 ? (int) (maxValue / 10.0) : 1);
            reactieSnelheidSlider.setMax(maxValue);
            reactieSnelheidSlider.setMin(1);
            reactieSnelheidSlider.setValue(1);
            reactieSnelheidSlider.valueProperty().addListener((ov, old_val, new_val) -> {
                if (new_val.intValue() - old_val.intValue() != 0)
                    setReactieSnelheidRange(new_val.intValue());
            });
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
                    ArrayList<Answer> answersFitler = new ArrayList<>();
                    answersFitler.add(answer);
                    statisticController.filterByAnswers(answersFitler);
                });
                super.setScaleTransitions(data.getNode(), 1.05);
            }
        }
    }

    public int getMaxVal(int[] values){
        Arrays.sort(values);
        if(values.length == 0){
            return 0;
        } else {
            return values[values.length - 1];
        }
    }

    public int getMaxVal(List<Integer> values){
        Collections.sort(values);
        if(values.size() == 0) {
            return 0;
        } else {
            return values.get(values.size() - 1);
        }
    }

    public void handleBackBtnClick() {
        statisticController.handleBackBtnClick();
    }

    public void handleResetFiltersButtonClick(){
        statisticController.handleResetFiltersButtonClick();
        reactionSpeedList.getSelectionModel().clearSelection();
    }
}

