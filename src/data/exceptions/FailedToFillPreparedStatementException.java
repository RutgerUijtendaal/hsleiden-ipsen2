package data.exceptions;

public class FailedToFillPreparedStatementException extends PreparedStatementException {

    private final static long serialVersionUID = 25005L;

    public FailedToFillPreparedStatementException() {
        super("Failed to fill a ");
    }

}
