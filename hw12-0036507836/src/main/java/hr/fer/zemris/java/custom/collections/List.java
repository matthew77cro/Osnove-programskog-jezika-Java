package hr.fer.zemris.java.custom.collections;

/**
 * An ordered collection (also known as a sequence). The user of this interface has precise control over where 
 * in the list each element is inserted. The user can access elements by their integer index (position in the list), 
 * and search for elements in the list.
 * @author Matija
 *
 */
public interface List extends Collection {
	
	/**
	 * Returns the element at the specified position in this list.
	 * @param index index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size())
	 */
	Object get(int index) throws IndexOutOfBoundsException;
	
	/**
	 * Inserts the specified element at the specified position in this list. Shifts the element currently 
	 * at that position (if any) and any subsequent elements to the right (adds one to their indices).
	 * @param value element to be inserted
	 * @param position index at which the specified element is to be inserted
	 * @throws NullPointerException if the specified element is null and this list does not permit null elements
	 * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size())
	 */
	void insert(Object value, int position) throws NullPointerException, IndexOutOfBoundsException;
	
	/**
	 * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain 
	 * the element. More formally, returns the lowest index i such that Objects.equals(o, get(i)), or -1 if there is no 
	 * such index.
	 * @param value element to search for
	 * @return the index of the first occurrence of the specified element in this list, or -1 if this list does not contain 
	 * the element
	 */
	int indexOf(Object value);
	
	/**
	 * Removes the element at the specified position in this list (optional operation). Shifts any subsequent elements to 
	 * the left (subtracts one from their indices).
	 * @param index the index of the element to be removed
	 */
	void remove(int index);

}
