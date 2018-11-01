package util;

import models.Admin;

import java.sql.Date;

public class AddAdminSubmitData extends SubmitData {

    private int id;
    private String email;
    private String password;
    private int rightsId;

    public AddAdminSubmitData(String email, String password, int rightsId) {
        this.email = email;
        this.password = password;
        this.rightsId = rightsId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Admin getAdmin(String passwordHash) {
        return new Admin(email, passwordHash, rightsId);
    }

    public int getRightsId() {
        return rightsId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean dataIsValid() {
        if(!InputValidator.isValidEmail(email)) {
            errorMessage = "Voer een correct e-mailadres in.";
            return false;
        }

        if(!InputValidator.isValidPassword(password)) {
            errorMessage = "Wachtwoord moet minimaal 4 tekens zijn.";
            return false;
        }

        return true;
    }
}
