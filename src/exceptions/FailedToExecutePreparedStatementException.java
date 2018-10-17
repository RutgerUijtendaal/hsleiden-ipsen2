package exceptions;

public class FailedToExecutePreparedStatementException extends PreparedStatementException {

    private final static long serialVersionUID = 25004L;

    public FailedToExecutePreparedStatementException() {
        super("Failed to execute a PreparedStatement");
    }

}
