package exception;
/**
* Class create exception DataAccessException.
* This exception calls, when we can't work with Data Source.
*/
public class DataAccessException extends Exception {
    public static final long serialVersionUID = 1l;
    public DataAccessException() {
        super();
    }
    public DataAccessException(String info) {
        super(info);
    }
    public DataAccessException(String message, Throwable cause) {
        super (message, cause);
    }
    public DataAccessException(Throwable cause) {
        super(cause);
    }
}