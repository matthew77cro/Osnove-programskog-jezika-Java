package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * 
 * Element which contains single read-only property of type String which represents the value of a symbol.
 * 
 * @author Matija
 *
 */
public class ElementOperator extends Element{

	private final String symbol;

	/**
	 * Constructor that initialises ElementOperator with specific String value which represents the value of a symbol.
	 * @param value value of a symbol
	 */
	public ElementOperator(String symbol) {
		this.symbol = Objects.requireNonNull(symbol);
	}
	
	@Override
	public String asText() {
		return symbol;
	}
	
	/**
	 * Returns value of a symbol
	 * @return value of a symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementOperator))
			return false;
		ElementOperator other = (ElementOperator) obj;
		return Objects.equals(symbol, other.symbol);
	}
	
}
