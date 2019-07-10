package hr.fer.zemirs.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;

public class LexerTest {

	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new Lexer(null));
	}

	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		Lexer lexer = new Lexer("");
		
		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}
	
	@Test
	public void testRadAfterEOF() {
		Lexer lexer = new Lexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testWhitespaceContent() {
		// When input is only of spaces, tabs, newlines, etc...
		Lexer lexer = new Lexer("   \r\n\t    ");
		
		assertEquals(TokenType.TEXT, lexer.nextToken().getType(), "Input had only whitespace. Lexer should generated word token.");
		assertEquals("   \r\n\t    ", lexer.getToken().getValue(), "Input had only whitespace. Lexer should generated word token.");
	}

	@Test
	public void testTextOnly() {
		// Lets check for several words...
		Lexer lexer = new Lexer("  Štefanija\r\n\t Automobil   ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT, "  Štefanija\r\n\t Automobil   "),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testStringEscape() {
		String simpleDoc = "A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}.";
		Lexer lexer = new Lexer(simpleDoc);

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT, "A tag follows "),
			
			new Token(TokenType.TAG_START, null),
			new Token(TokenType.SYMBOL, '='),
			new Token(TokenType.STRING, "Joe \\\"Long\\\" Smith"),
			new Token(TokenType.TAG_END, null),
			
			new Token(TokenType.TEXT, "."),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testInvalidEscapeText() {
		String simpleDoc = "This is a document\n   consisting only of text and invalid escape \\.";
		Lexer lexer = new Lexer(simpleDoc);
		
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testInvalidEscapeTextEnd() {
		String simpleDoc = "This is a document\n   consisting only of text and invalid escape \\";
		Lexer lexer = new Lexer(simpleDoc);

		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testInvalidEscapeTagString() {
		Lexer lexer = new Lexer("{$= i \"\\?\" $}");

		//skip until escape
		lexer.nextToken();
		lexer.setState(LexerState.TAG);
		lexer.nextToken();
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testWhiteSpaceAndTag() {
		// Lets check for several numbers...
		Lexer lexer = new Lexer("  {$ FOR i 1 10 1 $}   ");

		Token correctData[] = {
			new Token(TokenType.TEXT, "  "),
			new Token(TokenType.TAG_START, null),
			new Token(TokenType.WORD, "FOR"),
			new Token(TokenType.WORD, "i"),
			new Token(TokenType.NUMBER_INT, Integer.valueOf(1)),
			new Token(TokenType.NUMBER_INT, Integer.valueOf(10)),
			new Token(TokenType.NUMBER_INT, Integer.valueOf(1)),
			new Token(TokenType.TAG_END, null),
			new Token(TokenType.TEXT, "   "),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testCombinedInput() {
		// Lets check for several symbols...
		String s = "This is { sample text.\n" + 
				"{$ FOR i 1 10 1 $}\n" + 
				" This is {$= i $}-th time this message is generated.\n" + 
				"{$END$}\n" + 
				"{$FOR i 0 10 -2 $}\n" + 
				" sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" + 
				"{$END$}" +
				"{$ FOR i-1.35bbb\"\\\\1\" $}" +
				"{$END$}";
		Lexer lexer = new Lexer(s);

		Token correctData[] = {
			new Token(TokenType.TEXT, "This is { sample text.\n"),
			
			new Token(TokenType.TAG_START, null),
			new Token(TokenType.WORD, "FOR"),
			new Token(TokenType.WORD, "i"),
			new Token(TokenType.NUMBER_INT, Integer.valueOf(1)),
			new Token(TokenType.NUMBER_INT, Integer.valueOf(10)),
			new Token(TokenType.NUMBER_INT, Integer.valueOf(1)),
			new Token(TokenType.TAG_END, null),
			
			new Token(TokenType.TEXT, "\n This is "),
			
			new Token(TokenType.TAG_START, null),
			new Token(TokenType.SYMBOL, '='),
			new Token(TokenType.WORD, "i"),
			new Token(TokenType.TAG_END, null),
			
			new Token(TokenType.TEXT, "-th time this message is generated.\n"),
			
			new Token(TokenType.TAG_START, null),
			new Token(TokenType.WORD, "END"),
			new Token(TokenType.TAG_END, null),
			
			new Token(TokenType.TEXT, "\n"),
			
			new Token(TokenType.TAG_START, null),
			new Token(TokenType.WORD, "FOR"),
			new Token(TokenType.WORD, "i"),
			new Token(TokenType.NUMBER_INT, Integer.valueOf(0)),
			new Token(TokenType.NUMBER_INT, Integer.valueOf(10)),
			new Token(TokenType.NUMBER_INT, Integer.valueOf(-2)),
			new Token(TokenType.TAG_END, null),
			
			new Token(TokenType.TEXT, "\n sin("),
			
			new Token(TokenType.TAG_START, null),
			new Token(TokenType.SYMBOL, '='),
			new Token(TokenType.WORD, "i"),
			new Token(TokenType.TAG_END, null), 
			
			new Token(TokenType.TEXT, "^2) = "),
			
			new Token(TokenType.TAG_START, null),
			new Token(TokenType.SYMBOL, '='),
			new Token(TokenType.WORD, "i"),
			new Token(TokenType.WORD, "i"),
			new Token(TokenType.SYMBOL, '*'),
			new Token(TokenType.SYMBOL, '@'),
			new Token(TokenType.WORD, "sin"),
			new Token(TokenType.STRING, "0.000"),
			new Token(TokenType.SYMBOL, '@'),
			new Token(TokenType.WORD, "decfmt"),
			new Token(TokenType.TAG_END, null),
			
			new Token(TokenType.TEXT, "\n"),
			
			new Token(TokenType.TAG_START, null),
			new Token(TokenType.WORD, "END"),
			new Token(TokenType.TAG_END, null),
			
			new Token(TokenType.TAG_START, null),
			new Token(TokenType.WORD, "FOR"),
			new Token(TokenType.WORD, "i"),
			new Token(TokenType.NUMBER_DOUBLE, Double.valueOf(-1.35)),
			new Token(TokenType.WORD, "bbb"),
			new Token(TokenType.STRING, "\\\\1"),
			new Token(TokenType.TAG_END, null),
			
			new Token(TokenType.TAG_START, null),
			new Token(TokenType.WORD, "END"),
			new Token(TokenType.TAG_END, null),
			
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(Lexer lexer, Token[] correctData) {
		int counter = 0;
		for(Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
			if(actual.getType()==TokenType.TAG_START) lexer.setState(LexerState.TAG);
			if(actual.getType()==TokenType.TAG_END) lexer.setState(LexerState.TEXT);
		}
	}

}
