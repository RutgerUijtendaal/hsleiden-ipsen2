package exceptions;

public class FailedToCloseConnectionException extends DatabaseConnectionException {

    public FailedToCloseConnectionException() {
        super("Failed to close the connection to the database");
    }

}
