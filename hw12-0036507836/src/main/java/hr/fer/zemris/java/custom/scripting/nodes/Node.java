package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

/**
 * Base class for all graph nodes. Used in SimpleScriptParser as syntax graph nodes.
 */
import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

public abstract class Node {
	
	private ArrayIndexedCollection children;
	private int numOfChildren = 0;
	
	/**
	 * Adds node to list of children of current node.
	 * @param child node to be added as a child node
	 * @throws NullPointerException if child is null
	 */
	public void addChildNode(Node child) {
		if(child==null) throw new NullPointerException();
		
		if(children == null) children = new ArrayIndexedCollection();
		
		children.add(child);
		numOfChildren++;
	}
	
	/**
	 * Returns number of direct children of a node.
	 * @return number of direct children of a node
	 */
	public int numberOfChildren() {
		return numOfChildren;
	}
	
	/**
	 * Return a child node at a given index. If index is not legal, throws IndexOutOfBoundsException.
	 * More formally, if (index<0 || index>=numOfChildren) throws IndexOutOfBoundsException.
	 * @param index index of a child to be returned
	 * @return a child node at a given index
	 * @throws IndexOutOfBoundsException if (index<0 || index>=numOfChildren)
	 */
	public Node getChild(int index) {
		if(index<0 || index>=numOfChildren) throw new IndexOutOfBoundsException();
		
		return (Node) children.get(index);
	}
	
	public abstract void accept(INodeVisitor visitor);

	@Override
	public int hashCode() {
		return Objects.hash(children, numOfChildren);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Node))
			return false;
		Node other = (Node) obj;
		return Objects.equals(children, other.children) && numOfChildren == other.numOfChildren;
	}
	
	@Override
	public String toString() {
		return "";
	}

}
