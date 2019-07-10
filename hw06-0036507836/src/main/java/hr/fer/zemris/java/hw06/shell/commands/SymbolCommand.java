package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Models a command for a shell for interacting with shell properties (listing them or changing them): <br>
 * which prompt symbol is used<br>
 * which symbol for morelines is used<br>
 * or which symbol for multiline command is used.
 * @author Matija
 *
 */
public class SymbolCommand implements ShellCommand{

	private static List<String> desc;

	/**
	 * Crates and initialises this command and its description.
	 */
	public SymbolCommand() {
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
		
		if(args.length!=1 && args.length!=2) {
			env.writeln("Command takes one or two arguments.");
			return ShellStatus.CONTINUE;
		}
		
		if(args.length==1) {
			if(args[0].equals("PROMPT")) {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			} else if(args[0].equals("MORELINES")) {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			} else if(args[0].equals("MULTILINE")) {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
			} else {
				env.writeln("Unknown property " + args[0]);
			}
		} else if(args.length==2) {
			
			if(args[1].length()!=1) {
				env.writeln("New symbol should only be 1 character long.");
				return ShellStatus.CONTINUE;
			}
			
			if(args[0].equals("PROMPT")) {
				env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '" + args[1] + "'");
				env.setPromptSymbol(args[1].charAt(0));
			} else if(args[0].equals("MORELINES")) {
				env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "' to '" + args[1] + "'");
				env.setMorelinesSymbol(args[1].charAt(0));
			} else if(args[0].equals("MULTILINE")) {
				env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "' to '" + args[1] + "'");
				env.setMultilineSymbol(args[1].charAt(0));
			} else {
				env.writeln("Unknown property " + args[0]);
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		return desc;
	}

}
