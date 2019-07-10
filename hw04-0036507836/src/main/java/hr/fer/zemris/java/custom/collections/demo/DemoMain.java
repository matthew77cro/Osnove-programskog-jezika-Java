package hr.fer.zemris.java.custom.collections.demo;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

public class DemoMain {

	public static void main(String[] args) {

		System.out.println("PREPARING FIRST HASHTABLE AND CHECKING BASIC FUNCTIONALITY");
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		// query collection:
		Integer kristinaGrade = examMarks.get("Kristina");
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
		// What is collection's size? Must be four!
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
		
		System.out.println("CHECKING BASIC ITERATOR FUNCTIONALITY:");
		for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}
		
		System.out.println("CHECKING TWO ITERATORS FUNCTIONALITY:");
		for(SimpleHashtable.TableEntry<String,Integer> pair1 : examMarks) {
			for(SimpleHashtable.TableEntry<String,Integer> pair2 : examMarks) {
				System.out.printf(
						"(%s => %d) - (%s => %d)%n",
						pair1.getKey(), pair1.getValue(),
						pair2.getKey(), pair2.getValue()
						);
			}
		}
		
		System.out.println("CHECKING BASIC REMOVE IN ITERATOR FUNCTIONALITY:");
		System.out.println(examMarks);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		}
		System.out.println(examMarks);
		
		System.out.println("CHECKING FOR CONCURRENTMODIFICATIONEXCEPTION:");
		try {
			Iterator<SimpleHashtable.TableEntry<String,Integer>> iter2 = examMarks.iterator();
			while(iter2.hasNext()) {
				SimpleHashtable.TableEntry<String,Integer> pair = iter2.next();
				if(pair.getKey().equals("Jasna")) {
					examMarks.remove("Jasna");
				}
			}
		} catch (ConcurrentModificationException ex) {
			System.out.println("SUCCESS!");
		}

		System.out.println("EMPTYING THE TABLE:");
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter3 = examMarks.iterator();
		while(iter3.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter3.next();
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			iter3.remove();
		}
		System.out.printf("Veličina: %d%n", examMarks.size());
		
		System.out.println("CHECKING FOR ILLEGALSTATEEXCEPTION:");
		try {
			// create collection:
			SimpleHashtable<String, Integer> examMarks2 = new SimpleHashtable<>(2);
			// fill data:
			examMarks2.put("Ivana", 2);
			Iterator<SimpleHashtable.TableEntry<String,Integer>> iter4 = examMarks2.iterator();
			iter4.next();
			iter4.remove();
			iter4.remove();
		} catch (IllegalStateException ex) {
			System.out.println("SUCCESS!");
		}
		
	}

}
