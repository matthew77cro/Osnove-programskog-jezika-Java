package hr.fer.zemris.java.hw05.db;

/**
 * 
 * This class models a getters for values of the attributes of database records.
 * 
 * @author Matija
 *
 */
public class FieldValueGetters {

	/**
	 * Getter for the value of the firstName attribute of a database record
	 */
	public static final IFieldValueGetter FIRST_NAME;
	
	/**
	 * Getter for the value of the lastName attribute of a database record
	 */
	public static final IFieldValueGetter LAST_NAME;
	
	/**
	 * Getter for the value of the jmbag attribute of a database record
	 */
	public static final IFieldValueGetter JMBAG;
	 
	static {
		FIRST_NAME = ((record) -> record.getFirstName());
		LAST_NAME = ((record) -> record.getLastName());
		JMBAG = ((record) -> record.getJmbag());
	}

}
