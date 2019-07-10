package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Models a command for a shell which creates a new directory with all parent directories needed.
 * @author Matija
 *
 */
public class MkdirCommand implements ShellCommand {

	private static List<String> desc;

	/**
	 * Crates and initialises this command and its description.
	 */
	public MkdirCommand() {
		List<String> tmp = new ArrayList<String>();
		tmp.add("The mkdir command takes a single argument: directory name, and creates the appropriate directory structure.");
		desc = Collections.unmodifiableList(tmp);
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] args;
		
		try {
			args = Util.splitArgs(arguments);
		} catch(IllegalArgumentException ex) {
			env.writeln("String is not closed!");
			return ShellStatus.CONTINUE;
		}
		
		if(arguments.length()==0 || args.length!=1) {
			env.writeln("Command takes one arguments.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.createDirectories(Paths.get(args[0]));
		} catch (IOException | InvalidPathException e) {
			env.writeln("Error communicating with the file system.");
		}
		
		return ShellStatus.CONTINUE;
	}
	
	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		return desc;
	}

}
