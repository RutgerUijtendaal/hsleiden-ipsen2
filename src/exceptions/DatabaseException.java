package exceptions;

public class DatabaseException extends ApplicationException {

    private final static long serialVersionUID = 25002L;

    public DatabaseException(String message) {
        super(message);
    }

}
