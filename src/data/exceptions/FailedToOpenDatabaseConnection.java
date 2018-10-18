package data.exceptions;

public class FailedToOpenDatabaseConnection extends DatabaseConnectionException {

    private final static long serialVersionUID = 25006L;

    public FailedToOpenDatabaseConnection() {
        super("Cannot make a connection to the database");
    }

}
