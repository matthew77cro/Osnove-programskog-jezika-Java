package hr.fer.zemris.java.custom.collections;

/**
 * A collection represents a group of objects, known as its elements. 
 * Some collections allow duplicate elements and others do not. Some are 
 * ordered and others unordered. This class is typically used to pass collections 
 * around and manipulate them where maximum generality is desired. This class
 * does not provide any direct implementations and therefore should not be
 * instanced.
 * @author Matija
 *
 */
public class Collection {

	protected Collection() {
		
	}
	
	/**
	 * Returns true if this collection contains no elements.
	 * @return true if this collection contains no elements
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Returns the number of elements in this collection.
	 * @return the number of elements in this collection
	 */
	public int size(){
		return 0;
	}
	
	/**
	 * Ensures that this collection contains the specified element.
	 * @param value element whose presence in this collection is to be ensured
	 * @throws NullPointerException if the specified element is null and this collection does not permit null elements
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Returns true if this collection contains the specified element. More formally, returns 
	 * true if and only if this collection contains at least one element e such that  <code>(o==null ? e==null : o.equals(e))</code>.
	 * @param value element whose presence in this collection is to be tested
	 * @return true if this collection contains the specified element
	 */
	public boolean contains(Object value){
		return false;
	}
	
	/**
	 * Removes a single instance of the specified element from this collection, if it is present. More formally, removes 
	 * an element e such that (o==null ? e==null : o.equals(e)), if this collection contains one or more such elements. 
	 * Returns true if this collection contained the specified element (or equivalently, if this collection changed as a result of the call).
	 * @param value element to be removed from this collection, if present
	 * @return true if an element was removed as a result of this call, false otherwise
	 */
	public boolean remove(Object value){
		return false;
	}
	
	/**
	 * Returns an array containing all of the elements in this collection. The returned array will be "safe" in that no references to it 
	 * are maintained by this collection. (In other words, this method must allocate a new array even if this collection is backed by an array). 
	 * The caller is thus free to modify the returned array.
	 * @return an array containing all of the elements in this collection
	 * @throws UnsupportedOperationException if operation is not supported for specified implementation of collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Performs the given action for each element of the collection until all elements have been processed or the action throws an exception. 
	 *  Exceptions thrown by the action are relayed to the caller.
	 * @param processor The action to be performed for each element
	 */
	public void forEach(Processor processor){
		
	}
	
	/**
	 * Adds all of the elements in the specified collection to this collection.
	 * @param other collection containing elements to be added to this collection
	 */
	public void addAll(Collection other) {
		class AddToThisCollection extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		other.forEach(new AddToThisCollection());
	}
	
	/**
	 * Removes all of the elements from this collection. The collection will be empty after this method returns.
	 */
	public void clear() {
		
	}

}
