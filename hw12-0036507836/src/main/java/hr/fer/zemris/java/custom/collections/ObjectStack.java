package hr.fer.zemris.java.custom.collections;

/**
 * Object stack models a stack-like collection.
 * @author Matija
 *
 */
public class ObjectStack {

	private ArrayIndexedCollection stack;
	
	public ObjectStack() {
		stack = new ArrayIndexedCollection();
	}
	
	/**
	 * Returns true if this stack contains no elements.
	 * @return true if this stack contains no elements
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	/**
	 * Returns the number of elements in this stack.
	 * @return the number of elements in this stack
	 */
	public int size() {
		return stack.size();
	}
	
	/**
	 * Adds element on top of the stack. Throws NullPointerException
	 * if value given to the method is null. Complexity is O(n) if
	 * stack must be resized and O(1) in case it is already long enough.
	 * @param value Element to be added on top of the stack.
	 * @throws NullPointerException if value is null
	 */
	public void push(Object value) {
		stack.add(value);
	}
	
	/**
	 * Removes and returns element on top of the stack. Complexity is  O(1) 
	 * in all cases.
	 * @return element that is on top of the stack
	 * @throws EmptyStackException if stack is empty (there are no more elements on the stack).
	 */
	public Object pop() {
		int stackSize = stack.size();
		if(stackSize==0) throw new EmptyStackException();
		
		Object obj = stack.get(stackSize-1);
		stack.remove(stackSize-1);
		return obj;
	}

	/**
	 * Returns but does not remove last element on the stack. Complexity is  O(1) 
	 * in all cases.
	 * @return element that is on top of the stack
	 * @throws EmptyStackException if stack is empty (there are no more elements on the stack).
	 */
	public Object peek() {
		if(stack.size()==0) throw new EmptyStackException();
		
		Object obj = stack.get(stack.size()-1);
		return obj;
	}
	
	/**
	 * Removes all of the elements from this stack. The stack will be empty after this method returns.
	 */
	void clear() {
		stack.clear();
	}
	
}
