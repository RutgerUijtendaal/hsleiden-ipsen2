package util;

import models.Answer;
import models.Dilemma;

import java.io.File;

public class DilemmaSubmitData {

    public String errorMessage;
    public boolean hasPictures;

    private String dTheme;
    private String dFeedback;
    private String dWeekNr;
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

    public Answer getAnswerA(int dilemmaId) {
        return new Answer(dilemmaId, aOneUrl, aOneText);
    }

    public void setAOneUrl(String url) {
        this.aOneUrl = url;
    }

    public void setATwoUrl(String url) {
        this.aTwoUrl = url;
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

    public String getWeekNr() {
        return dWeekNr;
    }

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
        }

        return true;
    }
}
