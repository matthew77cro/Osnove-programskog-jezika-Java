package hr.fer.zemris.java.tecaj_13.web.forms;

import java.util.HashMap;
import java.util.Map;

public class EntryForm {

	private String title;
	private String text;
	
	private boolean valid = true;
	private Map<String, String> errors = new HashMap<>();
	
	public EntryForm(String title, String text) {
		this.title = title;
		this.text = text;
	}
	
	public boolean validate() {
		if(title==null || title.length()==0) {
			valid = false;
			errors.put("title", "Title cannot be blank!");
		}
		
		if(text==null || text.length()==0) {
			valid = false;
			errors.put("text", "Text cannot be blank!");
		}
		
		return valid;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

}
