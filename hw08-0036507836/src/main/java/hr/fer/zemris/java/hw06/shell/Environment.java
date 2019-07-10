package hr.fer.zemris.java.hw06.shell;

import java.io.IOException;
import java.nio.file.Path;
import java.util.SortedMap;

/**
 * 
 * Interface used for communication with the user for shell commands.
 * 
 * @author Matija
 *
 */
public interface Environment {
	
	/**
	 * Reading a line from user and returns the shell request.
	 * @return request user has entered
	 * @throws ShellIOException if communication fails
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Writing a line to a user.
	 * @return text message for user
	 * @throws ShellIOException if communication fails
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Writing a line to a user and places new line at the end.
	 * @return text message for user
	 * @throws ShellIOException if communication fails
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Returns a sorted map of available commands of this environment.
	 * @return a sorted map of available commands of this environment.
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Returns a character used as MULTILINE char
	 * @return a character used as MULTILINE char
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets a character used as MULTILINE char
	 * @param symbol character which to be used as a MULTILINE char
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Returns a character used as PROMPT char
	 * @return a character used as PROMPT char
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets a character used as PROMPT char
	 * @param symbol character which to be used as a PROMPT char
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Returns a character used as MORELINES char
	 * @return a character used as MORELINES char
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets a character used as MORELINES char
	 * @param symbol character which to be used as a MORELINES char
	 */
	void setMorelinesSymbol(Character symbol);
	
	
	Path getCurrentDirectory();
	void setCurrentDirectory(Path path) throws IOException;
	Object getSharedData(String key);
	void setSharedData(String key, Object value);

}
