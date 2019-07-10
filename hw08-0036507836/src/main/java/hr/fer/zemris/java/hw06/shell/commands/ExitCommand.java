package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Models a command for a shell which terminates a shell.
 * @author Matija
 *
 */
public class ExitCommand implements ShellCommand{

	private static List<String> desc;

	/**
	 * Crates and initialises this command and its description.
	 */
	public ExitCommand() {
		if(desc!=null) return;
		List<String> tmp = new ArrayList<String>();
		tmp.add("Terminates the shell.");
		desc = Collections.unmodifiableList(tmp);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		return desc;
	}

}
