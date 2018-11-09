package views;

import controllers.DilemmaController;
import controllers.EditDilemmaController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.Answer;
import models.Dilemma;
import util.DilemmaSubmitData;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

/**
 * Represent the view for Add and Edit dilemma view
 * @version 1.0
 * @author Jordi Dorren
 * @author Stefan de Keijzer
 * @author Danny van Tol
 */
public class AddEditDilemmaView extends BaseView {

    private @FXML Parent rootFXML;

    private @FXML Button choosePicture1Btn;
    private @FXML Button choosePicture2Btn;
    private @FXML Button submitBtn;
    private @FXML Button backBtn;

    private @FXML TextField theme;
    private @FXML TextField feedback;
    private @FXML TextField antwoord1text;
    private @FXML TextField antwoord2text;
    private @FXML TextField week;
    private @FXML ChoiceBox<String> category;

    private String[] options = new String[]{ "Zwanger", "Geboren" };
    private HashMap<String, Integer> mapper = new HashMap<>();

    private int answerAId;
    private int answerBId;
    private int dilemmaId;

    private File file1;
    private File file2;

    private DilemmaController dilemmaController;

    public AddEditDilemmaView(DilemmaController dilemmaController) {
        this.dilemmaController = dilemmaController;
        rootFXML = super.loadFXML("fxml/add_dilemma.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        applyTransitions();

        final JFileChooser fileChooser = new JFileChooser();
        // Make it so the file picker only accepts pictures
        FileFilter fileFilter = new FileNameExtensionFilter("Images", "png", "jpg");
        fileChooser.setFileFilter(fileFilter);

        choosePicture1Btn.setOnAction((event) -> {
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file1 = fileChooser.getSelectedFile();
                setBtnLayoutUploaded(choosePicture1Btn);
            }
        });

        choosePicture2Btn.setOnAction((event) -> {
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file2 = fileChooser.getSelectedFile();
                setBtnLayoutUploaded(choosePicture2Btn);
            }
        });

        mapper.put(options[0], 0);
        mapper.put(options[1], 15);

        category.setItems(FXCollections.observableArrayList(this.options));
        category.getSelectionModel().select("Zwanger");
    }

    /**
     * Applies the transitions to all the elements in the view
     */
    private void applyTransitions() {
        double smallerChange = 1.03;
        double smallChange = 1.05;
        double bigChange = 1.1;

        super.setScaleTransitions(theme, smallerChange);
        super.setScaleTransitions(feedback, smallerChange);
        super.setScaleTransitions(antwoord1text, smallChange);
        super.setScaleTransitions(antwoord2text, smallChange);
        super.setScaleTransitions(week, bigChange);
        super.setScaleTransitions(choosePicture1Btn, bigChange);
        super.setScaleTransitions(choosePicture2Btn, bigChange);

        super.setScaleTransitions(submitBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);
    }

    /**
     * Changes the controller based on the action needed
     * @param dilemmaController The according controller needed for the action
     */
    public void setController(DilemmaController dilemmaController) {
        this.dilemmaController = dilemmaController;
    }

    /**
     * Handles the button from the fxml file
     */
    public void handleBackBtnClick() {
        dilemmaController.handleBackBtnClick();
    }

    /**
     * Handles the button from the fxml file</br>
     * Submits the data to the controller
     */
    public void handleSubmitBtnClick() {
            String dTheme = theme.getText();
            String dFeedback = feedback.getText();
            String dWeekNr = week.getText();
            String aOneText = antwoord1text.getText();
            String aTwoText = antwoord2text.getText();
            File aOnePicture = file1;
            File aTwoPicture = file2;

            DilemmaSubmitData dilemmaSubmitData = new DilemmaSubmitData(dTheme, dFeedback, dWeekNr, aOneText, aTwoText, aOnePicture, aTwoPicture);

            if (dilemmaSubmitData.dataIsValid()) {
                dilemmaSubmitData.setWeekNr(Integer.toString(Integer.parseInt(week.getText()) + mapper.get(category.getSelectionModel().getSelectedItem())));


                // If we're editting a dilemma, we have to add the ID's to both the Answers aswell as the Dilemma so it can update
                if (dilemmaController instanceof EditDilemmaController) {
                    dilemmaSubmitData.setDilemmaId(this.dilemmaId);
                    dilemmaSubmitData.setaOneId(this.answerAId);
                    dilemmaSubmitData.setaTwoId(this.answerBId);
                }
                dilemmaController.handleSubmitBtnClick(dilemmaSubmitData);
            } else {
                displayError(dilemmaSubmitData.errorMessage);
            }
    }

    private void setBtnLayoutUploaded(Button btn) {
        // Change the buttons border to green to show the file was successfully selected.
        btn.setStyle("-fx-border-color:green; -fx-background-radius: 15 15 15 15; -fx-background-insets: 1 1 1 1; -fx-border-width: 5px; -fx-border-radius: 5 5 5 5;");

    }

    /**
     * Clears field from the view
     */
    public void clearFields() {
        theme.clear();
        feedback.clear();
        antwoord1text.clear();
        antwoord2text.clear();
        week.clear();
        file1 = file2 = null;
    }

    /**
     * Files views with data
     * @param dilemma The data from the dilemma
     * @param answers the answers from the dilemma
     * @param file1 A picture
     * @param file2 A picture
     */
    public void fillFields(Dilemma dilemma, Answer[] answers, File file1, File file2) {

        this.file1 = file1;
        this.file2 = file2;
        this.dilemmaId = dilemma.getId();
        this.answerAId = answers[0].getId();
        this.answerBId = answers[1].getId();

        theme.setText(dilemma.getTheme());
        feedback.setText(dilemma.getFeedback());
        antwoord1text.setText(answers[0].getText());
        antwoord2text.setText(answers[1].getText());
        week.setText(String.valueOf(dilemma.getWeekNr()));

    }
}

