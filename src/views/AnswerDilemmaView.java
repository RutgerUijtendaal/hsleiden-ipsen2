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

/**
 * View for answering dilemmas
 * @author Danny van Tol
 */
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
        applyTransitions();
    }

    /**
     * Applies transitions to all the elements
     */
    private void applyTransitions() {
        super.setScaleTransitions(answerOneBox, SMALL_SCALE);
        super.setScaleTransitions(answerTwoBox, SMALL_SCALE);

        super.setScaleTransitions(answerBtn, SCALE);
        super.setScaleTransitions(backBtn, SCALE);
        super.setScaleTransitions(childBornBtn, SCALE);

    }

    /**
     * Handles the selecting of answer one
     */
    public void selectAnswerOne() {
        answerDilemmaController.selectAnswer(1);

        answerOneBox.setStyle(HIGHLIGHT);
        answerTwoBox.setStyle(EMPTY);
    }

    /**
     * Handles the selecting of answer one
     */
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

    /**
     * Handles the back button
     */
    public void back() { answerDilemmaController.goBack(); }

    /**
     * Handles the child born button
     */
    public void childBorn() { answerDilemmaController.setChildBorn(); }

    /**
     * Fills the view with the dillema
     * @param dilemmaContent Dillema to show
     */
    public void setDilemmaContent(Dilemma dilemmaContent) {
        theme.setText(dilemmaContent.getTheme());
    }

    /**
     * Fills the view with answers
     * @param answers Answers to show
     */
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

    /**
     * Show popup when there is no dilemma available
     */
    public void noDilemmaAvailable() {
        this.displayPopup("Er is geen dilemma beschikbaar");
    }

    /**
     * Hides the the child button
     */
    public void childIsBorn() {
        childBornBtn.setVisible(false);
    }
}

