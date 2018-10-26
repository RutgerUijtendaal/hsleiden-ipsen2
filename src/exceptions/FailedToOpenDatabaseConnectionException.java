package exceptions;

public class FailedToOpenDatabaseConnectionException extends DatabaseConnectionException {

    private final static long serialVersionUID = 25006L;

    public FailedToOpenDatabaseConnectionException() {
        super("Cannot make a connection to the database");
    }

}
