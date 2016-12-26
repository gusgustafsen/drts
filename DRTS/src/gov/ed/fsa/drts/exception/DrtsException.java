package gov.ed.fsa.drts.exception;

public class DrtsException extends Exception {

	private static final long serialVersionUID = 7226037873946300151L;

	public DrtsException() {
		super();
	}

	public DrtsException(final Throwable t) {
		super();
	}

	public DrtsException(final String message) {
		super(message);
	}

	public DrtsException(final String message, final Throwable t) {
		super(message, t);
	}
}
