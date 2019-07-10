package hr.fer.zemris.java.hw13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used for voting in a hw13 servlets. This class is modelled as a singleton
 * @author Matija
 *
 */
public class Voting implements Iterable<Voting.VoteObject>{
	
	private static Voting instance;
	
	private List<VoteObject> results;
	private boolean loaded = false;
	
	/**
	 * Initialises a new instance of the Voting class
	 */
	private Voting() {
	}
	
	/**
	 * Returns the one and only instance to this class
	 * @return
	 */
	public static Voting getInstance() {
		if(instance==null) instance = new Voting();
		return instance;
	}
	
	/**
	 * Returns the voting results as an unmodifiable list
	 * @return the voting results as an unmodifiable list
	 * @throws IllegalStateException if results have not been loaded
	 */
	public List<VoteObject> getResults() {
		if(!loaded) throw new IllegalStateException();
		return Collections.unmodifiableList(results);
	}
	
	/**
	 * Returns the vote object at a given index
	 * @param index index of the voting object
	 * @return the vote object at a given index
	 */
	public VoteObject getVoteObject(int index) {
		if(!loaded) throw new IllegalStateException();
		if(index<0 || index>=results.size()) throw new IndexOutOfBoundsException();
		
		return results.get(index);
	}
	
	/**
	 * Returns number of voting objects
	 * @return number of voting objects
	 */
	public int getVoteObjectCount() {
		return results.size();
	}
	
	/**
	 * Loads the voting objects and their vote count
	 * @param resultsPath path to the file in which the results are stored
	 * @param definitionPath path to the file in which definitions of the voting objects are stored
	 * @throws IOException if an I/O error occurs
	 */
	public void load(Path resultsPath, Path definitionPath) throws IOException {
		if(!Files.exists(resultsPath)) {
			Files.createFile(resultsPath);
		}
		
		//map {id -> voteObject(id, name, url, voteCount = 0)}
		Map<String, VoteObject> definition = new LinkedHashMap<>();
		List<String> lines = Files.readAllLines(definitionPath);
		for(String line : lines) {
			String[] splitted = line.split("\t");
			definition.put(splitted[0], new VoteObject(Integer.parseInt(splitted[0]), 
														splitted[1], splitted[2], 0));
		}
		
		//load results
		results = new ArrayList<>();
		lines = Files.readAllLines(resultsPath);
		for(String line : lines) {
			String[] splitted = line.split("\t");
			VoteObject voteObj = definition.get(splitted[0]);
			if(voteObj==null) throw new RuntimeException("Internal servr error : There is no definition for id " + splitted[0]);
			voteObj.voteCount = Integer.parseInt(splitted[1]);
			results.add(voteObj);
			definition.remove(splitted[0]);
		}
		
		results.sort((vo1, vo2) -> Integer.compare(vo2.voteCount, vo1.voteCount));
		
		//add zero votes
		for(var mapEntry : definition.entrySet()) {
			results.add(mapEntry.getValue());
		}
		
		loaded = true;
	}
	
	@Override
	public Iterator<VoteObject> iterator() {
		return results.iterator();
	}

	/**
	 * This class represents a candidate in the voting.
	 * @author Matija
	 *
	 */
	public static class VoteObject {
		private int id;
		private String name;
		private String url;
		private int voteCount;
		
		/**
		 * Initialises the vote object
		 * @param id candidate's id
		 * @param name candidate's name
		 * @param url url of the candidate's best song
		 * @param voteCount number of votes candidate has received
		 */
		public VoteObject(int id, String name, String url, int voteCount) {
			this.id = id;
			this.name = name;
			this.url = url;
			this.voteCount = voteCount;
		}
		
		/**
		 * Returns the candidate's id
		 * @return the candidate's id
		 */
		public int getId() {
			return id;
		}
		
		/**
		 * Returns the candidate's name
		 * @return the candidate's name
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Returns the candidate's url
		 * @return the candidate's url
		 */
		public String getUrl() {
			return url;
		}
		
		/**
		 * Returns the candidate's vote count
		 * @return the candidate's vote count
		 */
		public int getVoteCount() {
			return voteCount;
		}
		
	}

}
