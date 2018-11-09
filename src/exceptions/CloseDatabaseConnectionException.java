package exceptions;

/**
 * Custom exception for better error handling
 *
 * @author Bas de Bruyn
 */
public class CloseDatabaseConnectionException extends DatabaseConnectionException {

    private final static long serialVersionUID = 25003L;

    public CloseDatabaseConnectionException() {
        super("Failed to close the connection to the database");
    }

}
