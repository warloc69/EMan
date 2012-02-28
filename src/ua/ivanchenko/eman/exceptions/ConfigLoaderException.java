package ua.ivanchenko.eman.exceptions;
/**
* Class create exception ConfigLoaderException.
* This exception calls, when got incorrect configs file.
*/
public class ConfigLoaderException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConfigLoaderException() {
		// TODO Auto-generated constructor stub
	}

	public ConfigLoaderException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ConfigLoaderException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ConfigLoaderException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
