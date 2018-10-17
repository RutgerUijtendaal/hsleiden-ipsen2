package exceptions;

public class FailedToOpenDatabaseConnection extends DatabaseConnectionException {

    public FailedToOpenDatabaseConnection() {
        super("Cannot make a connection to the database");
    }

}
