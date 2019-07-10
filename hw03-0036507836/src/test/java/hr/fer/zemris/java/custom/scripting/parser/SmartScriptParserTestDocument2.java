package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

class SmartScriptParserTestDocument2 {
	
	static SmartScriptParser parser;
	static DocumentNode docNode;
	
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while(true) {
				int read = is.read(buffer);
				if(read<1) break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch(IOException ex) {
			return null;
		}
	}


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		String docBody = new SmartScriptParserTestDocument2().loader("document2.txt");
		
		parser = new SmartScriptParser(docBody);
		docNode = parser.getDocumentNode();
		
	}

	@Test
	void testNumberOfChildren() {
		assertEquals(8, docNode.numberOfChildren());
		assertEquals(0, docNode.getChild(0).numberOfChildren());
		assertEquals(3, docNode.getChild(1).numberOfChildren());
		assertEquals(5, docNode.getChild(3).numberOfChildren());
		assertEquals(1, docNode.getChild(5).numberOfChildren());
	}
	
	@Test
	void testForTagElements() {
		ForLoopNode fln = (ForLoopNode) docNode.getChild(1);
		assertEquals("i", fln.getVariable().asText());
		assertEquals("1", fln.getStartExpression().asText());
		assertEquals("10", fln.getEndExpression().asText());
		assertEquals("1", fln.getStepExpression().asText());
		
		fln = (ForLoopNode) docNode.getChild(7);
		assertEquals("i", fln.getVariable().asText());
		assertEquals("\\\\ \\\" 1", fln.getStartExpression().asText());
		assertEquals("10", fln.getEndExpression().asText());
		assertNull(fln.getStepExpression());
	}
	
	@Test
	void testEchoTagElements() {
		EchoNode en = (EchoNode) docNode.getChild(3).getChild(3);
		assertEquals(7, en.getElements().length);
		assertEquals("i", en.getElements()[0].asText());
		assertEquals("i", en.getElements()[1].asText());
		assertEquals("*", en.getElements()[2].asText());
		assertEquals("sin", en.getElements()[3].asText());
		assertEquals("0.000", en.getElements()[4].asText());
		assertEquals("-1.25", en.getElements()[5].asText());
		assertEquals("decfmt", en.getElements()[6].asText());
	}
	
	@Test
	void testTextElements() {
		TextNode tn = (TextNode) docNode.getChild(1).getChild(2);
		assertEquals("-th time this message is generated.\r\n", tn.getText());
	}

}
