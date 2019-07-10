package searching.algorithms;

public class Transition<S> {
	
	private S state;
	private double cost;
	
	public Transition(S state, double cost) {
		this.state = state;
		this.cost = cost;
	}

	public S getState() {
		return state;
	}

	public double getCost() {
		return cost;
	}

}
