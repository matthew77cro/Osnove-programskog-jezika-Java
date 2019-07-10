package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

public class SkipCommand implements Command{

	private double step;
	
	public SkipCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D position = state.getPosition();
		Vector2D direction = state.getDirection();
		Vector2D newPosition = position.translated(direction.scaled(step*state.getStep()));
		
		state.setPosition(newPosition);
	}

}
