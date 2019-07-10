package hr.fer.zemris.java.tecaj_13.web.forms;

import java.util.HashMap;
import java.util.Map;

public class NewCommentForm {

	private String email;
	private String message;
	
	private boolean valid = true;
	private Map<String, String> errors = new HashMap<>();
	
	public NewCommentForm(String email, String message) {
		this.email = email;
		this.message = message;
	}
	
	public boolean validate() {		
		if(!isValidEmail()) {
			valid = false;
		}
		
		if(message==null || message.isEmpty()) {
			valid = false;
			errors.put("message", "Message cannot be blank!");
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

	public Map<String, String> getErrors() {
		return errors;
	}

}
