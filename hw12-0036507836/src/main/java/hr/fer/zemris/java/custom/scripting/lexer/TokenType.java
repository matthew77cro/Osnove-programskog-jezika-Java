package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * 
 * Enum used for type of a token for hr.fer.zemris.java.custom.scripting.lexer.Lexer
 * 
 * @author Matija
 *
 */
public enum TokenType {
	
	TEXT, // everything not inside tag declaration
	WORD, // everything inside tag declaration consisting only of characters ( starts by letter after which follows zero or more letters, digits or underscores )
	NUMBER_INT, // everything inside tag declaration matching regex : [0-9]+
	NUMBER_DOUBLE, // everything inside tag declaration matching regex : [0-9]+(.[0-9]+)?
	STRING, // everything inside tag declaration surrounded with quotes
	SYMBOL, // everything else inside tag declaration
	TAG_START, // "{$"
	TAG_END, // "$}"
	EOF; // end of file token

}
