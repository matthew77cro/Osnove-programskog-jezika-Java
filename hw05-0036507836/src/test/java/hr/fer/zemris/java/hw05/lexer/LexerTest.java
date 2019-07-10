package hr.fer.zemris.java.hw05.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LexerTest {

	static Lexer lexer;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		String text = "jmbag=\"0000000003\"\r\n" + 
				"lastName = \"Bačić\"\r\n" + 
				"firstName>=\"A\" and lastName LIKE \"B*ć\"\r\n" + 
				"firstName>\"A\" and firstName<=\"C\" and lastName LIKE \"B*ć\" and jmbag<\"0000000002\"";
		lexer = new Lexer(text);
	}

	@Test
	void test() {
		Token t;
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.ATTRIBUTE,"jmbag"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.OPERATOR,'='), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.STRING,"0000000003"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.ATTRIBUTE,"lastName"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.OPERATOR,'='), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.STRING,"Bačić"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.ATTRIBUTE,"firstName"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.OPERATOR,">="), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.STRING,"A"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.OPERATOR,"and"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.ATTRIBUTE,"lastName"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.OPERATOR,"LIKE"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.STRING,"B*ć"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.ATTRIBUTE,"firstName"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.OPERATOR,'>'), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.STRING,"A"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.OPERATOR,"and"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.ATTRIBUTE,"firstName"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.OPERATOR,"<="), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.STRING,"C"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.OPERATOR,"and"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.ATTRIBUTE,"lastName"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.OPERATOR,"LIKE"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.STRING,"B*ć"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.OPERATOR,"and"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.ATTRIBUTE,"jmbag"), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.OPERATOR,'<'), t);
		
		t = lexer.nextToken();
		assertEquals(new Token(TokenType.STRING,"0000000002"), t);
	}

}
