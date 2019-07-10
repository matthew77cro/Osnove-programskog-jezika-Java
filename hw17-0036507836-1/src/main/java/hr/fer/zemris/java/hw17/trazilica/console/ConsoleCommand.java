package hr.fer.zemris.java.hw17.trazilica.console;

import hr.fer.zemris.java.hw17.trazilica.model.TfIdfDocumentCollection;

/**
 * Models a console command that can be executed with given parameters in a given context.
 * @author Matija
 *
 */
public interface ConsoleCommand {
	
	/**
	 * Executes the command.
	 * @param parameters command parameters
	 * @param documents collection of documents that console works with
	 * @param context context in which this command is executed
	 * @return a command result : CommandResult.CONTINUE if console needs to continue
	 * working or CommandResult.EXIT if console needs to be exited
	 */
	public CommandResult execute(String parameters, TfIdfDocumentCollection documents, ConsoleContext context);

}
