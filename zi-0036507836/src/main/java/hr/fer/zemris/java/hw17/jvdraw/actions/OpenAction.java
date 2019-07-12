package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.FilledTriangle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.Line;

/**
 * Action for opening the file and loading it into the context
 * @author Matija
 *
 */
@SuppressWarnings("serial")
public class OpenAction extends AbstractAction{

	private ActionContext context;
	
	/**
	 * Initializes the open action
	 * @param name name of the action
	 * @param acceleratorKey accelerator key for the action
	 * @param mnemonicKey mnemonic key for the action
	 * @param shortDescription short description of the action
	 * @param context context in which the action needs to be executed
	 */
	public OpenAction(String name, KeyStroke acceleratorKey, int mnemonicKey, String shortDescription, ActionContext context) {
		super(name);
		putValue(Action.ACCELERATOR_KEY, acceleratorKey);
		putValue(Action.MNEMONIC_KEY, mnemonicKey);
		putValue(Action.SHORT_DESCRIPTION, shortDescription);
		this.context = Objects.requireNonNull(context);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		if(jfc.showOpenDialog(context.getFrame()) != JFileChooser.APPROVE_OPTION) return;
		Path p = jfc.getSelectedFile().toPath();
		if(!Files.isRegularFile(p) || !Files.isReadable(p) || !p.getFileName().toString().endsWith(".jvd")) {
			JOptionPane.showMessageDialog(context.getFrame(), "Error! Please choose correct type of file!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		List<String> lines;
		try {
			lines = Files.readAllLines(p);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(context.getFrame(), e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		context.getModel().clear();
		
		try {
			for(String s : lines) {
				String[] splitted = s.trim().split("\\s+");
				if(splitted[0].equals("LINE")) {
					Color c = new Color(Integer.parseInt(splitted[5]), 
										Integer.parseInt(splitted[6]),
										Integer.parseInt(splitted[7]));
					context.getModel().add(new Line(Integer.parseInt(splitted[1]), 
							Integer.parseInt(splitted[3]), 
							Integer.parseInt(splitted[2]), 
							Integer.parseInt(splitted[4]), c));
				} else if(splitted[0].equals("CIRCLE")) {
					Color c = new Color(Integer.parseInt(splitted[4]), 
										Integer.parseInt(splitted[5]),
										Integer.parseInt(splitted[6]));
					context.getModel().add(new Circle(
							Integer.parseInt(splitted[1]),
							Integer.parseInt(splitted[2]),
							Integer.parseInt(splitted[3]),
							c));
				} else if(splitted[0].equals("FCIRCLE")) {
					Color c1 = new Color(Integer.parseInt(splitted[4]), 
							Integer.parseInt(splitted[5]),
							Integer.parseInt(splitted[6]));
					Color c2 = new Color(Integer.parseInt(splitted[7]), 
							Integer.parseInt(splitted[8]),
							Integer.parseInt(splitted[9]));
					context.getModel().add(new FilledCircle(
							Integer.parseInt(splitted[1]),
							Integer.parseInt(splitted[2]),
							Integer.parseInt(splitted[3]),
							c1, c2));
				} else if(splitted[0].equals("FTRIANGLE")) {
					Color c1 = new Color(Integer.parseInt(splitted[7]), 
							Integer.parseInt(splitted[8]),
							Integer.parseInt(splitted[9]));
					Color c2 = new Color(Integer.parseInt(splitted[10]), 
							Integer.parseInt(splitted[11]),
							Integer.parseInt(splitted[12]));
					context.getModel().add(new FilledTriangle(
							Integer.parseInt(splitted[1]),
							Integer.parseInt(splitted[2]),
							Integer.parseInt(splitted[3]),
							Integer.parseInt(splitted[4]),
							Integer.parseInt(splitted[5]),
							Integer.parseInt(splitted[6]),
							c1, c2));
				} else {
					JOptionPane.showMessageDialog(context.getFrame(), "File error", "Error", JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(context.getFrame(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		context.setFile(p);
	}
	
}
