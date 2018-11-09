package exceptions;

/**
 * Custom exception for better error handling
 *
 * @author Bas de Bruyn
 */
public class ExecutePreparedStatementException extends PreparedStatementException {

    private final static long serialVersionUID = 25004L;

    public ExecutePreparedStatementException() {
        super("Failed to execute a PreparedStatement");
    }

}
