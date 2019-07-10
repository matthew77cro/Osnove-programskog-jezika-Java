package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Models a collection of prime numbers. This collection is iterable.
 * @author Matija
 *
 */
public class PrimesCollection implements Iterable<Integer>{
	
	private int numOfPrimes;

	/**
	 * Creates and initialises a new collection of prime numbers.
	 * @param numOfPrimes number of primes in the new collection
	 */
	public PrimesCollection(int numOfPrimes) {
		this.numOfPrimes = numOfPrimes;
	}
	
	/**
	 * Gets and returns the prime number at a specific index. <br/><br/>
	 * Examples:<br/>
	 * get(0) -> 2<br/>
	 * get(1) -> 3<br/>
	 * get(2) -> 5<br/>
	 * ...
	 * @param index index of a prime to be returned
	 * @return prime at a specific location specified by the argument index
	 * @throws IndexOutOfBoundsException if the index exceeds number of primes stored in this collection
	 */
	public int get(int index) {
		if(index>=numOfPrimes) throw new IndexOutOfBoundsException();
		
		int prime = 1;
		for(int i=0; i<=index; i++) {
			prime++;
			while(!isPrime(prime)) prime++;
		}
		
		return prime;
	}
	
	/**
	 * Checks whether a given number is prime or not. All
	 * negative numbers are considered not prime numbers.
	 * @param number number for which to check if it is prime
	 * @return true if number is prime, false otherwise
	 */
	private boolean isPrime(int number) {
		if(number<=1) return false;
		
        for (int i=2; i<=number/2; i++) {
            if (number%i == 0) {
                return false;
            }
        }
        return true;
    }

	@Override
	public Iterator<Integer> iterator() {
		return new PrimeIterator();
	}
	
	/**
	 * This class is a private implementation detail. It is an iterator used
	 * for iterating through the primes collection.
	 * @author Matija
	 *
	 */
	private class PrimeIterator implements Iterator<Integer> {
		
		int index = 0;

		@Override
		public boolean hasNext() {
			return index<numOfPrimes;
		}

		@Override
		public Integer next() {
			if(!hasNext()) throw new NoSuchElementException();
			int next = get(index);
			index++;
			return next;
		}
		
	}

}
