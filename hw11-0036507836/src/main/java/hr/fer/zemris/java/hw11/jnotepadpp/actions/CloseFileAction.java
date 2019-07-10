package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.util.Objects;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * Action closes current file in the documents
 * @author Matija
 *
 */
public class CloseFileAction extends LocalizableAction{

	private static final long serialVersionUID = 1L;
	
	private ILocalizationProvider lp;
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
	public CloseFileAction(String key, ILocalizationProvider lp, KeyStroke acceleratorKey, int mnemonicKey, String shortDescription, MultipleDocumentModel documents, JFrame frame) {
		super(key, lp);
		this.lp = lp;
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
		
		if(doc.isModified()) {
			String msg = String.format(lp.getString("save_changes_q"), (doc.getFilePath()==null ? "(" + lp.getString("unnamed") + ")" : doc.getFilePath().getFileName().toString()));
			int selected = JOptionPane.showConfirmDialog(frame, msg, "Save", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			if(selected==JOptionPane.CANCEL_OPTION) return;
			if(selected==JOptionPane.YES_OPTION) {
				Path p = doc.getFilePath();
				if(doc.getFilePath()==null) {
					JFileChooser fc = new JFileChooser();
					if(fc.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
						p = fc.getSelectedFile().toPath();
					} else {
						return;
					}
				} else {
					p = doc.getFilePath();
				}
				documents.saveDocument(doc, p);
			}
		}
		
		documents.closeDocument(documents.getCurrentDocument());
	}

}
