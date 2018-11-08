package util;

public class AddAdminSubmitData extends AdminSubmitData {

    public AddAdminSubmitData(String email, String password, int rightsId) {
        super(email, password, rightsId);
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
