package hr.fer.zemris.web.demo;

// Ovo je Java-bean
public class User {

	private String lastName;
	private String firstName;
	
	public User() {
	}
	
	public User(String lastName, String firstName) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}