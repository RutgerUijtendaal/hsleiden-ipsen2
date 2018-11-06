package exceptions;

public class PrepareStatementException extends PreparedStatementException {

    private final static long serialVersionUID = 25007L;

    public PrepareStatementException() {
        super("Failed to prepare a statement");
    }

}
