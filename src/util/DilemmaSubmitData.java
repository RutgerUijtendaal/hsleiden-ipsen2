package util;

import java.io.File;

public class DilemmaSubmitData {

    public String errorMessage;

    private String dTheme;
    private String dFeedback;
    private String dWeekNr;
    private String aOneText;
    private String aTwoText;
    private File aOnePicture;
    private File aTwoPicture;

    public DilemmaSubmitData(String theme, String feedback, String weekNr, String answerOneText, String anwserTwoText, File picture1, File picture2) {
        this.dTheme = theme;
        this.dFeedback = feedback;
        this.dWeekNr = weekNr;
        this.aOneText = answerOneText;
        this.aTwoText = anwserTwoText;
        this.aOnePicture = picture1;
        this.aTwoPicture = picture2;
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

        return true;
    }
}
