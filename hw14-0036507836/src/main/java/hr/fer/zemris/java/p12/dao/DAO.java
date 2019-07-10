package hr.fer.zemris.java.p12.dao;

import java.util.List;

/**
 * Interface toward Data Subsystem.
 * 
 * @author Matija
 *
 */
public interface DAO {
	
	/**
	 * Returns all available polls. If there are no polls, list will be empty.
	 * @return all available polls if any poll exists, empty list otherwise.
	 * @throws RuntimeException if any exception occurs
	 */
	List<Poll> getPolls();
	
	/**
	 * Returns poll with the given id. If there is no such poll, method return null.
	 * @param pollID id of the poll
	 * @return poll with the given id if such exists, null otherwise
	 * @throws RuntimeException if any exception occurs
	 */
	Poll getPoll(int pollID);
	
	/**
	 * Returns the list of poll options of the specific poll with the given poll id.
	 * If there are no poll options with the given pollID, list will be empty.
	 * @param pollID id of the poll whose options needs to be returned
	 * @return list of poll options if such exist, empty list otherwise
	 * @throws RuntimeException if any exception occurs
	 */
	List<PollOption> getPollOptions(int pollID);
	
	/**
	 * Increments poll option's votesCount by 1.
	 * @param optionID id of the option whose votesCount needs to be incremented by 1
	 * @throws RuntimeException if id is invalid (there are no options with that id) or any other exception occurs
	 */
	void voteFor(int optionID);
	
	/**
	 * Returns the id of the poll to which the poll option with the given optionId belongs.
	 * @param optionId id of the option whose pollID needs to be returned
	 * @return the id of the poll to which the poll option with the given optionId belongs.
	 * @throws RuntimeException if id is invalid (there are no options with that id) or any other exception occurs
	 */
	int getPollIdFromOptionId(int optionId);
	
	/**
	 * Class that represents a poll.
	 * @author Matija
	 *
	 */
	public static class Poll {
		
		private int id;
		private String title;
		private String message;
		
		/**
		 * Initialises the poll with it's id, title and display message.
		 * @param id id of the poll
		 * @param title title of the poll
		 * @param message display massage of the poll
		 */
		public Poll(int id, String title, String message) {
			this.id = id;
			this.title = title;
			this.message = message;
		}
		
		/**
		 * Returns the id of the poll
		 * @return the id of the poll
		 */
		public int getId() {
			return id;
		}
		
		/**
		 * Returns the title of the poll
		 * @return the title of the poll
		 */
		public String getTitle() {
			return title;
		}
		
		/**
		 * Returns the display message of the poll
		 * @return the display message of the poll
		 */
		public String getMessage() {
			return message;
		}
		
	}
	
	/**
	 * Class that represents a poll option which the user can vote for.
	 * @author Matija
	 *
	 */
	public static class PollOption {
		
		private int id;
		private String title;
		private String link;
		private int votesCount;
		
		/**
		 * Initialises the poll option with it's id, title, url link and votesCount.
		 * @param id id of the poll option
		 * @param title title of the poll option
		 * @param link url link
		 * @param votesCount number of votes this poll option has
		 */
		public PollOption(int id, String title, String link, int votesCount) {
			this.id = id;
			this.title = title;
			this.link = link;
			this.votesCount = votesCount;
		}
		
		/**
		 * Returns the id of the poll option
		 * @return the id of the poll option
		 */
		public int getId() {
			return id;
		}
		
		/**
		 * Returns the title of the poll option
		 * @return the title of the poll option
		 */
		public String getTitle() {
			return title;
		}
		
		/**
		 * Returns the url link of the poll option
		 * @return the url link of the poll option
		 */
		public String getLink() {
			return link;
		}
		
		/**
		 * Returns the number of votes of the poll option
		 * @return the number of votes of the poll option
		 */
		public int getVotesCount() {
			return votesCount;
		}
		
	}
	
}