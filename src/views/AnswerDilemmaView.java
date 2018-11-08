package views;

import controllers.AnswerDilemmaController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Answer;
import models.Dilemma;

public class AnswerDilemmaView extends BaseView {

    private Parent rootFXML;

    private static final String HIGHLIGHT =
            "-fx-border-color: #E2B53B; -fx-border-style: solid outside; -fx-border-width: 10;";
    private static final String EMPTY = "";

    private static final float SMALL_SCALE = 1.03f;
    private static final float SCALE = 1.1f;

    @FXML
    private VBox answerOneBox;

    @FXML
    private VBox answerTwoBox;

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

    @FXML
    private Button answerBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Button childBornBtn;

    private AnswerDilemmaController answerDilemmaController;

    public AnswerDilemmaView(AnswerDilemmaController answerDilemmaController) {
        this.answerDilemmaController = answerDilemmaController;

        this.rootFXML = super.loadFXML("../fxml/answer_dilemma_view.fxml");
        this.rootScene = new Scene(rootFXML, 1280, 720);

        super.setScaleTransitions(answerOneBox, SMALL_SCALE);
        super.setScaleTransitions(answerTwoBox, SMALL_SCALE);

        super.setScaleTransitions(answerBtn, SCALE);
        super.setScaleTransitions(backBtn, SCALE);
        super.setScaleTransitions(childBornBtn, SCALE);


    }

    public void selectAnswerOne() {
        answerDilemmaController.selectAnswer(1);

        answerOneBox.setStyle(HIGHLIGHT);
        answerTwoBox.setStyle(EMPTY);
    }

    public void selectAnswerTwo() {
        answerDilemmaController.selectAnswer(2);

        answerOneBox.setStyle(EMPTY);
        answerTwoBox.setStyle(HIGHLIGHT);
    }

    public void answer() {
        answerDilemmaController.processAnswer();
    }

    public void noAnswer() {
        this.displayPopup("U heeft nog geen antwoord gekozen");
    }

    public void back() { answerDilemmaController.goBack(); }

    public void childBorn() { answerDilemmaController.setChildBorn(); }

    public void setDilemmaContent(Dilemma dilemmaContent) {
        theme.setText(dilemmaContent.getTheme());
    }

    public void setAnswers(Answer[] answers) {
        Answer answerOne = answers[0];
        Answer answerTwo = answers[1];

        // TODO: Fix loading images => database only containes extensions
        if (answerOne.getUrl().equals(""))
            answerOneBox.getChildren().remove(imageOne);

        descriptionOne.setText(answerOne.getText());

        // TODO: Fix loading images => database only containes extensions
        if (answerTwo.getUrl().equals("")) {
            answerTwoBox.getChildren().remove(imageTwo);
        }

        descriptionTwo.setText(answerTwo.getText());
    }

    public void noDilemmaAvailable() {
        this.displayPopup("Er is geen dilemma beschikbaar");
    }

    public void childIsBorn() {
        childBornBtn.setVisible(false);
    }
}

