package util;

import models.Child;
import models.Couple;
import models.Parent;

import java.time.LocalDate;
import java.sql.Date;

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
        if(!isValidName(pOneName) || !isValidName(pTwoName)) {
            errorMessage = "Voer een naam in.";
            return false;
        }

        if(!isValidEmail(pOneEmail) || !isValidEmail(pTwoEmail)) {
            errorMessage = "Voer een correct e-mail address in.";
            return false;
        }

        // Have to check phone numbers separately in case one needs conversion
        if(!isValidInternationalPhone(pOnePhone)) {
            if(!isValidLocalPhone(pOnePhone)) {
                errorMessage = "Voer een correct telefoonnummer in.";
                return false;
            } else {
                pOnePhone = localPhoneToInternational(pOnePhone);
            }
        }

        if(!isValidInternationalPhone(pTwoPhone)) {
            if(!isValidLocalPhone(pTwoPhone)) {
                errorMessage = "Voer een correct telefoonnummer in.";
                return false;
            } else {
                pTwoPhone = localPhoneToInternational(pTwoPhone);
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



    /**
     * Check if a name is not empty and at least 2 characters long
     *
     * @param name Name to validate.
     * @return true if name is valid.
     */
    private boolean isValidName(String name) {
        return isNotEmpty(name) && name.length() > 2;
    }

    /**
     * Check if an email is in a valid format
     *
     * @param email email to validate.
     * @return True if email is valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        // Regex pattern from: http://emailregex.com
        String ePattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Check if a phone number is a valid Dutch local format.
     *
     * @param phone Phone number to validate.
     * @return true if phone number is a valid local nr.
     */
    private boolean isValidLocalPhone(String phone) {
        String pPatternLocal = "^06\\d{8}$";
        java.util.regex.Pattern pLocal = java.util.regex.Pattern.compile(pPatternLocal);
        java.util.regex.Matcher mLocal = pLocal.matcher(phone);
        return mLocal.matches();
    }

    /**
     * Check if a phone number is in a valid Dutch international format.
     *
     * @param phone Phone number to validate.
     * @return true if phone number is a valid international nr.
     */
    private boolean isValidInternationalPhone(String phone) {
        // ^\+\d{11}$ -> validates +31652240372
        String pPatternInternational = "^\\+\\d{11}$";
        java.util.regex.Pattern pInternational = java.util.regex.Pattern.compile(pPatternInternational);
        java.util.regex.Matcher mInternational = pInternational.matcher(phone);
        return mInternational.matches();
    }

    /**
     * Convert a local Dutch phone number to an international Dutch phone number.
     *
     * @param localPhone Local formatted phone number eg: "0652240274".
     * @return Internationally formatted phone number eg: "+31652240274".
     */
    private String localPhoneToInternational(String localPhone) {
        String temp = localPhone.substring(1);
        return "+31" + temp;
    }

    /**
     * Check if a String is not empty.
     *
     * @param s String to check.
     * @return true if s is not empty.
     */
    private boolean isNotEmpty(String s) {
        return !s.trim().isEmpty();
    }
}
