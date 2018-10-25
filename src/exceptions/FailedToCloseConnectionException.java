package exceptions;

public class FailedToCloseConnectionException extends DatabaseConnectionException {

    private final static long serialVersionUID = 25003L;

    public FailedToCloseConnectionException() {
        super("Failed to close the connection to the database");
    }

}
