package hr.fer.zemris.java.custom.scripting.exec;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Models a value which is stored in an ObjectMultistack.<br/><br/>
 * 
 * Rules for arithmetic operations:<br/><br/> 
 * Rule 1: If either current value or argument is null, that 
 * value is treaded as being equal to Integer with value 0.<br/><br/>
 * 
 * Rule 2: If current value and argument are not null, they can be instances of Integer, Double or String.
 * For each value that is String, it is checked if String literal is decimal value (i.e. does it have
 * somewhere a symbol '.' or 'E'). If it is a decimal value, it is treated as such; otherwise, it is treated
 * as an Integer (if conversion fails, RuntimeException is thrown since the result of operation is undefined).<br/><br/>
 * 
 * Rule 3: Now, if either current value or argument is Double, operation will be performed on Doubles, and
 * the result should be will as an instance of Double. If not, both arguments must be Integers so the
 * operation should be performed on Integers and the result stored as an Integer.
 * 
 * @author Matija
 *
 */
public class ValueWrapper {

	private Object value;
	
	/**
	 * Creates and initialises a new value wrapper.
	 * @param value initial value stored in this wrapper
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}
	
	/**
	 * Returns the value stored in this wrapper
	 * @return the value stored in this wrapper
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Updates the value of this wrapper to the given value
	 * @param value new value of the wrapper
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * Adds a new value to the currently stored value. Rules
	 * of arithmetic operations are explained in javadoc of this class.
	 * @param incValue value to be added to the current value
	 */
	public void add(Object incValue) {
		doOperation(incValue, new Add());
	}
	
	/**
	 * Subtracts a new value from the currently stored value. Rules
	 * of arithmetic operations are explained in javadoc of this class.
	 * @param incValue value to be subtracted from the current value
	 */
	public void subtract(Object decValue) {
		doOperation(decValue, new Subtract());
	}
	
	/**
	 * Multiplies currently stored value by the given value. Rules
	 * of arithmetic operations are explained in javadoc of this class.
	 * @param incValue value to be multiplied by the current value
	 */
	public void multiply(Object mulValue) {
		doOperation(mulValue, new Multiply());
	}
	
	/**
	 * Divides currently stored value by the given value. Rules
	 * of arithmetic operations are explained in javadoc of this class.
	 * @param incValue value by which current value should be divided
	 */
	public void divide(Object divValue) {
		doOperation(divValue, new Divide());
	}
	
	/**
	 * Applies the given operation to the currently stored value with the given value
	 * @param value second operand
	 * @param operation operation to apply
	 */
	private void doOperation(Object value, BiFunction<Number, Number, Number> operation) {		
		Number first, secound;
		
		first = convertToNumber(this.value);
		secound = convertToNumber(value);
		
		this.value = operation.apply(first, secound);
	}
	
	/**
	 * This method does not perform any change in the stored data.
	 * It performs numerical comparison between the currently stored value 
	 * in the ValueWrapper object and given argument. The method returns an integer 
	 * less than zero if the currently stored value is smaller than the
	 * argument, an integer greater than zero if the currently stored value 
	 * is larger than the argument, or an integer 0 if they are equal. <br/><br/>
	 * 
	 * Rules of comparison are : <br/><br/>
	 * If both values are null, they are treated as equal.<br/>
	 * If one is null and the other is not, the null-value is treated as being equal to an integer with value 0.<br/>
	 * Otherwise, both values are promoted to same type as described for arithmetic methods 
	 * and then the comparison is performed.
	 * @param withValue value with which to compare currently stored value
	 * @return an integer less than zero if the currently stored value is smaller than the
	 * argument, an integer greater than zero if the currently stored value 
	 * is larger than the argument, or an integer 0 if they are equal
	 */
	public int numCompare(Object withValue) {
		if(this.value==null && withValue==null) 
			return 0;
		
		Double first, secound;
		first = convertToNumber(this.value).doubleValue();
		secound = convertToNumber(withValue).doubleValue();
		
		return first.compareTo(secound);
	}
	
	/**
	 * Converts an object of type Object to the object of type Integer or Double
	 * if it can. null reference is treated as an Integer of value 0.
	 * @param obj object to be converted
	 * @return object in a number format
	 * @throws RuntimeException if object cannot be converted to number
	 */
	public Number convertToNumber(Object obj) {
		if(obj == null) {
			return Integer.valueOf(0);
		} else if(obj instanceof Integer || obj instanceof Double) {
			return (Number)obj;
		} else if(obj instanceof String) {
			try {
				if(((String)obj).contains(".") || ((String)obj).contains("E"))
					return Double.parseDouble((String) obj);
				else
					return Integer.parseInt((String) obj);
			} catch (NumberFormatException ex) {
				throw new RuntimeException("This String cannot be converted to a number. Got: " + obj.toString());
			}
		} else {
			throw new RuntimeException("Value must be of type Integer, Double, String or null. Got: " + obj.getClass().getName());
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ValueWrapper))
			return false;
		ValueWrapper other = (ValueWrapper) obj;
		return Objects.equals(value, other.value);
	}
	
	/**
	 * Strategy that adds objects of type Number and returns the result.
	 * @author Matija
	 *
	 */
	private static class Add implements BiFunction<Number, Number, Number>{

		@Override
		public Number apply(Number t, Number u) {
			if(t instanceof Double || u instanceof Double) {
				return t.doubleValue() + u.doubleValue();
			} else {
				return t.intValue() + u.intValue();
			}
		}
		
	}
	
	/**
	 * Strategy that subtracts objects of type Number and returns the result.
	 * @author Matija
	 *
	 */
	private static class Subtract implements BiFunction<Number, Number, Number>{

		@Override
		public Number apply(Number t, Number u) {
			if(t instanceof Double || u instanceof Double) {
				return t.doubleValue() - u.doubleValue();
			} else {
				return t.intValue() - u.intValue();
			}
		}
		
	}
	
	/**
	 * Strategy that multiplies objects of type Number and returns the result.
	 * @author Matija
	 *
	 */
	private static class Multiply implements BiFunction<Number, Number, Number>{

		@Override
		public Number apply(Number t, Number u) {
			if(t instanceof Double || u instanceof Double) {
				return t.doubleValue() * u.doubleValue();
			} else {
				return t.intValue() * u.intValue();
			}
		}
		
	}
	
	/**
	 * Strategy that divides objects of type Number and returns the result.
	 * @author Matija
	 *
	 */
	private static class Divide implements BiFunction<Number, Number, Number>{

		@Override
		public Number apply(Number t, Number u) {
			if(t instanceof Double || u instanceof Double) {
				return t.doubleValue() / u.doubleValue();
			} else {
				return t.intValue() / u.intValue();
			}
		}
		
	}
	
}
