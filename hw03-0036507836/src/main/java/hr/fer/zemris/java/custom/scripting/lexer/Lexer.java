package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Lexer used for SmartScriptParser. Tokenises given text into lexer tokens of different type.
 * @author Matija
 *
 */
public class Lexer {
	
	private char[] data; // ulazni tekst
	private Token token; // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	private LexerState state;
	
	// konstruktor prima ulazni tekst koji se tokenizira
	/**
	 * This constructor accepts text which needs to be tokenised.
	 * @param text text which to tokenise
	 * @throws NullPointerException if text is null
	 */
	public Lexer(String text) {
		if(text==null) throw new NullPointerException();
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.TEXT;
	}
	
	/**
	 * Sets lexer state. (In a tag or ouside of a tag)
	 * @param state state which lexer needs to be in
	 * @throws NullPointerException if state is null
	 */
	public void setState(LexerState state) {
		if(state == null) throw new NullPointerException();
		this.state = state;
	}
	
	// generira i vraća sljedeći token
	// baca LexerException ako dođe do pogreške
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
		
		if(state==LexerState.TEXT) {
			consumeNextText();
		} else if (state==LexerState.TAG){
			consumeNextTag();
		} else {
			throw new LexerException("Invalid lexer state detected.");
		}

		return token;
		
	}
	
	// vraća zadnji generirani token; može se pozivati
	// više puta; ne pokreće generiranje sljedećeg tokena
	/**
	 * Returns last generated token, does not generate new one.
	 * @return last generated token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Method generates tokens if next character is inside text area of document.
	 * @throws LexerException if escaping characters inside document is invalid
	 */
	private void consumeNextText() {
		StringBuilder sb = new StringBuilder();
		char nextChar = data[currentIndex];
		
		//if tag starts, create tag start token and return
		if(isTagAboutToStart()) {
			next(); //skip {
			next(); //skip $
			token = new Token(TokenType.TAG_START, null);
			return;
		}
		
		//read next text until tag starts or eof
		while(!isEof()) {
			nextChar = data[currentIndex];
	
			if(isTagAboutToStart()) break; //tag is about to start
			
			if(nextChar=='\\') {
				next();
				throwLexerException(isEof(), "Expected \\ or { after ecpace character \\. Got EOF instead.");
				nextChar = data[currentIndex];
				throwLexerException(nextChar!='\\' && nextChar!='{', "Invalid escaping. Expected \\ or { after ecpace character \\. Got " + nextChar + " instead.");
				sb.append("\\");
			}
			
			sb.append(nextChar);			
			next();
		}
		
		token = new Token(TokenType.TEXT, sb.toString()); 
	}
	
	/**
	 * Method generates tokens if next character is inside tag area of document.
	 * @throws LexerException if escaping characters inside document is invalid
	 */
	private void consumeNextTag() {
		
		//whitespace in tags is not relevant
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
		} else if(Character.isDigit(nextChar) || nextChar=='-' && (currentIndex+1<data.length) && Character.isDigit(data[currentIndex+1])) {
			number();
		} else {
			symbol();
		}
		
	}
	
	/**
	 * Generates new token which is inside tag area and starts with Character.isLetter and contains letters, numbers or _
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
		
		token = new Token(TokenType.WORD, sb.toString());
	}
	
	/**
	 * Generates new token which is inside tag area and starts with " and ends with "
	 * @throws LexerException if escaping characters inside document is invalid 
	 */
	private void string() {
		StringBuilder sb = new StringBuilder();
		next(); // skip quotes
		throwLexerException(isEof(), "String never closed.");
		
		for(char nextChar=data[currentIndex]; nextChar!='"'; nextChar=data[currentIndex]) {
			if(nextChar=='\\') { //escape-character is next
				next();
				throwLexerException(isEof(), "Expected \\ or \" after ecpace character \\. Got EOF instead.");
				nextChar = data[currentIndex];
				throwLexerException(nextChar!='\\' && nextChar!='"' && nextChar!='r' && nextChar!='n' && nextChar!='t', "Invalid escaping. Expected \\ or \" after ecpace character \\. Got " + nextChar + " instead.");
				if(nextChar=='r') {
					sb.append('\r');
				} else if(nextChar=='n') {
					sb.append('\n');
				} else if(nextChar=='t') {
					sb.append('\t');
				} else {
					sb.append(nextChar);
				}
				next();
				continue;
			} 
			
			sb.append(nextChar);
			next();
			throwLexerException(isEof(), "String never closed.");
		}
		
		next(); // skip end quotes
		token = new Token(TokenType.STRING, sb.toString());
	}
	
	/**
	 * Generates new token which contains digits only and one dot
	 * @throws LexerException number cannot be casted to double or integer value
	 */
	private void number() {
		StringBuilder sb = new StringBuilder();
		
		//append first character in case it is + or -
		sb.append(data[currentIndex]);
		next();
		
		boolean alreadyContainsDot = false;
		while(!isEof()) {
			char nextChar = data[currentIndex];
			if(!Character.isDigit(nextChar) && nextChar!='.') break;
			if(nextChar=='.' && alreadyContainsDot) break; // if we run into second dot - it is not part of a number (example: 2.35.4 -> 2.35 is double, . is a symbol and 4 is int)
			if(nextChar=='.') alreadyContainsDot = true;
			sb.append(nextChar);
			next();
		}
		
		String number = sb.toString();
		
		if(sb.toString().contains(".")) {
			try {
				token = new Token(TokenType.NUMBER_DOUBLE, Double.parseDouble(number));
			} catch (NumberFormatException ex) {
				throwLexerException(true, "Can not be converted to real number + " + number);
			}
		} else {
			try {
				token = new Token(TokenType.NUMBER_INT, Integer.parseInt(sb.toString()));
			} catch (NumberFormatException ex) {
				throwLexerException(true, "Can not be converted to real number + " + number);
			}
		}
		
		
	}
	
	/**
	 * Generates new token of type symbol
	 * @throws LexerException if symbol is not valid operator or valid function call symbol
	 * @throws LexerException if after function call symbol is whitespace
	 */
	private void symbol() {		
		char nextChar = data[currentIndex];
		
		//if tag ends
		if(nextChar=='$') {
			throwLexerException(!isTagAboutToEnd(), "Expected } after $ sign.");
			next(); //skip $
			next(); //skip }
			token = new Token(TokenType.TAG_END, null);
			return;
		}
		
		//check whether a symbol is a valid operator or function call or = (= is echo function start)
		throwLexerException(nextChar!='+' && nextChar!='-' && nextChar!='*' && nextChar!='/' && nextChar!='^' && nextChar!='@' && nextChar!='=', "Symbol is not a valid. Expected operator or function call @ as symbol. Got: " + nextChar);
		//if there is a space after @ symbol, it is not valid function call
		throwLexerException(nextChar=='@' && currentIndex+1<data.length && Character.isWhitespace(data[currentIndex+1]), "Invalid function call.");
		
		token = new Token(TokenType.SYMBOL, Character.valueOf((nextChar)));
		next();
	}
	
	private void skipWhiteSpace() {
		char nextChar = data[currentIndex];
		while(Character.isWhitespace(nextChar)) {
			next();
			nextChar = data[currentIndex];
			if(isEof()) break;
		}
	}
	
	private void next() {
		currentIndex++;
	}
	
	private boolean isTagAboutToStart() {
		return data[currentIndex]=='{' && currentIndex+1<data.length && data[currentIndex+1]=='$';
	}
	
	private boolean isTagAboutToEnd() {
		return data[currentIndex]=='$' && currentIndex+1<data.length && data[currentIndex+1]=='}';
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

