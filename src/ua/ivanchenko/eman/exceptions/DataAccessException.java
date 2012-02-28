package ua.ivanchenko.eman.exceptions;
/**
* Class create exception DataAccessException.
* This exception calls, when can't access to data.
*/
public class DataAccessException extends Exception {

	private static final long serialVersionUID = 1L;

	public DataAccessException() {
		// TODO Auto-generated constructor stub
	}

	public DataAccessException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DataAccessException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
