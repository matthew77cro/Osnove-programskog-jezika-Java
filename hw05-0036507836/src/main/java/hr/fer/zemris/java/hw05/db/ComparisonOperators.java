package hr.fer.zemris.java.hw05.db;

/**
 * 
 * This class models operators for comparing two strings. Used in a database.
 * 
 * @author Matija
 *
 */
public class ComparisonOperators {

	 /**
	  * Satisfied method of this instance returns true iff first argument in lexicographically less than other.
	  */
	 public static final IComparisonOperator LESS;
	 
	 /**
	  * Satisfied method of this instance returns true iff first argument in lexicographically less than or equal to other.
	  */
	 public static final IComparisonOperator LESS_OR_EQUALS;
	 
	 /**
	  * Satisfied method of this instance returns true iff first argument in lexicographically greater than other.
	  */
	 public static final IComparisonOperator GREATER;
	 
	 /**
	  * Satisfied method of this instance returns true iff first argument in lexicographically greater than or equal to other.
	  */
	 public static final IComparisonOperator GREATER_OR_EQUALS;
	 
	 /**
	  * Satisfied method of this instance returns true iff first argument in lexicographically equal to other.
	  */
	 public static final IComparisonOperator EQUALS;
	 
	 /**
	  * Satisfied method of this instance returns true iff first argument in lexicographically not equal to other.
	  */
	 public static final IComparisonOperator NOT_EQUALS;
	 
	 /**
	  * Satisfied method of this instance returns true iff first argument matches pattern provided in the second argument.
	  */
	 public static final IComparisonOperator LIKE;
	 
	 static {
		 LESS = ((s1, s2) -> s1.compareTo(s2)<0);
		 LESS_OR_EQUALS = ((s1, s2) -> s1.compareTo(s2)<=0);
		 GREATER = ((s1, s2) -> s1.compareTo(s2)>0);
		 GREATER_OR_EQUALS = ((s1, s2) -> s1.compareTo(s2)>=0);
		 EQUALS = ((s1, s2) -> s1.compareTo(s2)==0);
		 NOT_EQUALS = ((s1, s2) -> s1.compareTo(s2)!=0);
		 LIKE = ((s1, s2) -> {
			 
			 char[] strchr = s2.toCharArray();
			 
			 int numOfWildCards = 0;
			 for(char c : strchr) {
				 if(!Character.isLetter(c) && !Character.isDigit(c) && c!='*')
					 throw new IllegalArgumentException("Regex must contain only numbers, letters and wildcards!");
				 if(c=='*') numOfWildCards++;
			 }
			 
			 if(numOfWildCards>1) throw new IllegalArgumentException("Too many wildcards!");
			 
			 return s1.matches(s2.replace("*", ".*"));
		 });
	 }
	 
	 /**
	  * Returns IComparisonOperator for given operation
	  * @param operation operation for which to return the operator
	  * @return IComparisonOperator for given operation
	  * @throws IllegalArgumentException if there is no operator for given operation
	  */
	 public static IComparisonOperator forOperation(String operation) {
		 switch(operation) {
		 	case "<":
		 		return LESS;
		 	case "<=":
		 		return LESS_OR_EQUALS;
		 	case ">":
		 		return GREATER;
		 	case ">=":
		 		return GREATER_OR_EQUALS;
		 	case "=":
		 		return EQUALS;
		 	case "!=":
		 		return NOT_EQUALS;
		 }
		 
		 if(operation.equalsIgnoreCase("like")) return LIKE;
		 
		 throw new IllegalArgumentException();
	 }

}
