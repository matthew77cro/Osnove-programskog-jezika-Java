package hr.fer.zemris.opjj.pred4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Kvartet<T> implements Iterable<T>{
	
	private T[] elems;

	@SuppressWarnings("unchecked")
	public Kvartet(T elem1, T elem2, T elem3, T elem4) {
		elems = (T[])new Object[4];
		elems[0] = elem1;
		elems[1] = elem2;
		elems[2] = elem3;
		elems[3] = elem4;
	}
	
	private class IteratorImpl implements Iterator<T> {
		
		private int nextIndex;

		@Override
		public boolean hasNext() {
			return nextIndex < Kvartet.this.elems.length;
		}

		@Override
		public T next() {
			if(!hasNext()) throw new NoSuchElementException();
			return Kvartet.this.elems[nextIndex++];
		}
		
	}

	@Override
	public Iterator<T> iterator() {
		return new IteratorImpl();
	}

}
