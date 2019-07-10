package hr.fer.zemris.java.custom.collections;

import java.util.NoSuchElementException;

/**
 * An ElementsGetter over a collection is used to iterate through all elements one by one in any collection.
 * 
 * @author Matija
 *
 */
public interface ElementsGetter<E> {
	
	/**
	 * Returns the next element in the iteration.
	 * @return the next element in the iteration
	 * @throws NoSuchElementException if the ElementsGetter has no more elements
	 */
	E getNextElement() throws NoSuchElementException;
	
	/**
	 * Returns true if the ElementsGetter has more elements. (In other words, returns true if next() 
	 * would return an element rather than throwing an exception.)
	 * @return true if the iteration has more elements
	 */
	boolean hasNextElement();
	
	/**
	 * Performs the given process for each remaining element until all elements have been processed or the
	 * action throws an exception. Processes are performed in the order of iteration, if that order is specified. 
	 * Exceptions thrown by the process are relayed to the caller.
	 * @param p The process to be performed for each element
	 * @throws NullPointerException if the specified action is null
	 */
	default void processRemaining(Processor<? super E> p) throws NullPointerException{
		
		if(p==null) throw new NullPointerException();
		
		while(hasNextElement()) {
			p.process(getNextElement());
		}
		
	}

}
