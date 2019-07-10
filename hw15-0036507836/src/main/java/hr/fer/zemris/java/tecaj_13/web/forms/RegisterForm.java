package hr.fer.zemris.java.tecaj_13.web.forms;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.hw15.models.BlogUser;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

public class RegisterForm {

	private String firstName;
	private String lastName;
	private String email;
	private String nick;
	private String password;
	
	private boolean valid = true;
	private Map<String, String> errors = new HashMap<>();
	
	public RegisterForm(String firstName, String lastName, String email, String nick, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.nick = nick;
		this.password = password;
	}
	
	public boolean validate() {
		if(firstName==null || firstName.length()==0) {
			valid = false;
			errors.put("firstName", "First name cannot be blank!");
		}
		
		if(lastName==null || lastName.length()==0) {
			valid = false;
			errors.put("lastName", "Last name cannot be blank!");
		}
		
		if(!isValidEmail()) {
			valid = false;
		}
		
		if(!isValidNick()) {
			valid = false;
		}
		
		if(password==null || password.isEmpty()) {
			valid = false;
			errors.put("password", "Password cannot be blank!");
		}
			
		return valid;
	}

	private boolean isValidEmail() {

		if(email!=null && email.matches("\\p{Alnum}+@\\p{Alnum}+\\.\\p{Alnum}+")) {
			return true;
		}
		
		errors.put("email", "Email is not in the correct format!");
		return false;
		
	}
	
	private boolean isValidNick() {
		
		if(nick==null || nick.length()==0) {
			errors.put("nick", "Nick cannot be empty!");
			return false;
		}
		
		BlogUser user = DAOProvider.getDAO().getUser(nick);
		if(user==null) {
			return true;
		}
		
		errors.put("nick", "Nick is already in use!");
		return false;
		
	}
	
	public String hashPassword() {
		if(password==null) throw new NullPointerException("Password is null!");
		return Util.hash(password);
	}

	public Map<String, String> getErrors() {
		return errors;
	}

}
