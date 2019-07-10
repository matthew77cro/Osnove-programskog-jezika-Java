package hr.fer.zemris.java.custom.collections;

/**
 * 
 * Resizable-array implementation of the Collection class. General contract of this collection is: duplicate elements 
 * are allowed; storage of null references is not allowed. In addition to extending the Collection class, this class provides 
 * some additional methods for manipulating this collection.
 * 
 * @author Matija
 *
 */
public class ArrayIndexedCollection extends Collection {

	private static final int DEFAULT_CAPACITY = 16;
	private static final int INITIAL_SIZE = 0;
	
	private int size;
	private int capacity;
	private Object elements[];
	
	/**
	 * The default constructor creates an instance with capacity set to 16.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * This constructor creates an instance with capacity set to initalCapacity.
	 * @param initialCapacity initial value of capacity of the collection
	 * @throws IllegalArgumentException if initalCapcity is less than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity<1) throw new IllegalArgumentException();
		
		size = INITIAL_SIZE;
		capacity = initialCapacity;
		elements = new Object[capacity];
	}
	
	/**
	 * This constructor accepts a non-null reference to some other Collection which elements are copied into this newly
	 * constructed collection. If the given collection is null, a NullPointerException will be thrown. 
	 * @param other Collection whose elements should be present in this collection.
	 * @throws NullPointerException if other is null
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}
	
	/**
	 * This constructor accepts a non-null reference to some other Collection which elements are copied into this newly
	 * constructed collection; if the initialCapacity is smaller than the size of the given collection, the size of
	 * the given collection will be used for elements array preallocation. If the given collection is null, a
	 * NullPointerException will be thrown. 
	 * @param other Collection whose elements should be present in this collection.
	 * @param initialCapacity initialCapacity of this collection
	 * @throws NullPointerException if other is null
	 * @throws IllegalArgumentException if initalCapcity is less than 1
	 */
	public ArrayIndexedCollection(Collection other,  int initialCapacity) {
		if(other == null) throw new NullPointerException();
		if(initialCapacity<1) throw new IllegalArgumentException();
		
		size = INITIAL_SIZE;
		int sizeOfOtherCollection = other.size();
		capacity = initialCapacity < sizeOfOtherCollection ?  sizeOfOtherCollection : initialCapacity;
		elements = new Object[capacity];
		addAll(other);
	}
	
	//JavaDoc is inherited from Collection class
	@Override
	public int size() {
		return size;
	}
	
	//JavaDoc is inherited from Collection class
	@Override
	public void add(Object value) {
		if(value == null) throw new NullPointerException();
		
		if(size==capacity) {
			resize();
		}
		elements[size] = value;
		size++;
	}
	
	/**
	 * Doubles the capacity of a collection.
	 */
	private void resize() {
		capacity*=2;
		elements = copyOf(elements, capacity);
	}
	
	/**
	 * Makes a new copy of an array of objects with size set to newLength. If size of
	 * original array is greater than newLength, some elements will not be in the
	 * new array.
	 * @param original array from which the elements are copied
	 * @param newLength length of new array
	 * @return new array of objects with elements from original
	 */
	private Object[] copyOf(Object[] original, int newLength) {
		int stopCondition =  original.length<newLength ? original.length : newLength;
		Object[] newArray = new Object[newLength];
		
		for(int i=0; i<stopCondition; i++) {
			newArray[i] = original[i];
		}
		
		return newArray;
	}
	
	/**
	 * Returns the object that is stored in backing array at position index. Valid indexes are 0 to size-1. If index
	 * is invalid, the implementation will throw the appropriate exception (IndexOutOfBoundsException).
	 * @throws IndexOutOfBoundsException if index &lt; 0 or &gt; size-1
	 * @param index Index of the element that needs to be returned. Must be in interval [0, size-1]
	 * @return elements of the collection at specified index
	 */
	public Object get(int index) {
		if(index<0 || index>size-1) throw new IndexOutOfBoundsException(index);
		return elements[index];
	}
	
	@Override
	public void clear() {
		for(int i=0; i<size; i++) {
			elements[i]=null;
		}
		size=0;
	}
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in array (before actual
	 * insertion elements at position and at greater positions will be shifted one place toward the end, so that an
	 * empty place is created at position). The legal positions are 0 to size (both are included). If position is
	 * invalid, an appropriate exception will be thrown (IndexOutOfBoundsException).
	 * @param value element to be inserted into the collection
	 * @param position index at which the element should appear
	 * @throws NullPointerException if value passed is null
	 * @throws IndexOutOfBoundsException if position is &lt; 0 or &gt; size of the collection
	 */
	public void insert(Object value, int position) {
		if(value==null) throw new NullPointerException();
		if(position<0 || position>size) throw new IndexOutOfBoundsException(position);
		
		//if we need to add it to the end
		if(position==size) {
			add(value);
			return;
		}
		
		//if we need to add it to position that is not the end position
		if(size==capacity) {
			resize();
		}
		//shifting the elements
		for(int i=size; i>position; i--) {
			elements[i]=elements[i-1];
		}
		elements[position]=value;
		size++;
	}
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is
	 * not found. Argument can be null and the result will be that this element is not found (since the collection
	 * can not contain null). The equality will be determined using the equals method.
	 * @param value element for which index should be returned
	 * @return index of element given in the value parameter
	 */
	public int indexOf(Object value) {
		if(value==null) return -1;
		
		for(int i=0; i<size; i++) {
			if(value.equals(elements[i])) return i;
		}
		
		return -1;
	}
	
	/**
	 * Removes element at specified index from collection. Element that was previously at location index+1
	 * after this operation is on location index, etc. Legal indexes are 0 to size-1. In case of invalid index method
	 * throws IndexOutOfBoundsException.
	 * @param index Index at which an element should be removed. Legal indexes are 0 to size-1.
	 * @throws IndexOutOfBoundsException if index is &lt; 0 or &gt; size-1
	 */
	public void remove(int index) {
		if(index<0 || index>size-1) throw new IndexOutOfBoundsException(index);
		
		for(int i=index; i<size-1; i++) {
			elements[i]=elements[i+1];
		}
		elements[size-1]=null;
		size--;
	}
	
	//JavaDoc is inherited from Collection class
	@Override
	public boolean contains(Object value) {
		if(value==null) return false;
		
		for(int i=0; i<size; i++) {
			if(elements[i].equals(value)) return true;
		}
		
		return false;
	}
	
	//JavaDoc is inherited from Collection class
	@Override
	public boolean remove(Object value) {
		if(value==null) return false;
		
		int index = indexOf(value);
		if(index==-1) return false;
		remove(index);
		return true;
	}
	
	//JavaDoc is inherited from Collection class
	@Override
	public Object[] toArray() {
		return copyOf(elements, size);
	}
	
	//JavaDoc is inherited from Collection class
	@Override
	public void forEach(Processor processor) {
		if(processor==null) throw new NullPointerException();
		
		for(int i=0; i<size; i++) {
			processor.process(elements[i]);
		}
	}

}
