package exceptions;

/**
 * Custom exception for better error handling
 *
 * @author Bas de Bruyn
 */
public class ApplicationException extends RuntimeException {

    private final static long serialVersionUID = 25000L;

    ApplicationException(String message) {
        super(message);
    }
}
