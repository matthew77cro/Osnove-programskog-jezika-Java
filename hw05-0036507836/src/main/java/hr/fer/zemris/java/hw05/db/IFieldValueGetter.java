package hr.fer.zemris.java.hw05.db;

/**
 * This interface models a strategy for getting the value of an attribute from a database record.
 * @author Matija
 *
 */
public interface IFieldValueGetter {
	
	/**
	 * Returns the value of an attribute of a given record
	 * @param record record from which to extract an attribute
	 * @return the value of an attribute of a given record
	 * @throws NullPointerException if record is null
	 */
	public String get(StudentRecord record);

}
