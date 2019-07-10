package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 
 * This class models a database of student records.
 * 
 * @author Matija
 *
 */
public class StudentDatabase {

	private String[] data;
	private List<StudentRecord> records;
	private Map<String, StudentRecord> indexedRecords;
	
	/**
	 * Constructor for creating a new database out of array of strings.
	 * Each element of the string array must represent a single student
	 * record. Elements must be if order jmbag-lastName-firstName-finalGrade
	 * all separated by tabs. Each fiels can have one or more spaces.
	 * @param data data from which to create student records
	 * @throws NullPointerException if data is null
	 * @throws IllegalArgumentException if there are duplicate JMBAGs OR too many or too little fields in each element OR grade is not an integer between 1 and 5.
	 */
	public StudentDatabase(String[] data) {
		this.data = Objects.requireNonNull(data);
		records = new ArrayList<StudentRecord>();
		indexedRecords = new HashMap<String, StudentRecord>();
		
		fillDatabase();
	}
	
	/**
	 * Populates the database with given data
	 * @throws IllegalArgumentException if data has illegal values for any of the parameters
	 */
	private void fillDatabase() {
		Set<String> jmbags = new HashSet<>(); //to check for duplicate jmbags
		
		for(String row : data) {
			String[] rowSplit = row.split("\t");
			if(rowSplit.length!=4) throw new IllegalArgumentException();
			
			if(!jmbags.add(rowSplit[0])) throw new IllegalArgumentException("Duplicate JMBAGs for " + rowSplit[0] + "!");
			int grade;
			try {
				grade = Integer.parseInt(rowSplit[3]);
			} catch(NumberFormatException ex) {
				throw new IllegalArgumentException("Grade must be an integer!");
			}
			if(grade<1 || grade>5) throw new IllegalArgumentException("Grade must an integer between 1 and 5!");
			
			StudentRecord record = new StudentRecord(rowSplit[0], rowSplit[1], rowSplit[2], grade);
			records.add(record);
			indexedRecords.put(rowSplit[0], record);
		}
	}
	
	/**
	 * Returns a student record that has a given jmbag or null if there are not such recrods.
	 * @param jmbag filter for selecting a record by jmbag
	 * @return a student record that has a given jmbag or null if there are not such recrods
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if(jmbag==null) return null;
		return indexedRecords.get(jmbag);
	}
	
	/**
	 * Returns a list of student records that satisfy given filter
	 * @param filter filter by which to select student records
	 * @return  a list of student records that satisfy given filter
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> list = new ArrayList<StudentRecord>();
		
		for(StudentRecord record : records) {
			if(filter.accepts(record)) {
				list.add(record);
			}
		}
		
		return list;
	}

}
