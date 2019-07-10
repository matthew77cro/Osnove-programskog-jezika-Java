package hr.fer.zemris.java.hw03;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import static hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser.*;

public class SmartScriptTester {

	//as an argument pass "examples/doc1.txt" or "examples/doc2.txt" or "examples/doc3.txt"
	//because doc3 contains synax error, program will not explode with stack trace of an exception but
	//instead will print message what is the error (btw error is in line 8 - "-1.25.3")
	//program will print originalDocumentBody (result of createOriginalDocumentBody method)
	//and are 2 syntax trees equal (tree from parsing a document and tree from parsing originalDocumentBody)
	public static void main(String[] args) {
		
		if(args.length!=1) {
			System.out.println("Exactly one argument must be provided.");
			System.exit(-1);
		}
		
		String docBody = null;
		try {
			docBody = new String(
					 Files.readAllBytes(Paths.get(args[0])),
					 StandardCharsets.UTF_8
					);
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
			System.exit(-1);
		}
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			 System.out.println(e.getMessage());
			 System.exit(-1);
		} catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original
		 											// content of docBody
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		
		System.out.println();
		if(document.equals(document2)) System.out.println("Sytax trees are equal.");
		else System.out.println("Sytax trees are not equal.");
	}

}
