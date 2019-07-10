package hr.fer.zemris.java.hw03.prob1;

/**
 * Token used for hr.fer.zemris.java.hw03.prob1.Lexer
 * 
 * @author Matija
 *
 */
public class Token {
	
	private TokenType type;
	private Object value;
	
	/**
	 * Initialises a token with a type and a value.
	 * @param type token type
	 * @param value value of a token
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns a value of a token.
 	 * @return a value of a token.
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Returns a type of a token.
 	 * @return a type of a token.
	 */
	public TokenType getType() {
		return type;
	}
}
