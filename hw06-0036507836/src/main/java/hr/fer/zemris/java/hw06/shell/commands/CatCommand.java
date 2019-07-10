package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Models a command for a shell which writes a file content to user.
 * @author Matija
 *
 */
public class CatCommand implements ShellCommand{

	private static List<String> desc;

	/**
	 * Crates and initialises this command and its description.
	 */
	public CatCommand() {
		List<String> tmp = new ArrayList<String>();
		tmp.add("Command cat takes one or two arguments. The first argument is path to some file and is mandatory.");
		tmp.add("The second argument is charset name that should be used to interpret chars from bytes. If not provided,");
		tmp.add("a default platform charset will be used. This command opens given file and writes its content to console.");
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
		
		if(arguments.length()==0 || args.length<1 || args.length>2) {
			env.writeln("Command takes one or two arguments.");
			return ShellStatus.CONTINUE;
		}
		
		Charset charset;
		
		try {
			charset = args.length==2 ? Charset.forName(args[1]) : StandardCharsets.UTF_8;
		} catch(IllegalCharsetNameException ex) {
			env.writeln("Charset " + args[1] + " is not available.");
			return ShellStatus.CONTINUE;
		}
		 
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(
										new BufferedInputStream(
												Files.newInputStream(Paths.get(args[0]), 
																	 StandardOpenOption.READ)),
										charset));
			
			for(String line=in.readLine(); line!=null; line=in.readLine()) {
				env.writeln(line);
			}
			
			in.close();
		} catch (IOException | InvalidPathException e) {
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
