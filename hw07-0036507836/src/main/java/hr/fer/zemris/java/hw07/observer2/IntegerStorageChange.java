package hr.fer.zemris.java.hw07.observer2;

/**
 * Models an event that happens when the value of an IntegerStorage is changed.
 * Provides information on new value, old value and access to the storage whose
 * value has been updated.
 * @author Matija
 *
 */
public class IntegerStorageChange {
	
	private IntegerStorage storage;
	private int oldValue;
	private int newValue;

	/**
	 * Creates and initialises a storage change event.
	 * @param storage storage whose value has been updated
	 * @param oldValue value of the storage previous to the change
	 * @param newValue value of the storage after the change
	 */
	public IntegerStorageChange(IntegerStorage storage, int oldValue, int newValue) {
		this.storage = storage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * Returns a storage whose value has been updated
	 * @return a storage whose value has been updated
	 */
	public IntegerStorage getStorage() {
		return storage;
	}
	
	/**
	 * Returns value of the storage previous to the change
	 * @return value of the storage previous to the change
	 */
	public int getOldValue() {
		return oldValue;
	}
	
	/**
	 * Returns value of the storage after the change
	 * @return value of the storage after the change
	 */
	public int getNewValue() {
		return newValue;
	}

}
