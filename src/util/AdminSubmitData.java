package util;
import models.Admin;

/**
 * Abstract Class providing the base for Add/Edit Admin submit data
 *
 * @see util.AddAdminSubmitData
 * @see util.EditAdminSubmitData
 *
 * @author Jordi Dorren
 * @author Rutger Uijtendaal
 */
public abstract class AdminSubmitData extends SubmitData {

    private int id;
    final String email;
    final String password;
    private final int rightsId;

    AdminSubmitData(String email, String password, int rightsId) {
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
}
