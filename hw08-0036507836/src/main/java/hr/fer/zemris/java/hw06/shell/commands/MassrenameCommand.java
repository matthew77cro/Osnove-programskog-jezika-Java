package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw08.shell.parser.FilterResult;
import hr.fer.zemris.java.hw08.shell.parser.NameBuilder;
import hr.fer.zemris.java.hw08.shell.parser.NameBuilderParser;

/**
 * Models a command for a shell which copies a file to another location with the same or a different name.
 * @author Matija
 *
 */
public class MassrenameCommand implements ShellCommand{

	private static List<String> desc;

	/**
	 * Crates and initialises this command and its description.
	 */
	public MassrenameCommand() {
		if(desc!=null) return;
		List<String> tmp = new ArrayList<String>();
		tmp.add("");//TODO
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
		
		if(args.length<4 || args.length>5) {
			env.writeln("Invalid number of arguments! (4 or 5 expected)");
			return ShellStatus.CONTINUE;
		}
		
		if((args[2].equals("filter") || args[2].equals("groups")) && args.length!=4
				|| (args[2].equals("show") || args[2].equals("execute")) && args.length!=5
				|| (!args[2].equals("filter") && !args[2].equals("groups") && !args[2].equals("show") && !args[2].equals("execute"))) {
			env.writeln("Invalid arguments. Subcomands: filter, groups, show, execute.");
			env.writeln("With filter or groups subcommands, massrename takes 4 arguments!");
			env.writeln("With show or execute subcommands, massrename takes 5 arguments!");
			return ShellStatus.CONTINUE;
		}
		
		Path from, to;
		try {
			from = env.getCurrentDirectory().resolve(Paths.get(args[0]));
			to = env.getCurrentDirectory().resolve(Paths.get(args[1]));
		} catch (InvalidPathException e) {
			env.writeln("Error reading filesystem.");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.isDirectory(from) || !Files.isDirectory(to)) {
			env.writeln("First and second arguments must be directories!");
			return ShellStatus.CONTINUE;
		}
		
		List<FilterResult> filter = null;
		try {
			filter = Util.filter(from, args[3]);
		} catch (IOException e) {
			env.write("DIR1 is not a directory!");
		}
		
		if(args[2].equals("filter")) {
			filter.forEach(System.out::println);
		} else if(args[2].equals("groups")) {
			for(var result : filter) {
				env.write(result.toString());
				for(int i=0, end=result.numberOfGroups(); i<end; i++) {
					env.write(" " + i + ": " + result.group(i));
				}
				env.writeln("");
			}
		} else if(args[2].equals("show")) {
			NameBuilder nb = new NameBuilderParser(args[4]).getNameBuilder();
			for(var result : filter) {
				StringBuilder sb = new StringBuilder();
				nb.execute(result, sb);
				env.writeln(result.toString() + " => " + sb.toString());
			}
		} else { //execute
			NameBuilder nb = new NameBuilderParser(args[4]).getNameBuilder();
			for(var result : filter) {
				StringBuilder sb = new StringBuilder();
				nb.execute(result, sb);
				String newName = sb.toString();
				try {
					Files.move(Paths.get(from.toString(), result.toString()), 
							Paths.get(to.toString(), newName), 
							StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "massrename";
	}

	@Override
	public List<String> getCommandDescription() {
		return desc;
	}

}
