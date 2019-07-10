package hr.fer.zemris.java.hw06.shell;

/**
 * Enum used for communication between shell and shell commands.
 * @author Matija
 *
 */
public enum ShellStatus {
	
	/**
	 * Shell should continue working.
	 */
	CONTINUE, 
	
	/**
	 * Shell should terminate.
	 */
	TERMINATE;

}
