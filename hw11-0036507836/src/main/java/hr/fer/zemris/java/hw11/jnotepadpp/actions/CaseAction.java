package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentModel;

/**
 * Action for inverting the case of selected text from the document
 * @author Matija
 *
 */
public class CaseAction extends LocalizableAction{

	private static final long serialVersionUID = 1L;
	
	public static final int UPPER_CASE = 0;
	public static final int LOWER_CASE = 1;
	public static final int INVERT = 2;
	
	private MultipleDocumentModel documents;
	private int action;

	/**
	 * Creates and initialises the action
	 * @param key key for finding this action name in language files
	 * @param lp localization provider with the proper language set
	 * @param acceleratorKey accelerator key
	 * @param mnemonicKey mnemonic key
	 * @param shortDescription short description of the action
	 * @param documents documents on which this action operates
	 * @param action could be CaseAction.UPPER_CASE or CaseAction.LOWER_CASE or CaseAction.INVERT
	 * @throws NullPointerException if any of the params are null
	 */
	public CaseAction(String key, ILocalizationProvider lp, KeyStroke acceleratorKey, int mnemonicKey, String shortDescription, MultipleDocumentModel documents, int action) {
		super(key, lp);
		this.action = action;
		putValue(Action.ACCELERATOR_KEY, acceleratorKey);
		putValue(Action.MNEMONIC_KEY, mnemonicKey);
		putValue(Action.SHORT_DESCRIPTION, shortDescription);
		this.documents = Objects.requireNonNull(documents);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Document doc = documents.getCurrentDocument().getTextComponent().getDocument();
		
		Caret caret = documents.getCurrentDocument().getTextComponent().getCaret();
		int start = Math.min(caret.getMark(), caret.getDot());
		int len = Math.abs(caret.getMark()-caret.getDot());
		
		if(len==0) return;
		
		try {
			String text = doc.getText(start, len);
			text = toggleText(text);
			doc.remove(start, len);
			doc.insertString(start, text, null);
		} catch (BadLocationException ignorable) {
		}
	}

	/**
	 * Toggles case of the text given by the parameter.
	 * @param text text whose case that needs to be toggled
	 * @return toggled text
	 */
	private String toggleText(String text) {
		char[] strchr = text.toCharArray();
		if(action==UPPER_CASE) {
			for(int i=0; i < strchr.length; i++) {
				char c = strchr[i];
				strchr[i] = Character.toUpperCase(c);
			}
		} else if(action==LOWER_CASE) {
			for(int i=0; i < strchr.length; i++) {
				char c = strchr[i];
				strchr[i] = Character.toLowerCase(c);
			}
		} else if(action==INVERT) {
			for(int i=0; i < strchr.length; i++) {
				char c = strchr[i];
				if(Character.isUpperCase(c)) {
					strchr[i] = Character.toLowerCase(c);
				} else if(Character.isLowerCase(c)) {
					strchr[i] = Character.toUpperCase(c);
				}
			}
		}
		return new String(strchr);
	}

}
