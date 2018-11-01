package util;

import models.Admin;

import java.sql.Date;

public class EditAdminSubmitData extends AdminSubmitData {

    public EditAdminSubmitData(String email, String password, int rightsId) {
        super(email, password, rightsId);
    }

    @Override
    public boolean dataIsValid() {
        if(!InputValidator.isValidEmail(email)) {
            errorMessage = "Voer een correct e-mailadres in.";
            return false;
        }

        if(!InputValidator.isValidEditPassword(password)) {
            errorMessage = "Wachtwoord moet minimaal 4 tekens zijn.";
            return false;
        }

        return true;
    }
}
