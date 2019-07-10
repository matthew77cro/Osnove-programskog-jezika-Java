package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * This interface models an abstract shell command.
 * @author Matija
 *
 */
public interface ShellCommand {

	/**
	 * To execute this command, call this function.
	 * @param env environment in which to execute this command
	 * @param arguments arguments form the user for this command
	 * @return shell status (continue or terminate)
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Returns a command name
	 * @return a command name
	 */
	String getCommandName();
	
	/**
	 * Returns a command description as an unmodifiable list
	 * @return a command description as an unmodifiable list
	 */
	List<String> getCommandDescription();
	
}
