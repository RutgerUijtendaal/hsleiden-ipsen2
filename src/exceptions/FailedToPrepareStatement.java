package exceptions;

public class FailedToPrepareStatement extends PreparedStatementException {

    private final static long serialVersionUID = 25007L;

    public FailedToPrepareStatement() {
        super("Failed to prepare a statement");
    }

}
