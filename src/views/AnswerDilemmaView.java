package views;

import controllers.AnswerDilemmaController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class AnswerDilemmaView extends BaseView {
    private Scene rootScene;
    private Parent rootFXML;

    private AnswerDilemmaController adc;

    public AnswerDilemmaView(AnswerDilemmaController adc) {
        this.adc = adc;

        this.rootFXML = super.loadFXML("../fxml/answer_dilemma_view.fxml");
        this.rootScene = new Scene(rootFXML, 1280, 720);

    }

    public Scene getViewScene() {
        return rootScene;
    }
}

