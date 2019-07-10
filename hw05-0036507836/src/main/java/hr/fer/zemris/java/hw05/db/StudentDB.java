package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.parser.QueryParser;

/**
 * This program represents a database which you can query by any of the attributes from the command line.<br/> <br/>
 * Examples:<br/>
 * query jmbag = "0000000003"<br/>
 * query jmbag = "0000000003" AND lastName LIKE "B*"<br/>
 * query jmbag = "0000000003" AND lastName LIKE "L*"<br/>
 * query lastName LIKE "B*"<br/>
 * query lastName LIKE "Be*"<br/>
 * @author Matija
 *
 */
public class StudentDB {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		StudentDatabase db = loadData();
		
		while(true) {
			System.out.print("> ");
			
			String upit = null;
			if(sc.hasNext()) {
				upit = sc.nextLine();
			} else {
				break;
			}
			if(upit.equalsIgnoreCase("exit")) break;
			
			List<StudentRecord> filtered = filter(upit, db);
			if(filtered==null) continue;
			
			System.out.printf(recordsToString(filtered));
		}
		
		System.out.println("Goodbye!");
		
		sc.close();
		
	}
	
	/**
	 * Loads the data for the database from the files and returns StudentDabase populated with that data.
	 * @return StudentDabase populated with data loaded from the file
	 */
	private static StudentDatabase loadData() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("src/main/resources/database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] arr = new String[lines.size()];
		lines.toArray(arr);
		return new StudentDatabase(arr);
	}
	
	/**
	 * Filters the database for the query user inputed. Returns list of student records
	 * which satisfy given query.
	 * @param upit query for the database (user input)
	 * @param db database to be queried
	 * @return student records which satisfy given query
	 */
	private static List<StudentRecord> filter(String upit, StudentDatabase db) {
		upit = upit.trim();
		
		//chechking if query is legal
		if(!upit.startsWith("query ")) {
			System.out.println("Not a valid query expression!");
			return null;
		}
		
		//parsing the query
		QueryParser parser = null;
		try {
			parser = new QueryParser(upit.substring(5));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
		
		if(parser.isDirectQuery()) {
			System.out.println("Using index for record retrieval.");
		}
		
		//printing out records
		List<StudentRecord> filtered = null;
		try {
			filtered = db.filter(new QueryFilter(parser.getQuery()));
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
		
		return filtered;
	}
	
	/**
	 * Prepares all student records from filter method for print to the user by
	 * creating a border and table-like visual structure.
	 * @param records records to be displayed in the table
	 * @return string to be printed to the user
	 */
	private static String recordsToString(List<StudentRecord> records) {
		if(records.size()==0) return "Records selected: 0%n%n";
		
		StringBuilder sb = new StringBuilder();
		
		int longestJMBAG = 0;
		int longestLastName = 0;
		int longestFirstName = 0;
		int longestGrade = 1;
		
		for(StudentRecord record : records) {
			if(record.getJmbag().length()>longestJMBAG) longestJMBAG = record.getJmbag().length();
			if(record.getLastName().length()>longestLastName) longestLastName = record.getLastName().length();
			if(record.getFirstName().length()>longestFirstName) longestFirstName = record.getFirstName().length();
		}
		
		String border = null;
		{
			StringBuilder line = new StringBuilder();
			
			line.append("+");
			for(int i=0; i<longestJMBAG+2; i++) line.append("=");
			line.append("+");
			for(int i=0; i<longestLastName+2; i++) line.append("=");
			line.append("+");
			for(int i=0; i<longestFirstName+2; i++) line.append("=");
			line.append("+");
			for(int i=0; i<longestGrade+2; i++) line.append("=");
			line.append("+");
			line.append("%n");
			
			border = line.toString();
		}
		
		//top border
		sb.append(border);
		
		for(StudentRecord record : records) {
			StringBuilder jmbag = new StringBuilder();
			StringBuilder lastName = new StringBuilder();
			StringBuilder firstName = new StringBuilder();
			
			jmbag.append(record.getJmbag());
			for(int i=0; i<longestJMBAG-record.getJmbag().length(); i++) jmbag.append(" ");
			lastName.append( record.getLastName());
			for(int i=0; i<longestLastName-record.getLastName().length(); i++) lastName.append(" ");
			firstName.append(record.getFirstName());
			for(int i=0; i<longestFirstName-record.getFirstName().length(); i++) firstName.append(" ");
			
			sb.append("| " + jmbag.toString() + " | " + lastName.toString() + " | " + firstName.toString() +
					" | " + record.getFinalGrade() + " |%n");
		}
		
		//bottom border
		sb.append(border);
		sb.append("Records selected: " + records.size() + "%n%n");
		
		return sb.toString();
	}

}
