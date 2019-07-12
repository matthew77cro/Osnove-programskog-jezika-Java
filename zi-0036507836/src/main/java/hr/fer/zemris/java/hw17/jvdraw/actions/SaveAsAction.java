package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

/**
 * Action used for saving-as currently opened context into a jvd file.
 * @author Matija
 *
 */
@SuppressWarnings("serial")
public class SaveAsAction extends AbstractAction {

	private ActionContext context;
	
	/**
	 * Initializes the save-as action
	 * @param name name of the action
	 * @param acceleratorKey accelerator key for the action
	 * @param mnemonicKey mnemonic key for the action
	 * @param shortDescription short description of the action
	 * @param context context in which the action needs to be executed
	 */
	public SaveAsAction(String name, KeyStroke acceleratorKey, int mnemonicKey, String shortDescription, ActionContext context) {
		super(name);
		putValue(Action.ACCELERATOR_KEY, acceleratorKey);
		putValue(Action.MNEMONIC_KEY, mnemonicKey);
		putValue(Action.SHORT_DESCRIPTION, shortDescription);
		this.context = Objects.requireNonNull(context);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		if(jfc.showSaveDialog(context.getFrame()) != JFileChooser.APPROVE_OPTION) return;
		Path p = jfc.getSelectedFile().toPath();
		if(!p.toString().endsWith(".jvd")) p = Paths.get(p.toString() + ".jvd");
		
		Util.saveFile(context, p);
	}

}
