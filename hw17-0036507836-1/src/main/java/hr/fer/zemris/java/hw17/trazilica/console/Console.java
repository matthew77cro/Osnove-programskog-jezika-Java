package hr.fer.zemris.java.hw17.trazilica.console;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import hr.fer.zemris.java.hw17.trazilica.console.commands.*;
import hr.fer.zemris.java.hw17.trazilica.model.TfIdfDocumentCollection;

/**
 * This class models a console to the user and acts as a user command line interface 
 * for trazilica application.
 * @author Matija
 *
 */
public class Console {
	
	private TfIdfDocumentCollection documents;
	private Scanner sc;
	private Map<String, ConsoleCommand> commands;

	/**
	 * Initializes the console with document collection and a scanner.
	 * @param documents collection of documents
	 * @param sc scanner from which to get user input
	 */
	public Console(TfIdfDocumentCollection documents, Scanner sc) {
		this.documents = Objects.requireNonNull(documents);
		this.sc = Objects.requireNonNull(sc);
		this.commands = new HashMap<>();
		init();
	}
	
	/**
	 * Initializes the console commands.
	 */
	private void init() {
		System.out.printf("Veličina riječnika je %d riječi.%n%n", documents.getVocabulary().size());
		commands.put("query", new QueryCommand());
		commands.put("type", new TypeCommand());
		commands.put("results", new ResultsCommand());
		commands.put("exit", new ExitCommand());
	}

	/**
	 * Starts the console. This is a blocking call. Method returns only when console
	 * is shut down due to an unexpected error or when user types 'exit'.
	 */
	public void start() {
		
		CommandResult res = CommandResult.CONTINUE;
		ConsoleContext context = new Context();
		
		while(res == CommandResult.CONTINUE) {
			System.out.print("Enter command > ");
			String input = sc.nextLine().trim();
			if(input.isEmpty()) continue;
			String[] splitted = input.split("\\s+");
			ConsoleCommand command = commands.get(splitted[0]);
			if(command==null) {
				System.out.printf("Nepoznata naredba.%n%n");
				continue;
			}
			res = command.execute(input.substring(splitted[0].length()).trim(), documents, context);
		}
		
	}
	
	/**
	 * This class models a console context in which commands are executed.
	 * @author Matija
	 *
	 */
	private static class Context implements ConsoleContext {
		
		private List<ConsoleResult> results;

		@Override
		public List<ConsoleResult> getResults() {
			return results;
		}

		@Override
		public ConsoleResult getResult(int id) {
			if(results==null) return null;
			for(ConsoleResult res : results) {
				if(res.getId() == id) return res;
			}
			return null;
		}

		@Override
		public void storeResults(List<ConsoleResult> results) {
			this.results = Objects.requireNonNull(results);
		}
		
	}

}
