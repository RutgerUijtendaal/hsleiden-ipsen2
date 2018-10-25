package exceptions;

public class FailedToReadFromResultSetException extends DatabaseException {

    private final static long serialVersionUID = 25008L;

    public FailedToReadFromResultSetException() {
        super("Failed to read from a ResultSet");
    }

}
