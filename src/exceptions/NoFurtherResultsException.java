package exceptions;

public class NoFurtherResultsException extends DatabaseException{

    public NoFurtherResultsException(){
        super("The ResultSet you tried to read from has no further results");
    }

}
