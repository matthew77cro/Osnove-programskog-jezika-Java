package hr.fer.zemris.java.hw05.db;

import java.util.List;
import java.util.Objects;

/**
 * 
 * Models a filter for querying a database records. Accepts a given record iff all conditions in
 * a given condition list accept the record.
 * 
 * @author Matija
 *
 */
public class QueryFilter implements IFilter{

	private List<ConditionalExpression> list;
	
	/**
	 * Constructor for creating filter out of conditions
	 * @param list list of conditions for a filter
	 * @throws NullPointerException if the list is null
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = Objects.requireNonNull(list);
	}

	@Override
	public boolean accepts(StudentRecord record) {
		if(record==null) throw new NullPointerException();

		for(ConditionalExpression cond : list) {
			IComparisonOperator comparison = cond.getComparisonOperator();
			IFieldValueGetter getter = cond.getFieldGetter();
			String literal = cond.getStringLiteral();
			if(!comparison.satisfied(getter.get(record), literal))
				return false;
		}
		
		return true;
	}

}
