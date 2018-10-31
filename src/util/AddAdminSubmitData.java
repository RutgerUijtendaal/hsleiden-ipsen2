package util;

import models.Admin;

import java.sql.Date;

public class AddAdminSubmitData extends SubmitData {

    private int id;
    private String email;
    private String password;
    private Boolean isStatistics;
    private Boolean isAddEdit;

    public AddAdminSubmitData(String email, String password, Boolean isStatistics, Boolean isAddEdit) {
        this.email = email;
        this.password = password;
        this.isStatistics = isStatistics;
        this.isAddEdit = isAddEdit;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getStatistics() {
        return isStatistics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getAddEdit() { return isAddEdit; }

    public Admin getAdmin(String passwordHash) {
        return new Admin(email, passwordHash, getRightsId(), new Date(System.currentTimeMillis()));
    }

    /**
     * Get ID of the admin rights combination based on Database rights table layout.
     *
     * Id   isAddEdit   isStatistics
     * 2         true          false
     * 3         true           true
     * 4        false           true
     * 5        false          false
     */

    public void setRightsFromId(int id) throws RuntimeException {
        switch (id) {
            case 2:
                isAddEdit = true;
                isStatistics = false;
                break;
            case 3:
                isAddEdit = true;
                isStatistics = true;
                break;
            case 4:
                isAddEdit = false;
                isStatistics = true;
                break;
            case 5:
                isAddEdit = false;
                isStatistics = false;
                break;
            default:
                throw new RuntimeException();
        }
    }

    public int getRightsId() {
        if(isAddEdit) {
            if(isStatistics) {
                return 3;
            }
            return 2;
        } else {
            if(isStatistics) {
                return 4;
            }
            return 5;
        }
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

        if(!isStatistics && !isAddEdit) {
            errorMessage = "Beheerder moet rechten hebben";
            return false;
        }

        return true;
    }
}
