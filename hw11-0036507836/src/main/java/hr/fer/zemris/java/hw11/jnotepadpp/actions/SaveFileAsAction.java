package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.util.Objects;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * Action that saves the currently opened document to the disk as a new file
 * @author Matija
 *
 */
public class SaveFileAsAction extends LocalizableAction{

	private static final long serialVersionUID = 1L;
	
	private MultipleDocumentModel documents;
	private JFrame frame;

	/**
	 * Creates and initialises the action
	 * @param key key for finding this action name in language files
	 * @param lp localization provider with the proper language set
	 * @param acceleratorKey accelerator key
	 * @param mnemonicKey mnemonic key
	 * @param shortDescription short description of the action
	 * @param documents documents on which this action operates
	 * @param frame frame in which documents are displayed
	 * @throws NullPointerException if any of the params are null
	 */
	public SaveFileAsAction(String key, ILocalizationProvider lp, KeyStroke acceleratorKey, int mnemonicKey, String shortDescription, MultipleDocumentModel documents, JFrame frame) {
		super(key, lp);
		putValue(Action.ACCELERATOR_KEY, acceleratorKey);
		putValue(Action.MNEMONIC_KEY, mnemonicKey);
		putValue(Action.SHORT_DESCRIPTION, shortDescription);
		this.documents = Objects.requireNonNull(documents);
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SingleDocumentModel doc = documents.getCurrentDocument();
		if(doc==null) return;
		
		Path p;
		JFileChooser fc = new JFileChooser();
		if(fc.showSaveDialog(frame)==JFileChooser.APPROVE_OPTION) {
			p = fc.getSelectedFile().toPath();
		} else {
			return;
		}
		
		documents.saveDocument(doc, p);
	}

}
