package exceptions;

public class FailedToPrepareStatementException extends PreparedStatementException {

    private final static long serialVersionUID = 25007L;

    public FailedToPrepareStatementException() {
        super("Failed to prepare a statement");
    }

}
