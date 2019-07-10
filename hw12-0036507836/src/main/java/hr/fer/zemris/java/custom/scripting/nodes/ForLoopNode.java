package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Node which represents for loop tag part of a document.
 * @author Matija
 *
 */
public class ForLoopNode extends Node{
	
	private final ElementVariable variable;
	private final Element startExpression;
	private final Element endExpression;
	private final Element stepExpression; //can be null
	
	/**
	 * This constructor initialises a ForLoopNode whit given properties.
	 * @param variable variable in for loop
	 * @param startExpression start of for loop iteration
	 * @param endExpression end of for loop iteration
	 * @param stepExpression step in the for loop iteration
	 * @throws NullPointerException if variable, startExpression and/or endExpression is null
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = Objects.requireNonNull(variable);
		this.startExpression = Objects.requireNonNull(startExpression);
		this.endExpression = Objects.requireNonNull(endExpression);
		this.stepExpression = stepExpression;
	}

	/**
	 * Returns variable in the for loop.
	 * @return variable in the for loop.
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Returns start expression in the for loop.
	 * @return start expression in the for loop.
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Returns end expression in the for loop.
	 * @return end expression in the for loop.
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Returns step expression in the for loop.
	 * @return step expression in the for loop.
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(endExpression, startExpression, stepExpression, variable);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ForLoopNode))
			return false;
		ForLoopNode other = (ForLoopNode) obj;
		return Objects.equals(endExpression, other.endExpression)
				&& Objects.equals(startExpression, other.startExpression)
				&& Objects.equals(stepExpression, other.stepExpression) && Objects.equals(variable, other.variable);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$ FOR ");
		
		Element expressions[] = {getStartExpression(), getEndExpression(), getStepExpression()};
		String expressionsAsString[] = {expressions[0].toString(), expressions[1].toString(), expressions[2]==null ? null : expressions[2].toString()};
				
		sb.append(getVariable().asText() + " " + expressionsAsString[0] + " " + expressionsAsString[1] + " ");
		if(expressionsAsString[2]!=null) sb.append(expressionsAsString[2] + " ");
		
		sb.append("$}");
		return sb.toString();
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}

}
