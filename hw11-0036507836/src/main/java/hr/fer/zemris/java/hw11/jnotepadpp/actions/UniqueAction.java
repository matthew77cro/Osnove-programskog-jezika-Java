package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentModel;

/**
 * Action that removes duplicate lines from the selected lines
 * @author Matija
 *
 */
public class UniqueAction extends LocalizableAction{

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
	public UniqueAction(String key, ILocalizationProvider lp, KeyStroke acceleratorKey, int mnemonicKey, String shortDescription, MultipleDocumentModel documents) {
		super(key, lp);
		putValue(Action.ACCELERATOR_KEY, acceleratorKey);
		putValue(Action.MNEMONIC_KEY, mnemonicKey);
		putValue(Action.SHORT_DESCRIPTION, shortDescription);
		this.documents = Objects.requireNonNull(documents);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea textArea = documents.getCurrentDocument().getTextComponent();
		Document doc = textArea.getDocument();
		Caret caret = textArea.getCaret();
		
		int start = 0, len = 0;
		try {
			int line1 = textArea.getLineOfOffset(caret.getDot());
			int line2 = textArea.getLineOfOffset(caret.getMark());
			start = textArea.getLineStartOffset(line1<line2 ? line1 : line2);
			len = Math.abs(textArea.getLineEndOffset(line1>line2 ? line1 : line2) - start);
		} catch (BadLocationException ignorable) {
		}
		
		if(len==0) return;
		
		try {
			String text = doc.getText(start, len);
			text = unique(text);
			doc.remove(start, len);
			doc.insertString(start, text, null);
		} catch (BadLocationException ignorable) {
		}
	}

	/**
	 * Removes duplicate lines from the given string. Retains only
	 * the first occurrence of each line.
	 * @param text string from which to remove duplicate lines
	 * @return string with all unique lines.
	 */
	private String unique(String text) {
		String[] split = text.split("\n");
		
		Set<String> set = new HashSet<>();
		
		StringBuilder sb = new StringBuilder();
		
		for(String s : split) {
			if(set.add(s)) {
				sb.append(s + "\n");
			}
		}
		
		return sb.toString();
	}

}
