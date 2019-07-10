package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * 
 * An object that maps keys to values. A hashTable cannot contain duplicate keys; each key can map to at
 * most one value. Does not permit null keys; does permit null values.
 * 
 * @author Matija
 *
 * @param <K> the type of keys maintained by this dictionary
 * @param <V> the type of mapped values
 */
public class Dictionary<K,V> {
	
	private ArrayIndexedCollection<DictionaryEntry<K,V>> collection;

	/**
	 * Creates an empty dictionary.
	 */
	public Dictionary() {
		collection = new ArrayIndexedCollection<Dictionary.DictionaryEntry<K,V>>();
	}
	
	/**
	 * Returns true if this dictionary contains no key-value mappings.
	 * @return true if this dictionary contains no key-value mappings
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	
	/**
	 * Returns the number of key-value mappings in this dictionary.
	 * @return the number of key-value mappings in this dictionary.
	 */
	public int size() {
		return collection.size();
	}
	
	/**
	 * Removes all of the mappings from this dictionary. The dictionary will be empty after this call returns.
	 */
	public void clear() {
		collection.clear();
	}
	
	/**
	 * Associates the specified value with the specified key in this dictionary. If the dictionary 
	 * previously contained a mapping for the key, the old value is replaced.
	 * @param key key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 * @throws NullPointerException if key is null
	 */
	public void put(K key, V value) { // "gazi" eventualni postojeći zapis
		
		if(key==null) throw new NullPointerException();
		
		ElementsGetter<DictionaryEntry<K, V>> getter = collection.createElementsGetter();
		
		while(getter.hasNextElement()) {
			DictionaryEntry<K, V> entry = getter.getNextElement();
			if(entry.getKey().equals(key)) {
				entry.setValue(value);
				return;
			}
		}
			
		collection.add(new DictionaryEntry<K,V>(key, value));
		
	}
	
	/**
	 * Returns the value to which the specified key is mapped, or null if this dictionary contains no mapping 
	 * for the key. More formally, if this dictionary contains a mapping from a key k to a value v such 
	 * that (key==null ? k==null : key.equals(k)), then this method returns v; otherwise it returns null. 
	 * (There can be at most one such mapping.) A return value of null does not necessarily indicate that 
	 * the dictionary contains no mapping for the key; it's also possible that the dictionary explicitly maps the key to 
	 * null. The containsKey operation may be used to distinguish these two cases.
	 * @param key the key whose associated value is to be returned
	 * @return the value to which the specified key is mapped, or null if this dictionary contains no mapping for the key
	 */
	public V get(Object key) { // ako ne postoji pripadni value, vraća null
		if(key==null) return null;
		
		ElementsGetter<DictionaryEntry<K, V>> getter = collection.createElementsGetter();

		while(getter.hasNextElement()) {
			DictionaryEntry<K, V> entry = getter.getNextElement();
			if(entry.getKey().equals(key)) {
				return entry.getValue();
			}
		}
		
		return null;
	}
	
	private static class DictionaryEntry<K,V> {
		
		K key;
		V value;
		
		public DictionaryEntry(K key, V value) {
			if(key==null) throw new NullPointerException();
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

		@Override
		public int hashCode() {
			return Objects.hash(key, value);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof DictionaryEntry))
				return false;
			DictionaryEntry<?,?> other = (DictionaryEntry<?,?>) obj;
			return Objects.equals(key, other.key) && Objects.equals(value, other.value);
		}
		
	}

}
