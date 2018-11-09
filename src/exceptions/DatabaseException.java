package exceptions;

/**
 * Custom exception for better error handling
 *
 * @author Bas de Bruyn
 */
public class DatabaseException extends ApplicationException {

    private final static long serialVersionUID = 25002L;

    DatabaseException(String message) {
        super(message);
    }

}
