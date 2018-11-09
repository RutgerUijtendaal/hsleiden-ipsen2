package util;

public class AdminLoginSubmitData extends SubmitData {

    private final String email;
    private final String password;

    public AdminLoginSubmitData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean dataIsValid() {
        if(!InputValidator.isValidEmail(email)) {
            errorMessage = "Voer een correct e-mailadres in.";
            return false;
        }

        if(!InputValidator.isValidString(password)) {
            errorMessage = "Wachtwoord mag niet leeg zijn.";
            return false;
        }

        return true;
    }
}
