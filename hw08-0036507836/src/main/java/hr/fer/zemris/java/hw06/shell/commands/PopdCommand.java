package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class PopdCommand implements ShellCommand{

	private static List<String> desc;

	/**
	 * Crates and initialises this command and its description.
	 */
	public PopdCommand() {
		if(desc!=null) return;
		List<String> tmp = new ArrayList<String>();
		tmp.add("Command cat takes no arguments. Changes the current (working) directory to the directory on the stack that pushd created.");
		desc = Collections.unmodifiableList(tmp);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments.length()!=0) {
			env.writeln("Command takes no argument.");
			return ShellStatus.CONTINUE;
		}
		
		Object stack = env.getSharedData("cdstack");
		if(stack == null) {
			env.writeln("Path stack does not exist. Use pushd first!");
			return ShellStatus.CONTINUE;
		}
		
		Path p;
		try {
			p = ((Stack<Path>)stack).pop();
		} catch (EmptyStackException ex) {
			env.writeln("Path stack is empty. Use pushd first!");
			return ShellStatus.CONTINUE;
		}
		
		try {
			env.setCurrentDirectory(p);
		} catch (IOException e) {
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "popd";
	}

	@Override
	public List<String> getCommandDescription() {
		return desc;
	}

}
