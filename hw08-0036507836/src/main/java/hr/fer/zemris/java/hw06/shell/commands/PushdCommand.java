package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class PushdCommand implements ShellCommand{

	private static List<String> desc;

	/**
	 * Crates and initialises this command and its description.
	 */
	public PushdCommand() {
		if(desc!=null) return;
		List<String> tmp = new ArrayList<String>();
		tmp.add("Command cat takes one argument. Changes the current (working) directory to the directory provided with the argument of the command. Also, stores the current direcotry to the stack for popd retrieval.");
		desc = Collections.unmodifiableList(tmp);
	}

	@SuppressWarnings("unchecked")
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
		
		Path currentDir = env.getCurrentDirectory();
		try {
			env.setCurrentDirectory(Paths.get(args[0]));
		} catch (IOException e) {
			env.writeln("Argument is not a valid path.");
			return ShellStatus.CONTINUE;
		}
		
		Object stack = env.getSharedData("cdstack");
		if(stack == null) {
			Stack<Path> s = new Stack<Path>();
			s.add(currentDir);
			env.setSharedData("cdstack", s);
		} else {
			((Stack<Path>)stack).add(currentDir);
		}
		
		return null;
	}

	@Override
	public String getCommandName() {
		return "pushd";
	}

	@Override
	public List<String> getCommandDescription() {
		return desc;
	}

}
