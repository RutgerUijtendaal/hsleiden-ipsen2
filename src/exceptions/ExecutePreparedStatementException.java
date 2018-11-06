package exceptions;

public class ExecutePreparedStatementException extends PreparedStatementException {

    private final static long serialVersionUID = 25004L;

    public ExecutePreparedStatementException() {
        super("Failed to execute a PreparedStatement");
    }

}
