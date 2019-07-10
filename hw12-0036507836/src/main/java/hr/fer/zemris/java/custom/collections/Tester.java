package hr.fer.zemris.java.custom.collections;

@FunctionalInterface
/**
 * Represents a tester (boolean-valued function) of one argument.
 * This is a functional interface whose functional method is test(Object).
 * @author Matija
 *
 */
public interface Tester {
	
	/**
	 * Evaluates this test on the given argument.
	 * @param obj the input argument
	 * @return true if the input argument matches the test, otherwise false
	 */
	boolean test(Object obj);
	
}
