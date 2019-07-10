package hr.fer.zemris.jsdemo.servlets;

public class Quote {

	private String author;
	private String text;
	private String[] topics;
	private boolean danger;

	public Quote(String author, String text, String ... topics) {
		this(author, text, false, topics);
	}
	
	public Quote(String author, String text, boolean danger, String ... topics) {
		super();
		this.author = author;
		this.text = text;
		this.danger = danger;
		this.topics = topics;
	}

	public String getAuthor() {
		return author;
	}
	
	public String getText() {
		return text;
	}

	public String[] getTopics() {
		return topics;
	}
	
	public boolean isDanger() {
		return danger;
	}
}
