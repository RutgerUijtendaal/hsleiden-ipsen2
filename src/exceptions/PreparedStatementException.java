package exceptions;

/**
 * Custom exception for better error handling
 *
 * @author Bas de Bruyn
 */
public class PreparedStatementException extends DatabaseException {

    private final static long serialVersionUID = 25009L;

    PreparedStatementException(String message) {
        super(message);
    }

}
