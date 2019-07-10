package hr.fer.zemris.java.hw05.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;

class QueryParserTest {

	static QueryParser parser;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		String text = "jmbag=\"0000000003\"\r\n" + 
				"lastName = \"Bačić\"\r\n" + 
				"firstName>=\"A\" and lastName LIKE \"B*ć\"\r\n" + 
				"firstName>\"A\" and firstName<=\"C\" and lastName LIKE \"B*ć\" and jmbag<\"0000000002\"";
		parser = new QueryParser(text);
	}

	@Test
	void test() {
		List<ConditionalExpression> list = parser.getQuery();
		assertEquals(8, list.size());
		
		assertEquals(ComparisonOperators.EQUALS, list.get(0).getComparisonOperator());
		assertEquals(ComparisonOperators.EQUALS, list.get(1).getComparisonOperator());
		assertEquals(ComparisonOperators.GREATER_OR_EQUALS, list.get(2).getComparisonOperator());
		assertEquals(ComparisonOperators.LIKE, list.get(3).getComparisonOperator());
		assertEquals(ComparisonOperators.GREATER, list.get(4).getComparisonOperator());
		assertEquals(ComparisonOperators.LESS_OR_EQUALS, list.get(5).getComparisonOperator());
		assertEquals(ComparisonOperators.LIKE, list.get(6).getComparisonOperator());
		assertEquals(ComparisonOperators.LESS, list.get(7).getComparisonOperator());
		
		assertEquals(FieldValueGetters.JMBAG, list.get(0).getFieldGetter());
		assertEquals(FieldValueGetters.LAST_NAME, list.get(1).getFieldGetter());
		assertEquals(FieldValueGetters.FIRST_NAME, list.get(2).getFieldGetter());
		assertEquals(FieldValueGetters.LAST_NAME, list.get(3).getFieldGetter());
		assertEquals(FieldValueGetters.FIRST_NAME, list.get(4).getFieldGetter());
		assertEquals(FieldValueGetters.FIRST_NAME, list.get(5).getFieldGetter());
		assertEquals(FieldValueGetters.LAST_NAME, list.get(6).getFieldGetter());
		assertEquals(FieldValueGetters.JMBAG, list.get(7).getFieldGetter());
		
		assertEquals("0000000003", list.get(0).getStringLiteral());
		assertEquals("Bačić", list.get(1).getStringLiteral());
		assertEquals("A", list.get(2).getStringLiteral());
		assertEquals("B*ć", list.get(3).getStringLiteral());
		assertEquals("A", list.get(4).getStringLiteral());
		assertEquals("C", list.get(5).getStringLiteral());
		assertEquals("B*ć", list.get(6).getStringLiteral());
		assertEquals("0000000002", list.get(7).getStringLiteral());
	}

}
