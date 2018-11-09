package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
class InputValidator {

    /**
     * Check if a String is not empty
     *
     * @param string String to validate
     * @return true if string is not empty, false otherwise
     */
    public static boolean isValidString(String string) {
        return isNotEmpty(string);
    }

    /**
     * Check if a password is valid by checking length. Min length set to 3 chars.
     *
     * @param password Password to validate
     * @return true if pass is of min length, false otherwise
     */
    public static boolean isValidPassword(String password) {
        return password.length() > 3;
    }

    public static boolean isValidEditPassword(String password) {
        return (password.length() > 3) || password.isEmpty();
    }

    /**
     * Check if a name is not empty and only contains letters.
     *
     * @param name Name to validate.
     * @return true if name is valid, false otherwise.
     */
    public static boolean isValidName(String name) {
        String nPattern = "^[a-zA-Z ]*$";
        Pattern p = Pattern.compile(nPattern);
        Matcher m = p.matcher(name);
        return isNotEmpty(name) && m.matches();
    }

    /**
     * Check if a week number is not empty, 1 or 2 numbers and only numbers.
     *
     * @param weekNr week nr to check
     * @return true if week nr is valid, false otherwise.
     */
    public static boolean isValidWeekNr(String weekNr) {
        String nPattern = "^[0-9]*$";
        Pattern p = Pattern.compile(nPattern);
        Matcher m = p.matcher(weekNr);
        return isNotEmpty(weekNr) && m.matches() && weekNr.length() < 3;
    }

    /**
     * Check if an email is in a valid format
     *
     * @param email email to validate.
     * @return True if email is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        // Regex pattern from: http://emailregex.com
        String ePattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Check if a phone number is a valid Dutch local format.
     *
     * @param phone Phone number to validate.
     * @return true if phone number is a valid local nr.
     */
    public static boolean isValidLocalPhone(String phone) {
        String pPatternLocal = "^06\\d{8}$";
        Pattern pLocal = Pattern.compile(pPatternLocal);
        Matcher mLocal = pLocal.matcher(phone);
        return mLocal.matches();
    }

    /**
     * Check if a phone number is in a valid Dutch international format.
     *
     * @param phone Phone number to validate.
     * @return true if phone number is a valid international nr.
     */
    public static  boolean isValidInternationalPhone(String phone) {
        // ^\+\d{11}$ -> validates +31652240372
        String pPatternInternational = "^\\+\\d{11}$";
        Pattern pInternational = Pattern.compile(pPatternInternational);
        Matcher mInternational = pInternational.matcher(phone);
        return mInternational.matches();
    }

    /**
     * Convert a local Dutch phone number to an international Dutch phone number.
     *
     * @param localPhone Local formatted phone number eg: "0652240274".
     * @return Internationally formatted phone number eg: "+31652240274".
     */
    public static String localPhoneToInternational(String localPhone) {
        String temp = localPhone.substring(1);
        return "+31" + temp;
    }

    /**
     * Check if a String is not empty.
     *
     * @param s String to check.
     * @return true if s is not empty.
     */
    private static boolean isNotEmpty(String s) {
        return !s.trim().isEmpty();
    }
}
