package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Models a student record for a student database
 * 
 * @author Matija
 *
 */
public class StudentRecord {

	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;
	
	/**
	 * Constructor for creating a new student record
	 * @param jmbag student's jmbag
	 * @param lastName student's last name
	 * @param firstName student's first name
	 * @param finalGrade student's final grade
	 * @throws NullPointerException if any of the arguments is null
	 * @throws IllegalArgumentException if finalGrade<1 or finalGrade>5
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		if(finalGrade<1 || finalGrade>5) throw new IllegalArgumentException();
		
		this.jmbag = Objects.requireNonNull(jmbag);
		this.lastName = Objects.requireNonNull(lastName);
		this.firstName = Objects.requireNonNull(firstName);
		this.finalGrade = finalGrade;
	}

	/**
	 * Returns student's jmbag
	 * @return student's jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns student's last name
	 * @return student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns student's first name
	 * @return student's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns student's final grade
	 * @return student's final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}
	
}
