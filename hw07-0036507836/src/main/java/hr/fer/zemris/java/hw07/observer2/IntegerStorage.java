package hr.fer.zemris.java.hw07.observer2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Integers storage is a storage for a single object of type Integer. It models an observer pattern
 * for a single object storage. When vale stored in this storage changes, all observers will be
 * notified.
 * @author Matija
 *
 */
public class IntegerStorage {

	private int value;
	private List<IntegerStorageObserver> observers; // use ArrayList here!!!
	private Iterator<IntegerStorageObserver> it;
	private boolean isIterating;
	
	/**
	 * Creates and initialises the integer storage object.
	 * @param initialValue initial value stored in this storage
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		this.observers = new ArrayList<>();
	}
	
	/**
	 * Adds given observer to the list of observers which
	 * need to be notified once the value stored changes.
	 * @param observer observer to be added
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if(!this.observers.contains(observer)) {
			this.observers.add(observer);
		}
	}
	
	/**
	 * Removes given observer from the list of observers. Implementation
	 * allows an observer to remove itself from the list.
	 * @param observer observer to be removed
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if(isIterating) {
			this.it.remove();
		} else {
			this.observers.remove(observer);
		}
	}
	
	/**
	 * Removes all observers.
	 */
	public void clearObservers() {
		this.observers.clear();
	}
	
	/**
	 * Returns the current value stored in this storage
	 * @return the current value stored in this storage
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Updates the value stored in this storage to the new given value.
	 * Also, notifies all observers that a changes has been made only if
	 * given value is different from the value currently stored.
	 * @param value new value to be stored
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if(this.value!=value) {
			// Create new Change
			IntegerStorageChange change = new IntegerStorageChange(this, this.value, value);
			// Update current value
			this.value = value;
			// Notify all registered observers
			if(observers!=null) {
				isIterating = true;
				for(it = observers.iterator(); it.hasNext();) {
					it.next().valueChanged(change);
				}
				it = null;
				isIterating = false;
			}
		}
	}

}
