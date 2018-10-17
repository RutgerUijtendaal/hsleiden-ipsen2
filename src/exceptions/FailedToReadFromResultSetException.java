package exceptions;

public class FailedToReadFromResultSetException extends DatabaseException {

    public FailedToReadFromResultSetException() {
        super("Failed to read from a ResultSet");
    }

}
