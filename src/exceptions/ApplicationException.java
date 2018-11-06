package exceptions;

public class ApplicationException extends RuntimeException {

    private final static long serialVersionUID = 25000L;

    public ApplicationException(String message) {
        super(message);
    }
}
