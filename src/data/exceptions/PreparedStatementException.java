package data.exceptions;

public class PreparedStatementException extends DatabaseException {

    private final static long serialVersionUID = 25009L;

    public PreparedStatementException(String message) {
        super(message);
    }

}
