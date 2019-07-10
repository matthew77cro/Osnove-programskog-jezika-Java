package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * This interface models an abstract command for creating Lindenmayer systems of fractals.
 * @author Matija
 *
 */
public interface Command {
	
	/**
	 * Executes given command.
	 * @param ctx context in which to execute the command
	 * @param painter painter on which to draw if command interacts whith the painter
	 */
	void execute(Context ctx, Painter painter);

}
