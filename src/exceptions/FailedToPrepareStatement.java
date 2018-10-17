package exceptions;

public class FailedToPrepareStatement extends PreparedStatementException {

    public FailedToPrepareStatement() {
        super("Failed to prepare a statement");
    }

}
