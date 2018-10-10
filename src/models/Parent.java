package models;

public class Parent {

    private String phoneNr;
    private String firstName;

    public Parent(String phoneNr, String firstName) {
        this.phoneNr = phoneNr;
        this.firstName = firstName;
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
}
