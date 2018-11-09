package exceptions;


/**
 * Custom exception for better error handling
 *
 * @author Bas de Bruyn
 */
public class NoFurtherResultsException extends DatabaseException{

    private final static long serialVersionUID = 25010L;

    public NoFurtherResultsException(){
        super("The ResultSet you tried to read from has no further results");
    }

}
