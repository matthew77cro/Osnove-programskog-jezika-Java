package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Models a command for a shell which copies a file to another location with the same or a different name.
 * @author Matija
 *
 */
public class CopyCommand implements ShellCommand{

	private static List<String> desc;

	/**
	 * Crates and initialises this command and its description.
	 */
	public CopyCommand() {
		List<String> tmp = new ArrayList<String>();
		tmp.add("The copy command expects two arguments: source file name and destination file name.");
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
		
		if(args.length!=2) {
			env.writeln("Command takes two arguments.");
			return ShellStatus.CONTINUE;
		}
		
		Path from, to;
		try {
			from = Paths.get(args[0]);
			to = Paths.get(args[1]);
		} catch (InvalidPathException e) {
			env.writeln("Error reading file.");
			return ShellStatus.CONTINUE;
		}
		
		if(Files.isDirectory(from)) {
			env.writeln("Cannot copy directories.");
			return ShellStatus.CONTINUE;
		}
		
		if(Files.isDirectory(to)) {
			to = Paths.get(to.toString() + "/" + from.getFileName().toString());
		}
		
		if(Files.exists(to)) {
			env.writeln("File " + args[1] + " already exists. Do you want to overwrite it? Y/N");
			String s = env.readLine();
			if(s.equalsIgnoreCase("y")) {
				try {
					Files.delete(to);
					Files.createFile(to);
				} catch (IOException e) {
					env.writeln("Error reading file.");
					return ShellStatus.CONTINUE;
				}
			} else {
				return ShellStatus.CONTINUE;
			}
		}
		 
		BufferedInputStream in;
		BufferedOutputStream out;
		try {
			in = new BufferedInputStream(Files.newInputStream(from));
			out = new BufferedOutputStream(Files.newOutputStream(to));
			byte[] b = new byte[4096];
			
			while(true) {
				int r = in.read(b);
				if(r<1) break;
				out.write(b, 0, r);
			}
			
			in.close();
			out.close();
		} catch (IOException e) {
			env.writeln("Error reading file.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		return desc;
	}

}
