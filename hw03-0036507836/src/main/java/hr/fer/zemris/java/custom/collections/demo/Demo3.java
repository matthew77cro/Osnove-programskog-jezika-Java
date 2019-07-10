package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

public class Demo3 {

	public static void main(String[] args) {
		
		Collection col2 = new LinkedListIndexedCollection();
		col2.add("Ivo");
		col2.add("Ana");
		col2.add("Jasna");
		ElementsGetter getter2 = col2.createElementsGetter();
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		col2.clear(); //treba bacit ConcurrentModificationException
		System.out.println("Jedan element: " + getter2.getNextElement());
		
	}

}
