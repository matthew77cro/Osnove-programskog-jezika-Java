package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Models a command for a shell which writes all available charsets to a user.
 * @author Matija
 *
 */
public class CharsetsCommand implements ShellCommand{
	
	private static List<String> desc;

	/**
	 * Crates and initialises this command and its description.
	 */
	public CharsetsCommand() {
		List<String> tmp = new ArrayList<String>();
		tmp.add("Command charsets takes no arguments and lists names of supported charsets for your Java platform.");
		tmp.add("A single charset name is written per line.");
		desc = Collections.unmodifiableList(tmp);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isEmpty()) {
			env.writeln("Command takes no arguments.");
		} else {
			Charset.availableCharsets().keySet().forEach(env::writeln);;
		}		
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		return desc;
	}

}
