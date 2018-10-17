package exceptions;

public class FailedToExecutePreparedStatementException extends PreparedStatementException {

    public FailedToExecutePreparedStatementException() {
        super("Failed to execute a PreparedStatement");
    }

}
