package hr.fer.zemris.java.custom.scripting.parser;

import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * 
 * Represents a parser for the scripting language
 * 
 * @author Matija
 *
 */
public class SmartScriptParser {
	
	private Lexer lexer;
	private ObjectStack stack;
	private DocumentNode documentNode;
	private Token token;
	
	/**
	 * This constructor accepts document which needs to be tokenised and then parsed into a syntax tree.
	 * @param document document body which needs to be parsed
	 */
	public SmartScriptParser(String document) {
		if(document == null) throw new SmartScriptParserException("document cannot be null!");
		lexer = new Lexer(document);
		stack = new ObjectStack();
		documentNode = new DocumentNode();
		try {
			parse();
		} catch (LexerException | SmartScriptParserException | EmptyStackException | NumberFormatException | NullPointerException ex) {
			throw new SmartScriptParserException(ex.getMessage());
		}
	}
	
	/**
	 * Returns document node which is root of the syntax tree
	 * @return document node which is root of the syntax tree
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
	
	/**
	 * Returns a string than matches original document in that, after parsing it once more, it will generate same syntax tree.
	 * @param document DocumentNode that is root of syntax tree
	 * @return string that will generate same syntax tree as original document
	 */
	public static String createOriginalDocumentBody(Node node) {
		if(node.numberOfChildren()==0) return "";
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0, numOfChildren = node.numberOfChildren(); i<numOfChildren; i++) {
			Node n = node.getChild(i);
			sb.append(n.toString()); //add current node
			sb.append(createOriginalDocumentBody(n)); //add all children of current node
		}
		
		//if it is non-empty node, close it
		if(node instanceof EchoNode || node instanceof ForLoopNode) {
			sb.append("{$END$}");
		}
		
		return sb.toString();
	}
	
	/**
	 * Parses whole document and generates syntax tree.
	 * @throws LexerException if lexer throws an exception
	 * @throws SmartScriptParserException if there is an error in the document
	 */
	private void parse() throws LexerException, SmartScriptParserException{
		
		//pushes document node on top of the stack
		stack.push(documentNode);
		token = lexer.nextToken();
		
		while(token.getType()!=TokenType.EOF) {
			
			if(token.getType()==TokenType.TEXT) {
				addChildOnTopElement(stack, new TextNode(token.getValue().toString()));
			} else if(token.getType()==TokenType.TAG_START) {
				lexer.setState(LexerState.TAG);
				processTag(stack);			
				lexer.setState(LexerState.TEXT);
			} else {
				throwException(true, "Unexpected token " + token.getValue());
			}
			
			token = lexer.nextToken();
		}
		
		//pops document node from stack
		stack.pop();
		throwException(stack.size()!=0, "Non-empty tag has no end tag.");
		
	}
	
	/**
	 * Adds node on top of the stack.
	 * @param stack stack on which to push the node
	 * @param child node which to push on the stack
	 * @throws NullPointerException if stack or child is null
	 */
	private void addChildOnTopElement(ObjectStack stack, Node child) {
		if(stack==null) throw new NullPointerException();
		if(child==null) throw new NullPointerException();
		((Node)stack.peek()).addChildNode(child);
	}
	
	private void processTag(ObjectStack stack) {
		
		token = lexer.nextToken();
		String tokenName = token.getValue().toString();
		
		throwException(token.getType()!=TokenType.WORD && !token.getValue().equals('='), "Unexpected tag name " + token.getValue());
		
		if(tokenName.equalsIgnoreCase("FOR")) { //FOR TAG
			processForTag();
		} else if (tokenName.equals("=")){ //ECHO TAG
			processEchoTag();
		} else if(tokenName.equalsIgnoreCase("END")) { //END TAG
			stack.pop();
			token = lexer.nextToken();
		} else {
			throw new SmartScriptParserException("Unexpected tag name " + token.getValue());
		}
		
	}
	
	private void processForTag() {
		
		ElementVariable variable = null;
		Element expressions[] = new Element[3];
		
		token = lexer.nextToken();
		throwException(token.getType()!=TokenType.WORD, "Invalid variable name " + token.getValue());
		variable = new ElementVariable(token.getValue().toString());
		
		for(int i=0; i<3; i++) {
			token = lexer.nextToken();
			
			if(i==2 && token.getType()==TokenType.TAG_END) break; //in case there is only 2 instead of 3 arguments in for loop
			
			String tokenStringValue = token.getValue().toString();
			
			switch(token.getType()) {
				case NUMBER_INT:
					expressions[i] = new ElementConstantInteger(Integer.parseInt(tokenStringValue));
					break;
				case NUMBER_DOUBLE:
					expressions[i] = new ElementConstantDouble(Double.parseDouble(tokenStringValue));
					break;
				case STRING:
					expressions[i] = new ElementString(tokenStringValue);
					break;	
				case WORD:
					expressions[i] = new ElementVariable(tokenStringValue);
					break;
				default:
					throwException(true, "Expected integer, double, string or variable name. Got: " + token.getValue());
					break;
				
			}
			
			if(i==2) token = lexer.nextToken();
		}
		
		throwException(token.getType()!=TokenType.TAG_END, "Expected 2 or 3 parameters in a for loop."); //in case there is more than 3 arguments in a for loop
		
		ForLoopNode forNode = new ForLoopNode(variable, expressions[0], expressions[1], expressions[2]);
		addChildOnTopElement(stack, forNode);
		stack.push(forNode);
		
	}
	
	private void processEchoTag() {
		Element[] elements = new Element[0];
		token = lexer.nextToken(); // skip tag_start token
		
		while(token.getType()!=TokenType.TAG_END) {
			elements = Arrays.copyOf(elements, elements.length+1);
			int lastIndex = elements.length-1;
			
			switch(token.getType()) {
				case NUMBER_DOUBLE:
					elements[lastIndex] = new ElementConstantDouble(Double.parseDouble(token.getValue().toString()));
					break;
				case NUMBER_INT:
					elements[lastIndex] = new ElementConstantInteger(Integer.parseInt(token.getValue().toString()));
					break;
				case STRING:
					elements[lastIndex] = new ElementString(token.getValue().toString());
					break;
				case WORD:
					elements[lastIndex] = new ElementVariable(token.getValue().toString());
					break;
				case SYMBOL:
					if(token.getValue().equals('@')) {
						token = lexer.nextToken();
						throwException(token.getType()!=TokenType.WORD, "Unexpected function name " + token.getValue());
						elements[lastIndex] = new ElementFunction(token.getValue().toString());
					} else {
						elements[lastIndex] = new ElementOperator(token.getValue().toString());
					}
					break;
				default:
					throwException(true, "Unexpected token type " + token.getType() + ":" + token.getValue());
					break;
			}
			
			token = lexer.nextToken();
		}
		
		addChildOnTopElement(stack, new EchoNode(elements));
	}
	
	private void throwException(boolean whenToThrow, String msg) {
		if(whenToThrow) throw new SmartScriptParserException(msg);
	}

}
