package exceptions;

/**
 * Custom exception for better error handling
 *
 * @author Bas de Bruyn
 */
public class PrepareStatementException extends PreparedStatementException {

    private final static long serialVersionUID = 25007L;

    public PrepareStatementException() {
        super("Failed to prepare a statement");
    }

}
