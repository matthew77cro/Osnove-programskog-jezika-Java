package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.nio.file.Path;

import javax.swing.JFrame;

import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;

/**
 * This class represents a context in which actions are executed
 * @author Matija
 *
 */
public class ActionContext {
	
	private Path file;
	private DrawingModel model;
	private JFrame frame;
	
	/**
	 * Sets the currently opened file
	 * @param file currently opened file
	 */
	public void setFile(Path file) {
		this.file = file;
		if(file!=null)
			frame.setTitle("JVDraw - " + file.getFileName().toString());
		else
			frame.setTitle("JVDraw");
	}
	
	/**
	 * Returns the currently opened file
	 * @return the currently opened file
	 */
	public Path getFile() {
		return file;
	}
	
	/**
	 * Returns the drawing model
	 * @return the drawing model
	 */
	public DrawingModel getModel() {
		return model;
	}
	
	/**
	 * Sets the drawing model
	 * @param model the drawing model
	 */
	public void setModel(DrawingModel model) {
		this.model = model;
	}
	
	/**
	 * Returns the frame
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}
	
	/**
	 * Sets the frame
	 * @param frame the frame
	 */
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
}
