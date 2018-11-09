package exceptions;

/**
 * Custom exception for better error handling
 *
 * @author Bas de Bruyn
 */
public class OpenDatabaseConnectionException extends DatabaseConnectionException {

    private final static long serialVersionUID = 25006L;

    public OpenDatabaseConnectionException() {
        super("Cannot make a connection to the database");
    }

}
