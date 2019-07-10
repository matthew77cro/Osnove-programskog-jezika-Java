package hr.fer.zemris.java.hw17.trazilica.console;

import java.nio.file.Path;
import java.util.List;

/**
 * Models a context of the console in which commands need to be executed.
 * @author Matija
 *
 */
public interface ConsoleContext {
	
	/**
	 * Returns the latest results as a list of ConsoleResult
	 * @return the latest results as a list of ConsoleResult
	 */
	public List<ConsoleResult> getResults();
	
	/**
	 * Returns a console result at a specific id
	 * @param id id of the result
	 * @return console result at a given id
	 */
	public ConsoleResult getResult(int id);
	
	/**
	 * Stores the newly calculated results in the context.
	 * @param results results that need to be stored in the context.
	 */
	public void storeResults(List<ConsoleResult> results);

	/**
	 * This class models a result of console query command
	 * @author Matija
	 *
	 */
	public static class ConsoleResult {
		
		private int id;
		private double similarityIndex;
		private Path path;
		
		/**
		 * Initializes the result with the specific id, similarityIndex and the
		 * path to the document which this result represents.
		 * @param id id of the result
		 * @param similarityIndex similarityIndex between the document which this result represents and the query
		 * @param path path to the document which this result represents
		 */
		public ConsoleResult(int id, double similarityIndex, Path path) {
			this.id = id;
			this.similarityIndex = similarityIndex;
			this.path = path;
		}

		/**
		 * Returns the id of the result
		 * @return the id of the result
		 */
		public int getId() {
			return id;
		}

		/**
		 * Returns similarityIndex between the document which this result represents and the query
		 * @return similarityIndex between the document which this result represents and the query
		 */
		public double getSimilarityIndex() {
			return similarityIndex;
		}

		/**
		 * Returns path to the document which this result represents
		 * @return path to the document which this result represents
		 */
		public Path getPath() {
			return path;
		}
		
	}
	
}
