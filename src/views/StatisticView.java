package views;

import controllers.StatisticController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class StatisticView extends BaseView {

    private @FXML
    Parent rootFXML;

    private final StatisticController statisticController;

    public StatisticView(StatisticController statisticController) {
        this.statisticController = statisticController;
        rootFXML = super.loadFXML("../fxml/statistics.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);
    }
}

