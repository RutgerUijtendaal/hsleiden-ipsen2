package exceptions;

public class FillPreparedStatementException extends PreparedStatementException {

    private final static long serialVersionUID = 25005L;

    public FillPreparedStatementException() {
        super("Failed to fill a ");
    }

}
