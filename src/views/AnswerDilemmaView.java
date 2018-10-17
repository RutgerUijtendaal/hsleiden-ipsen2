package views;

import controllers.AnswerDilemmaController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Answer;
import models.Dilemma;

public class AnswerDilemmaView extends BaseView {
    private Scene rootScene;
    private Parent rootFXML;

    private static final String HIGHLIGHT =
            "-fx-border-color: #E2B53B; -fx-border-style: solid outside; -fx-border-width: 10;";
    private static final String EMPTY = "";

    private static final float SCALE = 1.1f;

    @FXML
    private VBox answerOne;

    @FXML
    private VBox answerTwo;

    @FXML
    private ImageView imageOne;

    @FXML
    private ImageView imageTwo;

    @FXML
    private Text descriptionOne;

    @FXML
    private Text descriptionTwo;

    @FXML
    private Text theme;

    private AnswerDilemmaController adc;

    public AnswerDilemmaView(AnswerDilemmaController adc) {
        this.adc = adc;

        this.rootFXML = super.loadFXML("../fxml/answer_dilemma_view.fxml");
        this.rootScene = new Scene(rootFXML, 1280, 720);

        super.setScaleTransitions(answerOne, SCALE);
        super.setScaleTransitions(answerTwo, SCALE);
    }

    public Scene getViewScene() {
        return rootScene;
    }


    public void selectAnswerOne() {
        adc.selectAnswer(1);

        answerOne.setStyle(HIGHLIGHT);
        answerTwo.setStyle(EMPTY);
    }

    public void selectAnswerTwo() {
        adc.selectAnswer(2);

        answerOne.setStyle(EMPTY);
        answerTwo.setStyle(HIGHLIGHT);
    }

    public void answer() {
        adc.processAnswer();
    }

    public void noAnswer() {
        this.displayPopup("U heeft nog geen antwoord gekozen");
    }

    public void setDilemmaContent(Dilemma dilemmaContent) {
        theme.setText(dilemmaContent.getTheme());
    }

    public void setAnswers(Answer[] answers) {
        Answer answerOne = answers[0];
        Answer answerTwo = answers[1];

        descriptionOne.setText(answerOne.getText());
        descriptionTwo.setText(answerTwo.getText());

    }
}

