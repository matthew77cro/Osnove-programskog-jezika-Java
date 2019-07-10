package hr.fer.zemris.java.hw05.lexer;

/**
 * Lexer used for SmartScriptParser. Tokenises given text into lexer tokens of different type.
 * @author Matija
 *
 */
public class Lexer {
	
	/**
	 * input text
	 */
	private char[] data;
	
	/**
	 * current token
	 */
	private Token token;
	
	/**
	 * index of the first unprocessed character
	 */
	private int currentIndex;
	
	/**
	 * This constructor accepts text which needs to be tokenised.
	 * @param text text which to tokenise
	 * @throws NullPointerException if text is null
	 */
	public Lexer(String text) {
		if(text==null) throw new NullPointerException();
		this.data = text.toCharArray();
		this.currentIndex = 0;
	}
	
	/**
	 * Generates and returns next token. Throws LexerExpcetion if any error occurs.
	 * @return next token generated
	 * @throws LexerException if any error occurs (invalid lexer state, eof etc.)
	 */
	public Token nextToken() {
		
		if(currentIndex > data.length) throw new LexerException("EOF exception");
		if(isEof()) {
			eof();
			return token;
		}
		
		consumeNext();

		return token;
		
	}
	
	/**
	 * Returns last generated token, does not generate new one.
	 * @return last generated token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Method generates next tokens.
	 */
	private void consumeNext() {
		
		//whitespace are not relevant
		skipWhiteSpace();
		if(isEof()) {
			eof();
			return;
		}
		
		char nextChar = data[currentIndex];
		
		if(Character.isLetter(nextChar)) {
			word();
		} else if(nextChar=='"') {
			string();
		} else {
			operator();
		}
		
	}
	
	/**
	 * Generates new token which starts with Character.isLetter and contains letters, numbers or _
	 */
	private void word() {
		StringBuilder sb = new StringBuilder();
		
		while(!isEof()) {
			
			char nextChar = data[currentIndex];
			
			if(Character.isLetter(nextChar) || Character.isDigit(nextChar) || nextChar=='_') {
				sb.append(nextChar);
			} else {
				break;
			}
			
			next();
			
		}
		
		String s = sb.toString();
		if(s.equalsIgnoreCase("like") || s.equalsIgnoreCase("and")) {
			token = new Token(TokenType.OPERATOR, s);
			return;
		}
		
		token = new Token(TokenType.ATTRIBUTE, s);
	}
	
	/**
	 * Generates new token which starts with " and ends with "
	 * @throws LexerException if string is never closed
	 */
	private void string() {
		StringBuilder sb = new StringBuilder();
		next(); // skip quotes
		throwLexerException(isEof(), "String never closed.");
		
		for(char nextChar=data[currentIndex]; nextChar!='"'; nextChar=data[currentIndex]) {
			sb.append(nextChar);
			next();
			throwLexerException(isEof(), "String never closed.");
		}
		
		next(); // skip end quotes
		token = new Token(TokenType.STRING, sb.toString());
	}
	
	/**
	 * Generates new token of type symbol
	 * @throws LexerException if symbol is not valid operator
	 */
	private void operator() {		
		char nextChar = data[currentIndex];
		
		// >, <, >=, <=, =, !=
		throwLexerException(nextChar!='>' && nextChar!='<' && nextChar!='=' && nextChar!='!', "Symbol is not a valid operator. Got: " + nextChar);
		next();
		if(nextChar == '=') {
			token = new Token(TokenType.OPERATOR, Character.valueOf((nextChar)));
			return;
		}
		
		char after = data[currentIndex];
		//if it is combined of two symbols >=, <= or !=
		if(after == '=') {
			next();
			token = new Token(TokenType.OPERATOR, Character.toString(nextChar) + Character.toString(after));
			return;
		}
		
		token = new Token(TokenType.OPERATOR, Character.valueOf((nextChar)));
	}
	
	/**
	 * Skips all characters that are considered white space.
	 */
	private void skipWhiteSpace() {
		char nextChar = data[currentIndex];
		while(Character.isWhitespace(nextChar)) {
			next();
			if(isEof()) break;
			nextChar = data[currentIndex];
		}
	}
	
	/**
	 * Increments current index so it points to the next character.
	 */
	private void next() {
		currentIndex++;
	}
	

	private boolean isEof() {
		return currentIndex >= data.length;
	}
	
	private void eof() {
		token = new Token(TokenType.EOF, null);
		currentIndex++;
	}
	
	private void throwLexerException(boolean whenToThrow, String msg) {
		if(whenToThrow) throw new LexerException(msg);
	}

}

