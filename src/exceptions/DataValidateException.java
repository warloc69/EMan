package exceptions;
/**
* Class create exception DataValidateException.
* This exception calls, when got incorrect data.
*/
public class DataValidateException extends Exception {
    public static final long serialVersionUID = 1l;
    public DataValidateException() {
        super();
    }
    public DataValidateException(String info) {
        super(info);
    }
    public DataValidateException(String message, Throwable cause) {
        super (message, cause);
    }
    public DataValidateException(Throwable cause) {
        super(cause);
    }
}