package util;

import models.Child;
import models.Couple;
import models.Parent;

import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;

public class CoupleSubmitData {

    public String errorMessage;

    private String pOneName;
    private String pTwoName;
    private String pOneEmail;
    private String pTwoEmail;
    private String pOnePhone;
    private String pTwoPhone;
    private LocalDate cDate;
    private Boolean cIsBorn;

    public CoupleSubmitData(String pOneName, String pTwoName, String pOneEmail, String pTwoEmail, String pOnePhone, String pTwoPhone, LocalDate cDate, Boolean cIsBorn) {
        this.pOneName = pOneName;
        this.pTwoName = pTwoName;
        this.pOneEmail = pOneEmail;
        this.pTwoEmail = pTwoEmail;
        this.pOnePhone = pOnePhone;
        this.pTwoPhone = pTwoPhone;
        this.cDate = cDate;
        this.cIsBorn = cIsBorn;
    }

    public Parent getParentOne() {
        return new Parent(pOnePhone, pOneName, pOneEmail);
    }

    public Parent getParentTwo() {
        return new Parent(pTwoPhone, pTwoName, pTwoEmail);
    }

    public ArrayList<Parent> getParents() {
        ArrayList<Parent> parents = new ArrayList<>();
        parents.add(getParentOne());
        parents.add(getParentTwo());
        return parents;
    }

    public Couple getCouple(int parentOneId, int parentTwoId) {
        return new Couple(new Date(System.currentTimeMillis()), parentOneId, parentTwoId);
    }

    public Child getChild(int coupleId) {
        return new Child(coupleId, java.sql.Date.valueOf(cDate), cIsBorn);
    }

    /**
     * Checks if all the data is valid. If invalid data is found an error message
     * is set.
     *
     * @return true if all data is valid, false otherwise
     */
    public boolean dataIsValid() {
        if(!InputValidator.isValidName(pOneName) || !InputValidator.isValidName(pTwoName)) {
            errorMessage = "Voer een naam in.";
            return false;
        }

        if(!InputValidator.isValidEmail(pOneEmail) || !InputValidator.isValidEmail(pTwoEmail)) {
            errorMessage = "Voer een correct e-mail address in.";
            return false;
        }

        // Have to check phone numbers separately in case one needs conversion
        if(!InputValidator.isValidInternationalPhone(pOnePhone)) {
            if(!InputValidator.isValidLocalPhone(pOnePhone)) {
                errorMessage = "Voer een correct telefoonnummer in.";
                return false;
            } else {
                pOnePhone = InputValidator.localPhoneToInternational(pOnePhone);
            }
        }

        if(!InputValidator.isValidInternationalPhone(pTwoPhone)) {
            if(!InputValidator.isValidLocalPhone(pTwoPhone)) {
                errorMessage = "Voer een correct telefoonnummer in.";
                return false;
            } else {
                pTwoPhone = InputValidator.localPhoneToInternational(pTwoPhone);
            }
        }

        if(cDate == null) {
            if(cIsBorn) {
                errorMessage = "Voer een geboortedatum in.";
            } else {
                errorMessage = "Voer een uitgerekende datum in.";
            }
            return false;
        }

        return true;

    }
}
