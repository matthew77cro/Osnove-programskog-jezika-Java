package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObjectMultistackTest {
	
	static ObjectMultistack multistack;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		multistack = new ObjectMultistack();
	}
	
	@BeforeEach
	void setUpBeforeTest() throws Exception {
	}
	
	@Test
	void test() {
		ValueWrapper year1 = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year1);
		
		ValueWrapper price1 = new ValueWrapper(200.51);
		ValueWrapper price2 = new ValueWrapper(200);
		ValueWrapper price3 = new ValueWrapper("200.51");
		multistack.push("price", price1);
		multistack.push("price", price2);
		multistack.push("price", price3);
		
		assertEquals(year1, multistack.peek("year"));
		assertEquals(price3, multistack.peek("price"));
		
		ValueWrapper year2 = new ValueWrapper(Integer.valueOf(1900));
		multistack.push("year", year2);
		
		assertEquals(year2, multistack.pop("year"));
		assertEquals(year1, multistack.peek("year"));
		
		multistack.peek("year").setValue(((Integer) multistack.peek("year").getValue()).intValue() + 50);
		assertEquals(2050, multistack.peek("year").getValue());
		
		multistack.peek("year").add("5");
		assertEquals(2055, multistack.peek("year").getValue());
		
		multistack.peek("year").add(5);
		assertEquals(2060, multistack.peek("year").getValue());
		
		multistack.peek("year").add(5.0);
		assertEquals(2065.0, multistack.peek("year").getValue());
		
		multistack.pop("year");
		assertThrows(EmptyStackException.class, () -> multistack.pop("year"));
	}

}
