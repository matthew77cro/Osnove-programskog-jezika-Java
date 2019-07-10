package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;

/**
 * A command-line program MyShell waits for the user to enter a command.
 * @author Matija
 *
 */
public class MyShell {

	public static void main(String[] args) {
		
		System.out.println("Welcome to MyShell v 1.0");
		
		
		Scanner sc = new Scanner(System.in);
		
		Environment shell = new ShellEnv(sc);
		
		
		try {
			//build environment
			ShellStatus status = null;
			do {
				 System.out.print(shell.getPromptSymbol() + " ");
				 String l = getUserInput(shell).trim(); // readLineOrLines
				 String command[] = l.split("\\s+");
				 String commandName = command[0];
				 String arguments = l.substring(commandName.length());
				 ShellCommand shellCommand = shell.commands().get(commandName);
				 
				 if(shellCommand==null) {
					 shell.writeln("Unknown command " + commandName);
					 continue;
				 }
				 
				 status = shellCommand.executeCommand(shell, arguments);
			} while(status!=ShellStatus.TERMINATE);
		} catch(Exception ex) {
			System.out.println("Exception occured. " + ex.getClass().getName() + " -> " + ex.getMessage());
		}

		sc.close();
	}
	
	private static String getUserInput(Environment env) {
		
		StringBuilder sb = new StringBuilder();
		
		while(true) {
			String s = env.readLine().trim();
			if(!s.endsWith(env.getMorelinesSymbol().toString())) return sb.append(s + " ").toString();
			sb.append(s.substring(0, s.length()-1).trim() + " ");
			env.write(env.getMultilineSymbol().toString() + " ");
		}
		
	}
	
	private static class ShellEnv implements Environment {
		
		private char PROMPTSYMBOL = '>';
		private char MORELINESSYMBOL = '\\';
		private char MULTILINESYMBOL = '|';
		
		private Scanner sc;
		private SortedMap<String, ShellCommand> commands;
		
		public ShellEnv(Scanner sc) {
			this.sc = sc;
			
			TreeMap<String, ShellCommand> commands = new TreeMap<String, ShellCommand>();
			commands.put("cat", new CatCommand());
			commands.put("charsets", new CharsetsCommand());
			commands.put("copy", new CopyCommand());
			commands.put("exit", new ExitCommand());
			commands.put("hexdump", new HexdumpCommand());
			commands.put("ls", new LsCommand());
			commands.put("mkdir", new MkdirCommand());
			commands.put("tree", new TreeCommand());
			commands.put("symbol", new SymbolCommand());
			commands.put("help", new HelpCommand());
			this.commands = Collections.unmodifiableSortedMap(commands);
		}

		@Override
		public String readLine() throws ShellIOException {
			String s;
			
			try {
				s = sc.nextLine();
			} catch(Exception ex) {
				throw new ShellIOException();
			}
			
			return s;
		}

		@Override
		public void write(String text) throws ShellIOException {
			System.out.print(text);
		}

		@Override
		public void writeln(String text) throws ShellIOException {
			System.out.println(text);
		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			return commands;
		}

		@Override
		public Character getMultilineSymbol() {
			return MULTILINESYMBOL;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			this.MULTILINESYMBOL = symbol;
		}

		@Override
		public Character getPromptSymbol() {
			return PROMPTSYMBOL;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			this.PROMPTSYMBOL = symbol;
		}

		@Override
		public Character getMorelinesSymbol() {
			return MORELINESSYMBOL;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			this.MORELINESSYMBOL = symbol;
		}
		
	}

}
