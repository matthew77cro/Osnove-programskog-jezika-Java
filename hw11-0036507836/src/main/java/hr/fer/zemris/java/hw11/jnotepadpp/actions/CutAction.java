package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * Action that cuts currently selected text to the clipboard
 * @author Matija
 *
 */
public class CutAction extends LocalizableAction{

	private static final long serialVersionUID = 1L;
	
	private MultipleDocumentModel documents;

	/**
	 * Creates and initialises the action
	 * @param key key for finding this action name in language files
	 * @param lp localization provider with the proper language set
	 * @param acceleratorKey accelerator key
	 * @param mnemonicKey mnemonic key
	 * @param shortDescription short description of the action
	 * @param documents documents on which this action operates
	 * @throws NullPointerException if any of the params are null
	 */
	public CutAction(String key, ILocalizationProvider lp, KeyStroke acceleratorKey, int mnemonicKey, String shortDescription, MultipleDocumentModel documents) {
		super(key, lp);
		putValue(Action.ACCELERATOR_KEY, acceleratorKey);
		putValue(Action.MNEMONIC_KEY, mnemonicKey);
		putValue(Action.SHORT_DESCRIPTION, shortDescription);
		this.documents = Objects.requireNonNull(documents);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SingleDocumentModel doc = documents.getCurrentDocument();
		if(doc==null) return;
		
		JTextArea text = doc.getTextComponent();
		Toolkit.getDefaultToolkit().getSystemClipboard()
			.setContents(new StringSelection(text.getSelectedText()), null);
		String data = text.getText();
		data = data.substring(0, text.getSelectionStart()) + data.substring(text.getSelectionEnd(), data.length());
		text.setText(data);
	}

}
