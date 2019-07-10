package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Models a command for a shell which writes files in the directory and subdirectories to a user.
 * @author Matija
 *
 */
public class LsCommand implements ShellCommand{

	private static List<String> desc;
	private boolean exceptionHappened;

	/**
	 * Crates and initialises this command and its description.
	 */
	public LsCommand() {
		List<String> tmp = new ArrayList<String>();
		tmp.add("Command ls takes a single argument – directory – and writes a directory listing.");
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
		
		Path root;
		try {
			root = Paths.get(args[0]);
		} catch (InvalidPathException e) {
			env.writeln("Error reading file");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.exists(root) || !Files.isDirectory(root)) {
			env.writeln("Argument must be a directory and it must exist.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			ls(env, root);
		} catch (IOException e) {
			exceptionHappened = true;
		}
		
		if(exceptionHappened) {
			env.writeln("Error reading file system.");
			exceptionHappened = false;
		}
		
		return ShellStatus.CONTINUE;
	}

	private void ls(Environment env, Path root) throws IOException {
		Files.walk(root, 1).forEach((p) -> {
			try {
				if(Files.isSameFile(root, p)) return;
			
				StringBuilder sb = new StringBuilder();
				
				sb.append(Files.isDirectory(p) ? "d" : "-");
				sb.append(Files.isReadable(p) ? "r" : "-");
				sb.append(Files.isWritable(p) ? "w" : "-");
				sb.append(Files.isExecutable(p) ? "x" : "-");
				sb.append(" ");
				
				String size = Long.toString(Files.size(p));
				sb.append(" ".repeat(10-size.length()) + size);
				sb.append(" ");
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				BasicFileAttributeView faView = Files.getFileAttributeView(
														p, BasicFileAttributeView.class, 
														LinkOption.NOFOLLOW_LINKS
												);
				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				sb.append(formattedDateTime);
				sb.append(" ");
				
				sb.append(p.getFileName().toString());
				
				env.writeln(sb.toString());
			} catch (IOException e) {
				exceptionHappened = true;
				return;
			}
		});
	}
	
	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		return desc;
	}

}
