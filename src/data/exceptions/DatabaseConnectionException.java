package data.exceptions;

public class DatabaseConnectionException extends DatabaseException {

    private final static long serialVersionUID = 25001L;

    public DatabaseConnectionException(String message) {
        super(message);
    }

}
