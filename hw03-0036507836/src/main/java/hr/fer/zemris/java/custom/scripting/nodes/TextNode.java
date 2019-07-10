package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

/**
 * Node which represents text part of a document.
 * @author Matija
 *
 */
public class TextNode extends Node{
	
	private final String text;

	/**
	 * This constructor initialises a TextNode whit given text.
	 * @param text text of a TextNode
	 */
	public TextNode(String text) {
		this.text = Objects.requireNonNull(text);
	}
	
	/**
	 * Returns text of this TextNode.
	 * @return text of this TextNode
	 */
	public String getText() {
		return text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(text);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof TextNode))
			return false;
		TextNode other = (TextNode) obj;
		return Objects.equals(text, other.text);
	}
	
	@Override
	public String toString() {
		return getText();
	}

}
