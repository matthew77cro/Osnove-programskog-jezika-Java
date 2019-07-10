package hr.fer.zemris.java.hw07.observer2;

/**
 * This interface models an observer for objects of type IntegerStorage.  
 * For more information see <a href="https://en.wikipedia.org/wiki/Observer_pattern">observer patter</a> 
 * wiki page.
 * @author Matija
 *
 */
public interface IntegerStorageObserver {
	
	/**
	 * Gets called after the value (of the storage this observers is
	 * observing) gets changed. Storage which value changed is given
	 * in the argument of this function wrapped in an object of type
	 * IntegerStorageChange.
	 * @param istorageChange storage which value changed
	 */
	public void valueChanged(IntegerStorageChange istorageChange);
}
