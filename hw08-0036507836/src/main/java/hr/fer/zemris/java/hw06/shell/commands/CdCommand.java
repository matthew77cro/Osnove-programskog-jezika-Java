package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CdCommand implements ShellCommand{

	private static List<String> desc;

	/**
	 * Crates and initialises this command and its description.
	 */
	public CdCommand() {
		if(desc!=null) return;
		List<String> tmp = new ArrayList<String>();
		tmp.add("Command cat takes one argument. Changes the current (working) directory to the directory provided with the argument of the command.");
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
			env.writeln("Command takes one argument.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			env.setCurrentDirectory(Paths.get(args[0]));
		} catch (IOException e) {
			env.writeln("Illegal directory path!");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return "cd";
	}

	@Override
	public List<String> getCommandDescription() {
		return desc;
	}

}
