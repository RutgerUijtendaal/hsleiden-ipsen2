package exceptions;

public class FailedToFillPreparedStatementException extends PreparedStatementException {

    public FailedToFillPreparedStatementException() {
        super("Failed to fill a ");
    }

}
