package hr.fer.zemris.java.hw17.trazilica.console.commands;

import hr.fer.zemris.java.hw17.trazilica.console.CommandResult;
import hr.fer.zemris.java.hw17.trazilica.console.ConsoleCommand;
import hr.fer.zemris.java.hw17.trazilica.console.ConsoleContext;
import hr.fer.zemris.java.hw17.trazilica.model.TfIdfDocumentCollection;

/**
 * Command that exits the console (returns exit result).
 * @author Matija
 *
 */
public class ExitCommand implements ConsoleCommand {

	@Override
	public CommandResult execute(String parameters, TfIdfDocumentCollection documents, ConsoleContext context) {
		return CommandResult.EXIT;
	}

}
