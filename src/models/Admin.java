package models;

import java.sql.Date;

public class Admin implements DatabaseObject<Admin> {

    private int id;
    private String email;
    private String password;
    private int right_id;
    private Date signup_date;

    public Admin(String email, String password, int right_id) {
        this.email = email;
        this.password = password;
        this.right_id = right_id;
    }

    public Admin(int id, String email, String password, int right_id) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.right_id = right_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRight_id(int right_id) {
        this.right_id = right_id;
    }

    public void setSignup_date(Date signup_date) {
        this.signup_date = signup_date;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public int getRights_id() {
        return right_id;
    }

    public Date getSignup_date() {
        return signup_date;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", right_id=" + right_id +
                ", signup_date=" + signup_date +
                '}';
    }
}

