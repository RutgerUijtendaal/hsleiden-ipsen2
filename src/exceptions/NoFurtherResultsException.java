package exceptions;

public class NoFurtherResultsException extends DatabaseException{

    private final static long serialVersionUID = 25010L;

    public NoFurtherResultsException(){
        super("The ResultSet you tried to read from has no further results");
    }

}
