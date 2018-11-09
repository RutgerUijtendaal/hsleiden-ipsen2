package util;

/**
 * Abstract Class providing the base for submit data from Views
 * Every submit data has to validate its own data and provide an errormessage
 * when an error occurs during validation
 *
 * @author Rutger Uijtendaal
 */
public abstract class SubmitData {

    public String errorMessage;

    public abstract boolean dataIsValid();
}
