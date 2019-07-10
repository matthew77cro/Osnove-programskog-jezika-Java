package hr.fer.zemris.java.hw17.trazilica.console.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;

import hr.fer.zemris.java.hw17.trazilica.console.CommandResult;
import hr.fer.zemris.java.hw17.trazilica.console.ConsoleCommand;
import hr.fer.zemris.java.hw17.trazilica.console.ConsoleContext;
import hr.fer.zemris.java.hw17.trazilica.console.ConsoleContext.ConsoleResult;
import hr.fer.zemris.java.hw17.trazilica.model.TfIdfDocumentCollection;

/**
 * Command that prints the content of the document which is represented by an id in a ConsoleResult.
 * @author Matija
 *
 */
public class TypeCommand implements ConsoleCommand {

	@Override
	public CommandResult execute(String parameters, TfIdfDocumentCollection documents, ConsoleContext context) {
		
		int id = 0;
		try {
			id = Integer.parseInt(parameters.trim());
		} catch (NumberFormatException ex) {
			System.out.printf("Unknown result id.%n%n");
			return CommandResult.CONTINUE;
		}
		
		ConsoleResult result = context.getResult(id);
		if(result==null) {
			System.out.printf("Unknown result id.%n%n");
			return CommandResult.CONTINUE;
		}
		
		System.out.println("----------------------------------------------------------------");
		System.out.println("Document: " + result.getPath());
		System.out.println("----------------------------------------------------------------");
		
		try {
			BufferedReader reader = Files.newBufferedReader(result.getPath());
			
			String line = reader.readLine();
			while(line!=null) {
				System.out.println(line);
				line = reader.readLine();
			}
			
			reader.close();
		} catch (IOException e) {
			System.out.printf("An IO error occured!%n%n");
			return CommandResult.CONTINUE;
		}
		
		System.out.println("----------------------------------------------------------------");
		System.out.println();
		
		return CommandResult.CONTINUE;
	}

}
