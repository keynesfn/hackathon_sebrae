package core.exceptions;

public class DeveloperException extends RuntimeException {

	public DeveloperException() {
		super();
	}

	public DeveloperException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeveloperException(String message) {
		super(message);
	}

	public DeveloperException(Throwable cause) {
		super(cause);
	}
	

}
