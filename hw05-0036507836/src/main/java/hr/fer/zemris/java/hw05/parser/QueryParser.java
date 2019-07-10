package hr.fer.zemris.java.hw05.parser;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.lexer.Lexer;
import hr.fer.zemris.java.hw05.lexer.Token;
import hr.fer.zemris.java.hw05.lexer.TokenType;

/**
 * Parser used for StudentDatabse as a part of hw05 for the OPJJ course at FER UNIZG.
 * @author Matija
 *
 */
public class QueryParser {

	private Lexer lexer;
	private Token t;
	private List<ConditionalExpression> conditions;
	private boolean isDirectQuery;
	
	/**
	 * Creates, initialises new instance of the query parser and builds a
	 * syntax tree for the given input.
	 * @param query
	 * @throws ParserException if any exception occurs
	 */
	public QueryParser(String query) {
		this.lexer = new Lexer(query);
		this.conditions = new ArrayList<ConditionalExpression>();
		
		try {
			parse();
		} catch (Exception ex) {
			throw new ParserException(ex.getMessage());
		}
	}
	
	/**
	 * Parses the given input and makes the syntax tree.
	 */
	private void parse() {
		
		next();
		
		while(t.getType()!=TokenType.EOF) {
			
			if(t.getType().equals(TokenType.OPERATOR) || t.getType().equals(TokenType.STRING)) throw new ParserException("Operator can not be first query argument!");
			
			if(t.getValue().equals("jmbag")) {
				jmbagQuery();
			} else if(t.getValue().equals("firstName")) {
				firstNameQuery();
			} else if(t.getValue().equals("lastName")) {
				lastNameQuery();
			} else {
				throw new ParserException("Invalid attribute name: " + t.getValue());
			}
			
			next();
			
			if(t.getType().equals(TokenType.OPERATOR) && "and".equalsIgnoreCase(t.getValue().toString())) {
				next();
				if(t.getType()==TokenType.EOF) throw new ParserException("Expected new query condition after operator AND. Got EOF instead.");
			}
			
		}
		
		isDirectQuery = conditions.size()==1 && 
				conditions.get(0).getFieldGetter()==FieldValueGetters.JMBAG && 
				conditions.get(0).getComparisonOperator()==ComparisonOperators.EQUALS;
		
	}
	
	/**
	 * Helper method for parse() method
	 */
	private void jmbagQuery() {
		next();
		Token operator = t;
		next();
		Token stringLiteral = t;
		
		if(operator.getType()!=TokenType.OPERATOR || 
				"and".equalsIgnoreCase(operator.getValue().toString()) ||
				stringLiteral.getType()!=TokenType.STRING) throw new ParserException("Expected operator after attribute name! Got: " + operator.getValue());
	
		conditions.add(new ConditionalExpression(FieldValueGetters.JMBAG, t.getValue().toString(), ComparisonOperators.forOperation(operator.getValue().toString())));
	}
	
	/**
	 * Helper method for parse() method
	 */
	private void firstNameQuery() {
		next();
		Token operator = t;
		next();
		Token stringLiteral = t;
		
		if(operator.getType()!=TokenType.OPERATOR || 
				"and".equalsIgnoreCase(operator.getValue().toString()) ||
				stringLiteral.getType()!=TokenType.STRING) throw new ParserException("Expected operator after attribute name! Got: " + operator.getValue());
	
		conditions.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, t.getValue().toString(), ComparisonOperators.forOperation(operator.getValue().toString())));
	}

	/**
	 * Helper method for parse() method
	 */
	private void lastNameQuery() {
		next();
		Token operator = t;
		next();
		Token stringLiteral = t;
		
		if(operator.getType()!=TokenType.OPERATOR || 
				"and".equalsIgnoreCase(operator.getValue().toString()) ||
				stringLiteral.getType()!=TokenType.STRING) throw new ParserException("Expected operator after attribute name! Got: " + operator.getValue());
	
		conditions.add(new ConditionalExpression(FieldValueGetters.LAST_NAME, t.getValue().toString(), ComparisonOperators.forOperation(operator.getValue().toString())));
	}
	
	/**
	 * Helper method for parse() method
	 */
	private void next() {
		t = lexer.nextToken();
	}
	
	/**
	 * Returns if query is direct or not. Direct queries are queries which
	 * only filter data by jmbag attribute.
	 * @return if query is direct or not
	 */
	public boolean isDirectQuery() {
		return isDirectQuery;
	}
	
	/**
	 * Returns queried jmbag for direct query
	 * @return queried jmbag for direct query
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) throw new IllegalStateException("Query is not direct!");
		return conditions.get(0).getStringLiteral();
	}
	
	/**
	 * Returns conditional expression for this query
	 * @return conditional expression for this query
	 */
	public List<ConditionalExpression> getQuery(){
		return this.conditions;
	}

}
