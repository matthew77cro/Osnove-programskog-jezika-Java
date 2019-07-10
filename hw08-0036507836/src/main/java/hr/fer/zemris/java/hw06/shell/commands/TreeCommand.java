package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Models a command for a shell which writes a directory tree to a user.
 * @author Matija
 *
 */
public class TreeCommand implements ShellCommand{

	private static List<String> desc;

	/**
	 * Crates and initialises this command and its description.
	 */
	public TreeCommand() {
		if(desc!=null) return;
		List<String> tmp = new ArrayList<String>();
		tmp.add("The tree command expects a single argument: directory name and prints a tree.");
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
		
		Path p;
		try {
			p = env.getCurrentDirectory().resolve(Paths.get(args[0]));
		} catch (InvalidPathException e) {
			env.writeln("Error reading file");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.exists(p)) {
			env.writeln("Directory does not exist!");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.walkFileTree(Paths.get(args[0]), new Visitor());
		} catch (Exception e) {
			env.writeln("Error reading file system.");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		return desc;
	}
	
	private static class Visitor implements FileVisitor<Path> {
		
		private int level;

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			System.out.print("  ".repeat(level));
			System.out.println(dir.getFileName());
			level++;
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			System.out.print("  ".repeat(level));
			System.out.println(file.getFileName());
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}
		
	}

}
