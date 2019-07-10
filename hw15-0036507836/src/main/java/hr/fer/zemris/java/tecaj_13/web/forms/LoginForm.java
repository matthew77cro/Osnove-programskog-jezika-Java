package hr.fer.zemris.java.tecaj_13.web.forms;

import java.util.HashMap;
import java.util.Map;

public class LoginForm {

	private String nick;
	private String password;
	
	private boolean valid = true;
	private Map<String, String> errors = new HashMap<>();
	
	public LoginForm(String nick, String password) {
		this.nick = nick;
		this.password = password;
	}
	
	public boolean validate() {
		if(nick==null || nick.length()==0) {
			errors.put("nick", "Nick cannot be empty!");
			return false;
		}
		
		if(password==null || password.isEmpty()) {
			valid = false;
			errors.put("password", "Password cannot be blank!");
		}
			
		return valid;
	}
	
	public String hashPassword() {
		if(password==null) throw new NullPointerException("Password is null!");
		return Util.hash(password);
	}

	public Map<String, String> getErrors() {
		return errors;
	}

}
