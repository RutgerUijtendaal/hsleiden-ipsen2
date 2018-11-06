package exceptions;

public class OpenDatabaseConnectionException extends DatabaseConnectionException {

    private final static long serialVersionUID = 25006L;

    public OpenDatabaseConnectionException() {
        super("Cannot make a connection to the database");
    }

}
