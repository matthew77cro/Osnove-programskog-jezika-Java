package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class DropdCommand implements ShellCommand{

	private static List<String> desc;

	/**
	 * Crates and initialises this command and its description.
	 */
	public DropdCommand() {
		if(desc!=null) return;
		List<String> tmp = new ArrayList<String>();
		tmp.add("Command cat takes no arguments. Pops a path from the top of the stack but does not change the current directory.");
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
		if(stack == null || ((Stack<Path>)stack).size()==0) {
			env.writeln("Nema pohranjenih direktorija.");
			return ShellStatus.CONTINUE;
		}
		
		Stack<Path> s = ((Stack<Path>)stack);
		s.pop();
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "dropd";
	}

	@Override
	public List<String> getCommandDescription() {
		return desc;
	}

}
