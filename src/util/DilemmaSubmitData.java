package util;

import models.Answer;
import models.Dilemma;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class DilemmaSubmitData extends SubmitData {

    public boolean hasPictures;

    private int dilemmaId;
    private String dTheme;
    private String dFeedback;
    private String dWeekNr;
    private int aOneId;
    private int aTwoId;
    private String aOneText;
    private String aTwoText;
    private String aOneUrl;
    private String aTwoUrl;
    private File aOnePicture;
    private File aTwoPicture;

    public DilemmaSubmitData(String theme, String feedback, String weekNr, String answerAText, String answerBText, File picture1, File picture2) {
        this.dTheme = theme;
        this.dFeedback = feedback;
        this.dWeekNr = weekNr;
        this.aOneText = answerAText;
        this.aTwoText = answerBText;
        this.aOnePicture = picture1;
        this.aTwoPicture = picture2;
        this.aOneUrl = null;
        this.aTwoUrl = null;
        this.hasPictures = false;
    }

    public Dilemma getDilemma() {
        return new Dilemma(Short.parseShort(dWeekNr), dTheme, dFeedback);
    }

    public String getWeekNr() {
        return dWeekNr;
    }

    public int getaOneId() {
        return aOneId;
    }

    public void setaOneId(int aOneId) {
        this.aOneId = aOneId;
    }

    public void setDilemmaId(int dilemmaId) {
        this.dilemmaId = dilemmaId;
    }

    public void setaTwoId(int aTwoId) {
        this.aTwoId = aTwoId;
    }

    public int getaTwoId() {
        return aTwoId;
    }

    public Answer getAnswerA(int dilemmaId) {
        return new Answer(dilemmaId, aOneUrl, aOneText);
    }

    public Answer getAnswerB(int dilemmaId) {
        return new Answer(dilemmaId, aTwoUrl, aTwoText);
    }

    public File getAOnePicture() {
        return aOnePicture;
    }

    public File getATwoPicture() {
        return aTwoPicture;
    }

    public int getDilemmaId() {
        return dilemmaId;
    }
    public void setWeekNr(String weekNr) {
        this.dWeekNr = weekNr;
    }

    @Override
    public boolean dataIsValid() {
        if(!InputValidator.isValidString(dTheme)) {
            errorMessage = "Thema mag niet leeg zijn";
            return false;
        }

        if(!InputValidator.isValidString(dFeedback)) {
            errorMessage = "Feedback mag niet leeg zijn";
            return false;
        }

        if(!InputValidator.isValidString(aOneText)) {
            errorMessage = "Antwoord 1 tekst mag niet leeg zijn";
            return false;
        }

        if(!InputValidator.isValidString(aTwoText)) {
            errorMessage = "Antwoord 2 tekst mag niet leeg zijn";
            return false;
        }

        if(!InputValidator.isValidWeekNr(dWeekNr)) {
            errorMessage =  "Incorrect weeknummer";
            return false;
        }

        if((aOnePicture == null && aTwoPicture != null) || (aOnePicture != null && aTwoPicture == null) ) {
            errorMessage = "Kies voor allebij de antwoorden een plaatje.";
            return false;
        }

        if(aOnePicture != null && aTwoPicture != null) {
            hasPictures = true;

            aOneUrl = FilenameUtils.getExtension(aOnePicture.toString());
            aTwoUrl = FilenameUtils.getExtension(aTwoPicture.toString());
        }

        return true;
    }
}
