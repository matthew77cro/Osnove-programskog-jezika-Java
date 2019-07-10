package hr.fer.zemris.java.hw05.db;

/**
 * This interface models a strategy for comparing two strings.
 * @author Matija
 *
 */
public interface IComparisonOperator {
	
	/**
	 * Returns true if condition is satisfied
	 * @param value1 first string
	 * @param value2 second string
	 * @return true if condition is satisfied
	 */
	public boolean satisfied(String value1, String value2);

}
