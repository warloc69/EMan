package ua.ivanchenko.eman.exceptions;
/**
* Class create exception CreateProcessorException.
* This exception calls, when we can't made new action processor.
*/
public class CreateProcessorException extends Exception {

	private static final long serialVersionUID = 1L;

	public CreateProcessorException() {
		// TODO Auto-generated constructor stub
	}

	public CreateProcessorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CreateProcessorException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public CreateProcessorException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
