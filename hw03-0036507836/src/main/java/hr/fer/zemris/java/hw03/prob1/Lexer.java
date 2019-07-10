package hr.fer.zemris.java.hw03.prob1;

/**
 * 
 * The Lexer class allows an application to break a document into tokens.
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
	 * Constructor which accepts text to be tokenized
	 * @param text input which needs to be tokenized
	 */
	public Lexer(String text) {
		if(text==null) throw new NullPointerException();
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.BASIC;
	}
	
	// generira i vraća sljedeći token
	// baca LexerException ako dođe do pogreške
	/**
	 * Returns next token in input passed through constructor. If there are no
	 * more tokens, throws LexerException.
	 * @return next token in input passed through constructor
	 * @throws LexerException if there are no more tokens to be returned
	 */
	public Token nextToken() {
		
		if(currentIndex > data.length) throw new LexerException();
		
		skipSpace();
		if(isEof()) return eof();
		
		if(state==LexerState.BASIC) {
			consumeNextBasic();
		} else {
			consumeNextExtended();
		}

		return token;
		
	}
	
	// vraća zadnji generirani token; može se pozivati
	// više puta; ne pokreće generiranje sljedećeg tokena
	/**
	 * Returns last generated token. It can be called
	 * many times. It does not generate new tokens.
	 * @return
	 */
	public Token getToken() {
		return token;
	}
	
	// "Konzumira" iduci token u extended nacinu rada
	private void consumeNextExtended() {
		StringBuilder sb = new StringBuilder();
		char nextChar = data[currentIndex];
		
		//in extended mode, everything that is a word except # - this is a symbol
		if(nextChar=='#') {
			symbol();
			return;
		}
		
		while(!isEof()) {
			nextChar = data[currentIndex];
			if(Character.isWhitespace(nextChar) || nextChar=='#') break;
			sb.append(nextChar);
			next();
		}
		
		token = new Token(TokenType.WORD, sb.toString()); 
	}
	
	// "Konzumira" iduci token u basic nacinu rada
	private void consumeNextBasic() {
		
		char nextChar = data[currentIndex];
		
		if(Character.isLetter(nextChar) || nextChar=='\\') {
			string();
		} else if(Character.isDigit(nextChar)) {
			number();
		} else {
			symbol();
		}
		
	}
	
	
	// Procesira string u basic nacinu rada
	private void string() {
		StringBuilder sb = new StringBuilder();
		
		while(!isEof()) {
			
			char nextChar = data[currentIndex];
			
			if(Character.isLetter(nextChar)) {
				sb.append(nextChar);
			} else if(nextChar=='\\') { // Ako je escape character
				next();
				if(isEof()) throw new LexerException();
				nextChar = data[currentIndex];
				if(!Character.isDigit(nextChar) && nextChar!='\\') throw new LexerException();
				sb.append(nextChar);
			} else {
				break;
			}
			
			next();
			
		}
		
		token = new Token(TokenType.WORD, sb.toString());
	}
	
	// Procesira number u basic nacinu rada
	private void number() {
		StringBuilder sb = new StringBuilder();
		
		while(!isEof()) {
			char nextChar = data[currentIndex];
			if(!Character.isDigit(nextChar)) break;
			sb.append(nextChar);
			next();
		}
		
		Long l;
		try {
			l = Long.parseLong(sb.toString());
		} catch (NumberFormatException ex) {
			throw new LexerException();
		}
		
		token = new Token(TokenType.NUMBER, l);
	}
	
	// Procesira symbol u basic ili extended nacinu rada
	private void symbol() {
		char nextChar = data[currentIndex];
		token = new Token(TokenType.SYMBOL, Character.valueOf((nextChar)));
		next();
	}
	
	/**
	 * Changes state of this lexer.
	 * @param state new state of this lexer.
	 * @throws NullPointerException if state is null
	 */
	public void setState(LexerState state) {
		if(state == null) throw new NullPointerException();
		this.state = state;
	}
	
	// Preskace whitespace zankove
	private void skipSpace() {
		while(!isEof()) {
			char nextChar = data[currentIndex];
			if(Character.isWhitespace(nextChar)) {
				next();
			} else {
				break;
			}
		}
	}
	
	private void next() {
		currentIndex++;
	}
	
	private boolean isEof() {
		return currentIndex >= data.length;
	}
	
	private Token eof() {
		token = new Token(TokenType.EOF, null);
		currentIndex++;
		return token;
	}

}

