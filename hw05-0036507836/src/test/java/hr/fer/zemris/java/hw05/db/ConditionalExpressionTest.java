package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ConditionalExpressionTest {
	
	static ConditionalExpression expr;
	static StudentRecord record;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME,
				 "Bac*",
				 ComparisonOperators.LIKE
		);
		
		record = new StudentRecord("0036507836", "Bacic", "Matija", 5);
	}

	@Test
	void test() {
		assertTrue(expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(record), // returns lastName from given record
				 expr.getStringLiteral() // returns "Bos*"
				));
	}

}
