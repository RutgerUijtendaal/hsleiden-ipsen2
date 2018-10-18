package ui.addeditdilemma;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import ui.BaseView;
import models.submitdata.DilemmaSubmitData;

import java.awt.*;
import java.io.File;
import java.io.FileFilter;

import models.database.Answer;
import models.database.Dilemma;

public class AddEditDilemmaView extends BaseView {

//    private Desktop desktop = Desktop.getDesktop();

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

    private int answerAId;
    private int answerBId;
    private int dilemmaId;

    private File file1;
    private File file2;

    private DilemmaController dc;

    public AddEditDilemmaView(DilemmaController dc) {
        this.dc = dc;
        rootFXML = super.loadFXML("add_dilemma.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

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

        final FileChooser fileChooser = new FileChooser();
        // Make it so the file picker only accepts pictures
        FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter("Images", "png", "jpg");
        fileChooser.setSelectedExtensionFilter(fileFilter);

        choosePicture1Btn.setOnAction((event) -> {
            file1 = fileChooser.showOpenDialog(null);
            setBtnLayoutUploaded(choosePicture1Btn);

        });

        choosePicture2Btn.setOnAction((event) -> {
            file1 = fileChooser.showOpenDialog(null);
            setBtnLayoutUploaded(choosePicture1Btn);
        });

    }

    public void setController(DilemmaController dilemmaController) {
        this.dc = dilemmaController;
    }

    public void handleBackBtnClick() {
        System.out.println("running handleBackBtnClick in AddEditDilemmaView");
        dc.handleBackBtnClick();
    }

    public void handleSubmitBtnClick() {
        System.out.println("running handleSubmitBtnClick in AddDilemmaView");

        String dTheme = theme.getText();
        String dFeedback = feedback.getText();
        String dWeekNr = week.getText();
        String aOneText = antwoord1text.getText();
        String aTwoText = antwoord2text.getText();
        File aOnePicture = file1;
        File aTwoPicture = file2;

        DilemmaSubmitData dilemmaSubmitData = new DilemmaSubmitData(dTheme, dFeedback, dWeekNr, aOneText, aTwoText, aOnePicture, aTwoPicture);

        if (dilemmaSubmitData.dataIsValid()) {
            // If we're editting a dilemma, we have to add the ID's to both the Answers aswell as the Dilemma so it can update
            if (dc instanceof EditDilemmaController) {
                dilemmaSubmitData.setDilemmaId(this.dilemmaId);
                dilemmaSubmitData.setaOneId(this.answerAId);
                dilemmaSubmitData.setaTwoId(this.answerBId);
            }
        	dc.handleSubmitBtnClick(dilemmaSubmitData);
        } else {
            displayError(dilemmaSubmitData.errorMessage);
        }
    }

    private void setBtnLayoutUploaded(Button btn) {
        // Change the buttons border to green to show the file was successfully selected.
        btn.setStyle("-fx-border-color:green; -fx-background-radius: 15 15 15 15; -fx-background-insets: 1 1 1 1; -fx-border-width: 5px; -fx-border-radius: 5 5 5 5;");

    }

    public void clearFields() {
        theme.clear();
        feedback.clear();
        antwoord1text.clear();
        antwoord2text.clear();
        week.clear();
        file1 = file2 = null;
    }

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

