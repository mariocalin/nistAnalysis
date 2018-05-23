package nist.functions;

import java.io.IOException;

/**
 * Exception for Nist parsers
 * 
 * @author mario
 *
 */
public class NistException extends Exception {

	private static final long serialVersionUID = 1L;

	public NistException(String msg) {
		super(msg);
	}

	public NistException(IOException e) {
		super(e);
	}

}
