package hr.fer.zemris.java.hw17.trazilica.console.commands;

import java.util.List;

import hr.fer.zemris.java.hw17.trazilica.console.CommandResult;
import hr.fer.zemris.java.hw17.trazilica.console.ConsoleCommand;
import hr.fer.zemris.java.hw17.trazilica.console.ConsoleContext;
import hr.fer.zemris.java.hw17.trazilica.console.ConsoleContext.ConsoleResult;
import hr.fer.zemris.java.hw17.trazilica.model.TfIdfDocumentCollection;

/**
 * Command that prints latest results again to the screen.
 * @author Matija
 *
 */
public class ResultsCommand implements ConsoleCommand {

	@Override
	public CommandResult execute(String parameters, TfIdfDocumentCollection documents, ConsoleContext context) {

		List<ConsoleResult> results = context.getResults();
		if(results==null) {
			System.out.printf("No previous results found!%n%n");
			return CommandResult.CONTINUE;
		}
		
		for(ConsoleResult res : results) {
			System.out.printf("[%2d] (%.4f) %s%n", res.getId(), res.getSimilarityIndex(), res.getPath());
		}
		System.out.println();
		
		return CommandResult.CONTINUE;
	}

}
