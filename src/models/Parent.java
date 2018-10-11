package models;

public class Parent {

    private int id;
    private String phoneNr;
    private String firstName;
    private String email;

    public Parent(String phoneNr, String firstName, String email) {
        this.phoneNr = phoneNr;
        this.firstName = firstName;
        this.email = email;
    }

    public String getPhoneNr() {
        return phoneNr;
    }
    
    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
