package searching.algorithms;

public class Node<S> {
	
	private Node<S> parent;
	private S state;
	private double cost;

	public Node(Node<S> parent, S state, double cost) {
		this.parent = parent;
		this.state = state;
		this.cost = cost;
	}
	
	public Node<S> getParent() {
		return parent;
	}
	
	public S getState() {
		return state;
	}
	
	public double getCost() {
		return cost;
	}

}
