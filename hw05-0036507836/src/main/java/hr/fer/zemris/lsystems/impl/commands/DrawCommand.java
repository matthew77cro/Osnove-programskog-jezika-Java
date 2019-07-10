package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

public class DrawCommand implements Command{

	private double step;
	
	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D position = state.getPosition();
		Vector2D direction = state.getDirection();
		Vector2D newPosition = position.translated(direction.scaled(step*state.getStep()));
		
		painter.drawLine(position.getX(), position.getY(), 
				newPosition.getX(), newPosition.getY(), state.getColor(), 1f);
		
		state.setPosition(newPosition);
	}

}
