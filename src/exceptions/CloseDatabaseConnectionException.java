package exceptions;

public class CloseDatabaseConnectionException extends DatabaseConnectionException {

    private final static long serialVersionUID = 25003L;

    public CloseDatabaseConnectionException() {
        super("Failed to close the connection to the database");
    }

}