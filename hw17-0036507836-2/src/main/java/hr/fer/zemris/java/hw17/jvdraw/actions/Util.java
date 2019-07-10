package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.Line;

/**
 * This class represents a utility class used for action executing.
 * @author Matija
 *
 */
public class Util {
	
	/**
	 * saves the currently opened context to the path p
	 * @param context context that needs to be saved
	 * @param p path to the file in which the context needs to be saved
	 */
	protected static void saveFile(ActionContext context, Path p) {
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<context.getModel().getSize(); i++) {
			var obj = context.getModel().getObject(i);
			
			if(obj instanceof Line) {
				Line l = (Line) obj;
				sb.append(String.format("LINE %d %d %d %d %d %d %d%n", l.getX1(), l.getY1(), 
						l.getX2(), l.getY2(), l.getColor().getRed(), l.getColor().getGreen(),
						l.getColor().getBlue()));
			} else if(obj instanceof Circle) {
				Circle l = (Circle) obj;
				sb.append(String.format("CIRCLE %d %d %d %d %d %d%n", l.getX(), l.getY(), 
						l.getR(), l.getColor().getRed(), l.getColor().getGreen(),
						l.getColor().getBlue()));
			} else if(obj instanceof FilledCircle) {
				FilledCircle l = (FilledCircle) obj;
				sb.append(String.format("FCIRCLE %d %d %d %d %d %d %d %d %d%n",
						l.getX(), l.getY(), l.getR(), l.getOuterColor().getRed(),
						l.getOuterColor().getGreen(), l.getOuterColor().getBlue(),
						l.getFillColor().getRed(), l.getFillColor().getGreen(),
						l.getFillColor().getBlue()));
			}
		}
		
		try {
			Files.writeString(p, sb.toString(), StandardOpenOption.CREATE);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(context.getFrame(), e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		context.setFile(p);
	}

}
