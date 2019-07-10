package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Thrown when an application attempts to get next token in a case where that 
 * token has an error in it or there is no next token.
 * @author Matija
 *
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a LexerException with no detail message.
	 */
	public LexerException() {
	}

	/**
	 * Constructs a LexerException with the specified detail message.
	 * @param message the detail message
	 */
	public LexerException(String message) {
		super(message);
	}

}
