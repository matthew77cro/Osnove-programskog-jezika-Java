package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.impl.GeometricalObjectPainter;

/**
 * Action used for exporting currently opened context into jpg, png or gif file.
 * @author Matija
 *
 */
@SuppressWarnings("serial")
public class ExportAction extends AbstractAction {

	private ActionContext context;
	
	/**
	 * Initializes the export action
	 * @param name name of the action
	 * @param acceleratorKey accelerator key for the action
	 * @param mnemonicKey mnemonic key for the action
	 * @param shortDescription short description of the action
	 * @param context context in which the action needs to be executed
	 */
	public ExportAction(String name, KeyStroke acceleratorKey, int mnemonicKey, String shortDescription, ActionContext context) {
		super(name);
		putValue(Action.ACCELERATOR_KEY, acceleratorKey);
		putValue(Action.MNEMONIC_KEY, mnemonicKey);
		putValue(Action.SHORT_DESCRIPTION, shortDescription);
		this.context = Objects.requireNonNull(context);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String type = null; //JPG, PNG, GIF
		JFileChooser jfc = new JFileChooser();
		if(jfc.showSaveDialog(context.getFrame()) != JFileChooser.APPROVE_OPTION) return;
		Path p = jfc.getSelectedFile().toPath();
		String fileName = p.getFileName().toString();
		if(fileName.endsWith(".png")) {
			type = "png";
		} else if(fileName.endsWith(".jpg")) {
			type = "jpg";
		} else if(fileName.endsWith(".gif")) {
			type = "gif";
		} else {
			JOptionPane.showMessageDialog(context.getFrame(), "Error! Please choose correct type of file! (With extension .jpg, .png or .gif)", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//Calculating the bounding box
		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
		for(int i=0, stop=context.getModel().getSize(); i<stop; i++)
			context.getModel().getObject(i).accept(bbcalc);
		Rectangle box = bbcalc.getBoundingBox();
		
		//Drawing the image
		BufferedImage image = new BufferedImage(
		 box.width, box.height, BufferedImage.TYPE_3BYTE_BGR
		);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, box.width, box.height);
		g.translate(-box.x, -box.y);
		GeometricalObjectVisitor painter = new GeometricalObjectPainter(g);
		for(int i=0, stop=context.getModel().getSize(); i<stop; i++)
			context.getModel().getObject(i).accept(painter);
		g.dispose();
		
		//Writing to the file
		try {
			if(Files.exists(p)) {
				Files.delete(p);
				Files.createFile(p);
			}
			File file = p.toFile();
			ImageIO.write(image, type, file);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(context.getFrame(), "An IO error occured! " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(context.getFrame(), "The image has been successfully exported!", "Success!", JOptionPane.INFORMATION_MESSAGE);
	}

}
