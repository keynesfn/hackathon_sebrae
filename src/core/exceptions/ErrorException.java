package core.exceptions;

public class ErrorException extends RuntimeException {

	public ErrorException() {
		super();
	}

	public ErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ErrorException(String message) {
		super(message);
	}

	public ErrorException(Throwable cause) {
		super(cause);
	}
	

}
