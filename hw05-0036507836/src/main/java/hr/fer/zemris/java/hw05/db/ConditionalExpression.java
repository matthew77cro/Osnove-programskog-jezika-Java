package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * 
 * This class models a condition for querying database.
 * 
 * @author Matija
 *
 */
public class ConditionalExpression {

	private IFieldValueGetter fieldGetter;
	private String stringLiteral;
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor for creating new instance of ConditionalExpression class.
	 * @param fieldGetter getter for the database field
	 * @param stringLiteral string by which to compare values in the database
	 * @param comparisonOperator which comparison to do
	 * @throws NullPointerException if any of the arguments is null
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		this.fieldGetter = Objects.requireNonNull(fieldGetter);
		this.stringLiteral = Objects.requireNonNull(stringLiteral);
		this.comparisonOperator = Objects.requireNonNull(comparisonOperator);
	}

	/**
	 * Returns field getter
	 * @return field getter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Returns string literal
	 * @return string literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Returns comparison operator
	 * @return comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}
