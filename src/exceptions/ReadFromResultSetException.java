package exceptions;

/**
 * Custom exception for better error handling
 *
 * @author Bas de Bruyn
 */
public class ReadFromResultSetException extends DatabaseException {

    private final static long serialVersionUID = 25008L;

    public ReadFromResultSetException() {
        super("Failed to read from a ResultSet");
    }

}
