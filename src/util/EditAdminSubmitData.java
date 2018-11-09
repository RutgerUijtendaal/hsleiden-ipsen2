package util;

/**
 * Class responsible for holding the AddEditAdminView submit data
 *
 * @see views.AddEditAdminView
 *
 * @author Jordi Dorren
 */
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

        if(!InputValidator.isValidEditPassword(password)) {
            errorMessage = "Wachtwoord moet minimaal 4 tekens zijn.";
            return false;
        }

        return true;
    }

}
