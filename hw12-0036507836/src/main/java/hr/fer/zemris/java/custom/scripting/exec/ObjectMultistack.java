package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Models a stack-like collection that is wrapped in a map-like (dictionary) object.
 * Every key in the map corresponds with a value which is a stack. (There are multiple
 * stacks identified with a key of type String).
 * @author Matija
 *
 */
public class ObjectMultistack {
	
	private Map<String, MultistackEntry> multiStack;

	/**
	 * Creates and initialises a new multistack objeck.
	 */
	public ObjectMultistack() {
		multiStack = new HashMap<String, MultistackEntry>();
	}
	
	/**
	 * Places a new value wrapper on top of a stack
	 * identified with a keyName.
	 * @param keyName identifier of a specific stack on which to push a valueWrapper
	 * @param valueWrapper value to push on a specified stack
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		
		MultistackEntry entry = new MultistackEntry(valueWrapper);
		multiStack.merge(keyName, entry, (oldVal, newVal) -> {
			newVal.next = oldVal;
			return newVal;
		});
		
	}
	
	/**
	 * Removes and returns a value from the top of the stack
	 * identified with a keyName.
	 * @param keyName identifier of a specific stack from which to remove a valueWrapper
	 * @return value removed from the stack
	 * @throws EmptyStackException if the stack with that key is empty
	 */
	public ValueWrapper pop(String keyName) {
		MultistackEntry entry = multiStack.get(keyName);
		if(entry==null) throw new EmptyStackException();
		
		if(entry.next==null) {
			multiStack.remove(keyName);
		} else {
			multiStack.put(keyName, entry.next);
		}
		return entry.value;
	}
	
	/**
	 * Returns a value from the top of the stack
	 * identified with a keyName without removing it.
	 * @param keyName identifier of a specific stack from which to return a valueWrapper
	 * @return value removed from the stack
	 * @throws EmptyStackException if the stack with that key is empty
	 */
	public ValueWrapper peek(String keyName) {
		MultistackEntry entry = multiStack.get(keyName);
		if(entry==null) throw new EmptyStackException();
		return entry.value;
	}
	
	/**
	 * Returns true iff the stack identified with the given keyName is empty
	 * (has no elements).
	 * @param keyName identifier of a specific stack for which to check emptiness
	 * @return true if the stack is empty, false otherwise
	 */
	public boolean isEmpty(String keyName) {
		return multiStack.get(keyName)==null;
	}
	
	/**
	 * This class models an entry for a ObjectMultistack stack.
	 * @author Matija
	 *
	 */
	private static class MultistackEntry {
		
		private ValueWrapper value;
		private MultistackEntry next;
		
		/**
		 * Creates and initialises a new stack entry
		 * @param value value of the entry
		 * @param next next entry in the stack (entry below this one).
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Creates and initialises a new stack entry
		 * @param value value of the entry
		 */
		public MultistackEntry(ValueWrapper value) {
			this(value,null);
		}

		@Override
		public int hashCode() {
			return Objects.hash(next, value);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof MultistackEntry))
				return false;
			MultistackEntry other = (MultistackEntry) obj;
			return Objects.equals(next, other.next) && Objects.equals(value, other.value);
		}
		
	}

}
