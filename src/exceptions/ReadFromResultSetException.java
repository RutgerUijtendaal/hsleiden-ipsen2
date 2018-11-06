package exceptions;

public class ReadFromResultSetException extends DatabaseException {

    private final static long serialVersionUID = 25008L;

    public ReadFromResultSetException() {
        super("Failed to read from a ResultSet");
    }

}
