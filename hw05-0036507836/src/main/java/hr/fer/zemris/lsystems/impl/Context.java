package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Context for executing the commands for Lindenmayer systems of fractals.
 * @author Matija
 *
 */
public class Context {

	private ObjectStack<TurtleState> stack;
	
	/**
	 * Creates a new context and initialises it.
	 */
	public Context() {
		stack = new ObjectStack<TurtleState>();
	}
	
	/**
	 * Get state for the top of the stack without removal.
	 * @return state for the top of the stack
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * Puts a given state on the top of the stack
	 * @param state
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Removes a state for the top of the stack.
	 */
	public void popState() {
		stack.pop();
	}


}
