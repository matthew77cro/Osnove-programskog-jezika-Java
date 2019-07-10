package hr.fer.zemris.java.custom.collections;

/**
 * Doubly-linked list implementation of the Collection class. General contract of this collection is: duplicate elements 
 * are allowed (each of those element will be held in different list node); storage of null references is not allowed. 
 * In addition to extending the Collection class, this class provides  some additional methods for manipulating this collection.
 * @author Matija
 *
 */
public class LinkedListIndexedCollection extends Collection {
	
	/**
	 * 
	 * Class used to represent a Node in doubly-linked list.
	 * @author Matija
	 *
	 */
	private static class ListNode{
		ListNode previous;
		ListNode next;
		Object value;
	}
	
	private int size;
	private ListNode first;
	private ListNode last;

	/**
	 * Creates an empty collection with size 0 and no elements in it.
	 */
	public LinkedListIndexedCollection() {
		size = 0;
		first = null;
		last = null;
	}
	
	/**
	 * Creates a collection with elements copied from some other collection
	 * given via constructor parameter.
	 * @param other Some other collection which elements will be present in this collection.
	 */
	public LinkedListIndexedCollection(Collection other) {
		this();
		if(other==null) throw new NullPointerException();
		
		addAll(other);
	}
	
	//JavaDoc is inherited from Collection class
	@Override
	public void add(Object value) {
		if(value==null) throw new NullPointerException();
		
		ListNode node = new ListNode();
		node.value = value;
		node.previous = null;
		node.next = null;
		
		if(size==0) {
			first = last = node;
		} else {
			last.next = node;
			node.previous = last;
			last = node;
		}
		
		size++;
	}
	
	/**
	 * Returns the object that is stored in linked list at position index. Valid indexes are 0 to size-1. If index is
	 * invalid, the IndexOutOfBoundsException will be thrown. Complexity is never greater than n/2+1.
	 * @param index Index of an element to be returned. Valid indexes are 0 to size-1.
	 * @return element at specified index
	 * @throws IndexOutOfBoundsException if index is &lt; 0 or &gt; size-1
	 */
	public Object get(int index) {
		if(index<0 || index>size-1) throw new IndexOutOfBoundsException(index);
		
		ListNode temporaryNode;
		if(index<size/2) {
			temporaryNode = first;
			for(int i=0; i<index; i++, temporaryNode = temporaryNode.next);
		} else {
			temporaryNode = last;
			for(int i=size-1; i>index; i--, temporaryNode = temporaryNode.previous);
		}
		
		return temporaryNode.value;
	}
	
	//JavaDoc is inherited from Collection class
	@Override
	public void clear() {
		size = 0;
		first = null;
		last = null;
	}
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in linked-list. Elements starting from
	 * this position are shifted one position. The legal positions are 0 to size of the collection. If position is invalid,
	 * a NullPointerException will be thrown.
	 * @param value element to be inserted in the collection
	 * @param position Index at element should be inserted
	 * @throws NullPointerException if value is null
	 * @throws IndexOutOfBoundsException if position is &lt; 0 or &gt; size
	 */
	public void insert(Object value, int position) {
		if(value == null) throw new NullPointerException();
		if(position<0 || position>size) throw new IndexOutOfBoundsException(position);
		
		ListNode node = new ListNode();
		node.value = value;
		node.previous = null;
		node.next = null;
		
		//if size==position or position==0
		if(position==size) {
			add(value);
			return;
		} else if (position==0) {
			first.previous = node;
			node.next = first;
			first = node;
		} else {
			//if it is in the middle or at the beginning
			ListNode temporaryNode;
			if(position<size/2) {
				temporaryNode = first;
				for(int i=0; i<position; i++, temporaryNode = temporaryNode.next);
			} else {
				temporaryNode = last;
				for(int i=size-1; i>position; i--, temporaryNode = temporaryNode.previous);
			}
			temporaryNode.previous.next = node;
			node.previous = temporaryNode.previous;
			temporaryNode.previous = node;
			node.next = temporaryNode;
		}
		
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
		if(value == null) throw new NullPointerException();

		ListNode temporaryNode = first;
		for(int i=0; i<size; i++, temporaryNode = temporaryNode.next) {
			if(value.equals(temporaryNode.value)) return i;
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
		
		ListNode temporaryNode;
		if(index<size/2) {
			temporaryNode = first;
			for(int i=0; i<index; i++, temporaryNode = temporaryNode.next);
		} else {
			temporaryNode = last;
			for(int i=size-1; i>index; i--, temporaryNode = temporaryNode.previous);
		}
		temporaryNode.previous.next = temporaryNode.next;
		temporaryNode.next.previous = temporaryNode.previous;
		size--;
	}
	
	//JavaDoc is inherited from Collection class
	@Override
	public int size() {
		return size;
	}
	
	//JavaDoc is inherited from Collection class
	@Override
	public boolean contains(Object value) {
		if(value==null) return false;
		
		for(ListNode temporaryNode = first; temporaryNode!=null; temporaryNode=temporaryNode.next) {
			if(value.equals(temporaryNode.value)) return true;
		}
		
		return false;
	}
	
	//JavaDoc is inherited from Collection class
	@Override
	public boolean remove(Object value) {
		if(value == null) throw new NullPointerException();
		
		for(ListNode temporaryNode = first; temporaryNode!=null; temporaryNode=temporaryNode.next) {
			if(value.equals(temporaryNode.value)) {
				temporaryNode.previous.next = temporaryNode.next;
				temporaryNode.next.previous = temporaryNode.previous;
				size--;
				return true;
			}
		}
		
		return false;
	}
	
	//JavaDoc is inherited from Collection class
	@Override
	public Object[] toArray() {
		Object[] objects = new Object[size];
		
		ListNode temporaryNode = first;
		for(int i=0; i<size; i++, temporaryNode = temporaryNode.next) {
			objects[i]=temporaryNode.value;
		}
		
		return objects;
	}
	
	//JavaDoc is inherited from Collection class
	@Override
	public void forEach(Processor processor) {
		if(processor==null) throw new NullPointerException();
		
		for(ListNode temporaryNode = first; temporaryNode!=null; temporaryNode=temporaryNode.next) {
			processor.process(temporaryNode.value);
		}
	}

}
