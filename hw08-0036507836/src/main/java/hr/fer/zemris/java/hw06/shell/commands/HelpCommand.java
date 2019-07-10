package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Models a command for a shell which is used for listing all available commands or the description of a command.
 * @author Matija
 *
 */
public class HelpCommand implements ShellCommand{

	private static List<String> desc;

	/**
	 * Crates and initialises this command and its description.
	 */
	public HelpCommand() {
		if(desc!=null) return;
		List<String> tmp = new ArrayList<String>();
		tmp.add("Used for displaying or changing shell symbols (PROMPT, MORELINES or MULTILINE symbol).");
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
		
		if(args.length!=0 && args.length!=1) {
			env.writeln("Command takes zero or one argument.");
			return ShellStatus.CONTINUE;
		}
		
		if(args.length==0) {
			env.commands().forEach((s,c) -> env.writeln(s));
		} else {
			ShellCommand c = env.commands().get(args[0]);
			
			if(c==null) {
				env.writeln("Command " + args[0] + " does not exist.");
			} else {
				env.writeln(c.getCommandName());
				c.getCommandDescription().forEach((s) -> env.writeln(s));
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		return desc;
	}

}
