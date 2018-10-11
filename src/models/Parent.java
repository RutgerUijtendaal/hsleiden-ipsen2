package models;

public class Parent {

    private int id;
    private String phoneNr;
    private String firstName;
    private String email;

    public Parent(int id, String phoneNr, String firstName, String email) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
