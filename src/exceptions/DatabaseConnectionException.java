package exceptions;

/**
 * Custom exception for better error handling
 *
 * @author Bas de Bruyn
 */
public class DatabaseConnectionException extends DatabaseException {

    private final static long serialVersionUID = 25001L;

    DatabaseConnectionException(String message) {
        super(message);
    }

}
