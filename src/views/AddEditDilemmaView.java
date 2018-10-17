package views;

import controllers.AddDilemmaController;
import controllers.EditDilemmaController;
import controllers.DilemmaController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.stage.FileChooser;
import util.DilemmaSubmitData;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import models.Answer;
import models.Dilemma;

public class AddEditDilemmaView extends BaseView {

    private Desktop desktop = Desktop.getDesktop();

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

    private File file1;
    private File file2;

    private DilemmaController dc;

    public AddEditDilemmaView(DilemmaController dc) {
        this.dc = dc;
        rootFXML = super.loadFXML("../fxml/add_dilemma.fxml");
        rootScene = new Scene(rootFXML, 1280, 720);

        double smallChange = 1.05;

        super.setScaleTransitions(theme, smallChange);
        super.setScaleTransitions(feedback, smallChange);
        super.setScaleTransitions(antwoord1text, smallChange);
        super.setScaleTransitions(antwoord2text, smallChange);
        super.setScaleTransitions(week, smallChange);
        super.setScaleTransitions(choosePicture1Btn, smallChange);
        super.setScaleTransitions(choosePicture2Btn, smallChange);

        super.setScaleTransitions(submitBtn, smallChange);
        super.setScaleTransitions(backBtn, smallChange);

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

    public void fillFields(Dilemma dilemma, Answer[] answers) {
        theme.setText(dilemma.getTheme());
        feedback.setText(dilemma.getFeedback());
        antwoord1text.setText(answers[0].getText());
        antwoord2text.setText(answers[1].getText());
        week.setText(String.valueOf(dilemma.getWeekNr()));
    }

}

