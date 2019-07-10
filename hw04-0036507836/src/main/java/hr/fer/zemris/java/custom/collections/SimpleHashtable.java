package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * An object that maps keys to values. A hashTable cannot contain duplicate keys; each key can map to at
 * most one value. Does not permit null keys; does permit null values.
 * 
 * @author Matija
 *
 * @param <K> the type of keys maintained by this hashTable
 * @param <V> the type of mapped values
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>>{
	
	private final static int DEFAULT_CAPACITY = 16;
	
	public int capacity;
	private int size;
	private TableEntry<K, V> table[];
	private long modificationCount = 0;

	/**
	 * Creates an empty hashTable with default capacity.
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Creates an empty hashTable with capacity provided. Initial capacity will be
	 * argument provided if it is a power of two, first greater power of two otherwise.
	 * @param capacity initial capacity of the hashTable
	 * @throws IllegalArgumentException if capacity is less than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity<1) throw new IllegalArgumentException();
		
		int powerOfTwo = 1;
		
		for(;powerOfTwo<capacity; powerOfTwo*=2);

		this.capacity = capacity;
		this.size = 0;
		this.table = (TableEntry<K, V>[]) new TableEntry[this.capacity];
	}
	
	/**
	 * Associates the specified value with the specified key in this hashTable. If the hashTable 
	 * previously contained a mapping for the key, the old value is replaced.
	 * @param key key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 */
	public void put(K key, V value) {
		if(key == null) throw new NullPointerException();
		
		checkTableSize();
		
		TableEntry<K, V> newEntry = new TableEntry<K, V>(key, value);
		
		int hashCode = Math.abs(key.hashCode())%capacity;
		TableEntry<K, V> e = table[hashCode];
		for(; e!=null && e.next!=null && !e.key.equals(key); e=e.next);
		
		if(e==null) {
			table[hashCode] = newEntry;
			modificationCount++;
			size++;
		} else if(e.key.equals(key)) {
			e.value = value;
		} else {
			e.next = newEntry;
			modificationCount++;
			size++;
		}
	}
	
	/**
	 * Returns the value to which the specified key is mapped, or null if this hashTable contains no mapping 
	 * for the key. More formally, if this hashTable contains a mapping from a key k to a value v such 
	 * that (key==null ? k==null : key.equals(k)), then this method returns v; otherwise it returns null. 
	 * (There can be at most one such mapping.) A return value of null does not necessarily indicate that 
	 * the hashTable contains no mapping for the key; it's also possible that the hashTable explicitly maps the key to 
	 * null. The containsKey operation may be used to distinguish these two cases.
	 * @param key the key whose associated value is to be returned
	 * @return the value to which the specified key is mapped, or null if this hashTable contains no mapping for the key
	 */
	public V get(Object key) {
		if(key == null) return null;
		
		int hashCode = Math.abs(key.hashCode())%capacity;
		TableEntry<K, V> e = table[hashCode];
		for(; e!=null && !e.key.equals(key); e=e.next);
		
		return (e!=null && e.key.equals(key)) ? e.value : null;
	}
	
	/**
	 * Returns the number of key-value mappings in this hashTable.
	 * @return the number of key-value mappings in this hashTable.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns true if this hashTable contains a mapping for the specified key. More formally, returns true 
	 * if and only if this hashTable contains a mapping for a key k such that Objects.equals(key, k). 
	 * (There can be at most one such mapping.)
	 * @param key key whose presence in this hashTable is to be tested
	 * @return true if this hashTable contains a mapping for the specified key
	 */
	public boolean containsKey(Object key) {
		if(key == null) return false;
		
		for(int i=0; i<capacity; i++) {
			if(table[i]==null) continue;
			
			for(TableEntry<K, V> e = table[i]; e!=null; e=e.next) {
				if(e.key.equals(key))
					return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns true if this hashTable maps one or more keys to the specified value. More formally, returns true 
	 * if and only if this hashTable contains at least one mapping to a value v such that Objects.equals(value, v).
	 * This operation will require time linear in the hashTable size.
	 * @param value value whose presence in this hashTable is to be tested
	 * @return true if this hashTable maps one or more keys to the specified value
	 */
	public boolean containsValue(Object value) {
		if(value==null) return false;
		
		for(int i=0; i<capacity; i++) {
			if(table[i]==null) continue;
			
			for(TableEntry<K, V> e = table[i]; e!=null; e=e.next) {
				if(e.value.equals(value))
					return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Removes the mapping for a key from this hashTable if it is present. More formally, if this hashTable
	 * contains a mapping from key k to value v such that Objects.equals(key, k), that mapping is removed. 
	 * (The hashTable can contain at most one such mapping.) The hashTable will not contain a mapping for 
	 * the specified key once the call returns.
	 * @param key key whose mapping is to be removed from the hashTable
	 */
	public void remove(Object key) {
		if(key == null) return;
		
		int hashCode = Math.abs(key.hashCode())%capacity;
		TableEntry<K, V> e = table[hashCode];
		
		//testing if slot is empty => no such element
		if(e==null) return;
		
		if(e!=null && e.key.equals(key)) {
			table[hashCode] = e.next;
		} else {
		
			//if not, find it
			for(; e.next!=null && !e.next.key.equals(key); e=e.next);
			
			//if we haven't found it
			if(e.next==null) return;
			
			//if we have found it
			e.next = e.next.next;
		
		}
		
		modificationCount++;
		size--;
	}
	
	/**
	 * Returns true if this hashTable contains no key-value mappings.
	 * @return true if this hashTable contains no key-value mappings
	 */
	public boolean isEmpty() {
		return size==0;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		boolean first = true;
		
		for(int i=0; i<capacity; i++) {
			if(table[i]==null) continue;
			
			for(TableEntry<K, V> e = table[i]; e!=null; e=e.next) {
				if(!first) {
					sb.append(", ");
				}
				sb.append(e.key + "=" + e.value);
				first = false;
			}
		}
		
		sb.append("]");
		
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	private void checkTableSize() {
		if((size+1.0)/capacity<0.75) return; //there is no need to change table size if it is less than 75% full
		
		modificationCount++;
		capacity*=2; //double the capacity
		TableEntry<K, V> oldTable[] = table;
		
		//create new table
		table = (TableEntry<K, V>[]) new TableEntry[capacity];
		size = 0;
		
		for(int index=0; index<capacity/2; index++) {
			if(oldTable[index]==null) continue;
			
			for(TableEntry<K, V> entry = oldTable[index]; entry!=null; entry=entry.next) {
				put(entry.key, entry.value);
			}
		}
	}
	
	public static class TableEntry<K, V>{
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		public K getKey() {
			return key;
		}
		
		public V getValue() {
			return value;
		}
		
		public void setValue(V value) {
			this.value = value;
		}
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		private long savedModificationCount;
		private int toBeReturned;
		private int index;
		private SimpleHashtable.TableEntry<K, V> lastReturned;
		private boolean removeCalled;
		
		public IteratorImpl() {
			savedModificationCount = SimpleHashtable.this.modificationCount;
			toBeReturned = SimpleHashtable.this.size;
		}
		
		public boolean hasNext() {
			if(savedModificationCount != SimpleHashtable.this.modificationCount) throw new ConcurrentModificationException();
			return toBeReturned!=0;
		}
		
		public SimpleHashtable.TableEntry<K, V> next() {
			if(savedModificationCount != SimpleHashtable.this.modificationCount) throw new ConcurrentModificationException();
			if(toBeReturned==0) throw new NoSuchElementException();
			
			removeCalled = false;
			
			if(lastReturned!=null && lastReturned.next!=null) {
				lastReturned = lastReturned.next;
			} else {
				for(index++; index<SimpleHashtable.this.capacity && SimpleHashtable.this.table[index]==null; index++);
				lastReturned = SimpleHashtable.this.table[index];
			}
			
			toBeReturned--;
			return lastReturned;
		}
		
		public void remove() {
			if(savedModificationCount != SimpleHashtable.this.modificationCount) throw new ConcurrentModificationException();
			if(removeCalled) throw new IllegalStateException();
			if(lastReturned==null) throw new NoSuchElementException();
			removeCalled = true;
			
			SimpleHashtable.this.remove(lastReturned.key);
			savedModificationCount++;
		}
	}

}
