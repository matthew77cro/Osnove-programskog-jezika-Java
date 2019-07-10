package hr.fer.zemris.java.hw05.db;

/**
 * This interface models a strategy for getting the value of an attribute from a database record.
 * @author Matija
 *
 */
public interface IFilter {
	
	/**
	 * Returns true iff filter accepts given record
	 * @param record record on which to apply a given filter
	 * @return true iff filter accepts given record
	 * @throws NullPointerException if record is null
	 */
	public boolean accepts(StudentRecord record) throws NullPointerException;
	
}
