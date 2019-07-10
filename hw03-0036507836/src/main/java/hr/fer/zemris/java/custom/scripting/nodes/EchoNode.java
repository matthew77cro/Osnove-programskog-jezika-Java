package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Arrays;
import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Node which represents echo tag part of a document.
 * @author Matija
 *
 */
public class EchoNode extends Node{
	
	private final Element[] elements;

	/**
	 * This constructor initialises a EchoNode whit given elements.
	 * @param elements elements of the echo tag
	 * @throws NullPointerException if elements is null
	 */
	public EchoNode(Element[] elements) {
		this.elements = Objects.requireNonNull(elements);
	}
	
	/**
	 * Returns elements of this EchoNode.
	 * @return elements of this EchoNode
	 */
	public Element[] getElements() {
		return elements;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(elements);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof EchoNode))
			return false;
		EchoNode other = (EchoNode) obj;
		return Arrays.equals(elements, other.elements);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$= ");
		Element[] elements = getElements();
		
		for(int i=0; i<elements.length; i++) {
			sb.append(elements[i].toString());
			sb.append(" ");
		}
		sb.append("$}");
		return sb.toString();
	}

}
