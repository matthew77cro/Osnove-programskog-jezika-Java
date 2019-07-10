package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * An implementation of a builder that configures Lindenmayer systems.
 * @author Matija
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder{
	
	private Dictionary<Character, String> productions;
	private Dictionary<Character, Command> actions;
	
	private double unitLength = 0.1;
	private double unitLengthDegreeScaler = 1;
	private Vector2D origin = new Vector2D(0, 0);
	private double angle = 0;
	private String axiom = "";
	
	/**
	 * Creates and initialises a new instance of LSystemBuilder
	 */
	public LSystemBuilderImpl() {
		productions = new Dictionary<Character, String>();
		actions = new Dictionary<Character, Command>();
	}

	/**
	 * Returns a new lsystem with configured parameters.
	 * @return a new lsystem with configured parameters
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 * Configures LSystem from text. Returns this instance of LSystemBuilder.
	 * @return this
	 */
	@Override
	public LSystemBuilder configureFromText(String[] text) {
		
		for(String s : text) {
			s = s.trim();
			if(s.startsWith("origin")) {
				String arguments[] = s.substring(6).trim().split(" ");
				setOrigin(Double.parseDouble(arguments[0]), Double.parseDouble(arguments[1]));
			} else if(s.startsWith("angle")) {
				String argument = s.substring(5).trim();
				setAngle(Double.parseDouble(argument));
			} else if(s.startsWith("unitLengthDegreeScaler")) {
				String argument = s.substring(22).trim();
				double argumentDouble;
				if(argument.contains("/")) {
					String fraction[] = argument.split("/");
					argumentDouble = Double.parseDouble(fraction[0].trim()) / Double.parseDouble(fraction[1].trim());
				} else {
					argumentDouble = Double.parseDouble(argument);
				}
					
				setUnitLengthDegreeScaler(argumentDouble);
			} else if(s.startsWith("unitLength")) {
				String argument = s.substring(10).trim();
				setUnitLength(Double.parseDouble(argument));
			} else if(s.startsWith("command")) {
				String argument = s.substring(7).trim();
				char key = argument.charAt(0);
				String value = argument.substring(1).trim();
				registerCommand(key, value);
			} else if(s.startsWith("axiom")) {
				String argument = s.substring(5).trim();
				setAxiom(argument);
			} else if(s.startsWith("production")) {
				String argument = s.substring(10).trim();
				char key = argument.charAt(0);
				String value = argument.substring(1).trim();
				registerProduction(key, value);
			}
		}
		
		return this;
	}

	/**
	 * Adds a new command to the list of available commands. Returns this instance of LSystemBuilder.
	 * @return this
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		
		Command c;
		
		String actionSplit[] = action.trim().split(" ");
		
		if(actionSplit[0].equals("draw")) {
			c = new DrawCommand(Double.parseDouble(actionSplit[1]));
		} else if(actionSplit[0].equals("skip")) {
			c = new SkipCommand(Double.parseDouble(actionSplit[1]));
		} else if(actionSplit[0].equals("scale")) {
			c = new ScaleCommand(Double.parseDouble(actionSplit[1]));
		} else if(actionSplit[0].equals("rotate")) {
			c = new RotateCommand(Double.parseDouble(actionSplit[1])*Math.PI/180);
		} else if(actionSplit[0].equals("push")) {
			c = new PushCommand();
		} else if(actionSplit[0].equals("pop")) {
			c = new PopCommand();
		} else if(actionSplit[0].equals("color")) {
			
			c = new ColorCommand( new Color(Integer.parseInt(actionSplit[1].substring(0, 2), 16), 
										Integer.parseInt(actionSplit[1].substring(2, 4), 16), 
										Integer.parseInt(actionSplit[1].substring(4, 6), 16)));
			
		} else {
			throw new IllegalArgumentException();
		}
		
		actions.put(symbol, c);
		return this;
	}

	/**
	 * Adds a new production to the list of available productions. Returns this instance of LSystemBuilder.
	 * @return this
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(symbol, production);
		return this;
	}

	/**
	 * Sets an initial angle. Returns this instance of LSystemBuilder.
	 * @param angle new angle
	 * @return this
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle*Math.PI/180;
		return this;
	}

	/**
	 * Sets an initial axiom. Returns this instance of LSystemBuilder.
	 * @param axiom new axiom
	 * @return this
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * Sets the origin. Returns this instance of LSystemBuilder.
	 * @param x x coordinate of the origin
	 * @param y y coordinate of the origin
	 * @return this
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Sets an initial unit length. Returns this instance of LSystemBuilder.
	 * @param unitLength new unitLength
	 * @return this
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * Sets an initial unit length degree scaler. Returns this instance of LSystemBuilder.
	 * @param unitLengthDegreeScaler new unitLengthDegreeScaler
	 * @return this
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}
	
	public class LSystemImpl implements LSystem {

		@Override
		public void draw(int level, Painter painter){			
			Context ctx = new Context();
			Vector2D angle = new Vector2D(1, 0);
			angle.rotate(LSystemBuilderImpl.this.angle);
			TurtleState state = new TurtleState(origin, angle, Color.BLACK, unitLength * Math.pow(unitLengthDegreeScaler, level));
			ctx.pushState(state);
			
			String system = generate(level);
			char[] symbols = system.toCharArray();
			for(char c : symbols) {
				actions.get(c).execute(ctx, painter);
			}
		}

		@Override
		public String generate(int level) {
			StringBuilder sb = new StringBuilder();
			sb.append(axiom);
			
			while(level>0) {
				char[] current = sb.toString().toCharArray();
				sb = new StringBuilder();
				
				for(char c : current) {
					String production = productions.get(c);
					if(production==null) {
						sb.append(c);
					} else {
						sb.append(production);
					}
				}
				
				level--;
			}
			
			return sb.toString();
		}
		
	}

}
