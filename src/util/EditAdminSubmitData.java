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

        return true;
    }

}
