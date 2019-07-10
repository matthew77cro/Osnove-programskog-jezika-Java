package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class QueryFilterTest {
	
	static QueryFilter filter;
	static StudentDatabase db;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		List<ConditionalExpression> list = new ArrayList<ConditionalExpression>();
		
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "0036507836", ComparisonOperators.EQUALS));
		list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Matiaa", ComparisonOperators.GREATER));
		
		filter = new QueryFilter(list);
		db = new StudentDatabase(new String[] {"0036507836	Bacic	Matija	5", "0036507835	Bacic	Matiaa	5"});
	}

	@Test
	void test() {
		List<StudentRecord> filtered = db.filter(filter);
		
		assertEquals(1, filtered.size());
		assertEquals(new StudentRecord("0036507836", "Bacic", "Matija", 5), filtered.get(0));
	}

}
