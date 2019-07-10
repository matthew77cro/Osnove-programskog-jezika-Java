package hr.fer.zemris.jsdemo.servlets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuotesDB {

	private static final Quote[] quotes = {
		new Quote(
			"Albert Einstein", 
			"Only two things are infinite, the universe and human stupidity, and I'm not sure about the former.",
			new String[] {"Science", "About", "Former", "Human", "Infinite", "Only", "Stupidity", "Sure", "Things", "Two", "Universe"}
		),
		new Quote(
			"Albert Einstein", 
			"The only reason for time is so that everything doesn't happen at once.",
			new String[] {"Time", "Everything", "Happen", "Once", "Only", "Reason"}
		),
		new Quote(
			"Aristotle", 
			"The roots of education are bitter, but the fruit is sweet.",
			new String[] {"Education", "Fruit"}
		),
		new Quote(
			"Aristotle", 
			"The one exclusive sign of thorough knowledge is the power of teaching.",
			new String[] {"Teacher", "Power", "Knowledge"}
		),
		new Quote(
			"Douglas Adams", 
			"A common mistake that people make when trying to design something completely foolproof is to underestimate the ingenuity of complete fools.",
			new String[] {"Design", "Foolproof", "Common"}
		),
		new Quote(
			"Douglas Adams", 
			"I seldom end up where I wanted to go, but almost always end up where I need to be.",
			new String[] {"Almost", "Seldom"}
		),
		new Quote(
			"Isaac Asimov", 
			"People who think they know everything are a great annoyance to those of us who do.",
			new String[] {"Funny", "Great"}
		),
		new Quote(
			"Isaac Asimov", 
			"If knowledge can create problems, it is not through ignorance that we can solve them.",
			new String[] {"Knowledge", "Ignorance", "Create"}
		),
		new Quote(
			"Marko Čupić", 
			"Primjer napada.<img src=\"xxx\" onerror=\'window.location.assign(\"http://www.google.com/?gws_rd=ssl#q=javascript+injection+attack\");\'>",
			true,
			new String[] {"Knowledge", "Ignorance", "Attack"}
		)
	};


	public static int getNumberOfQoutes() {
		return quotes.length;
	}
	
	public static Quote getQuote(int index) {
		return quotes[index];
	}

	public static Quote getRandomQuote() {
		Random r = new Random();
		while(true) {
			Quote q = quotes[r.nextInt(quotes.length)];
			if(q.isDanger()) continue;
			return q;
		}
	}

	public static List<Quote> getRandomSelection() {
		List<Quote> list = new ArrayList<>();
		Random r = new Random();
		for(int i = 0; i < quotes.length; i++) {
			if(r.nextBoolean() && !quotes[i].isDanger()) {
				list.add(quotes[i]);
			}
		}
		if(list.isEmpty()) {
			list.add(getRandomQuote());
		}
		return list;
	}
	
	public static List<Quote> getFilteredSelection(String filter) {
		List<Quote> list = new ArrayList<>();
		if(filter==null) {
			return list;
		}
		filter = filter.toUpperCase();
		for(int i = 0; i < quotes.length; i++) {
			if(quotes[i].getAuthor().toUpperCase().startsWith(filter) || filter.equals("*")) {
				list.add(quotes[i]);
			}
		}
		return list;
	}
}
