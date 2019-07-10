package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

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
 * Action that sorts currently selected rows
 * @author Matija
 *
 */
public class SortAction extends LocalizableAction{

	private static final long serialVersionUID = 1L;
	
	private MultipleDocumentModel documents;
	private ILocalizationProvider lp;
	private boolean ascending;

	/**
	 * Creates and initialises the action
	 * @param key key for finding this action name in language files
	 * @param lp localization provider with the proper language set
	 * @param acceleratorKey accelerator key
	 * @param mnemonicKey mnemonic key
	 * @param shortDescription short description of the action
	 * @param documents documents on which this action operates
	 * @param ascending if true, lines will be sorted in ascending order, in descending otherwise
	 * @throws NullPointerException if any of the params are null
	 */
	public SortAction(String key, ILocalizationProvider lp, KeyStroke acceleratorKey, int mnemonicKey, String shortDescription, MultipleDocumentModel documents, boolean ascending) {
		super(key, lp);
		this.lp = lp;
		this.ascending = ascending;
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
		boolean newLineAtTheEnd = false;
		try {
			int line1 = textArea.getLineOfOffset(caret.getDot());
			int line2 = textArea.getLineOfOffset(caret.getMark());
			start = textArea.getLineStartOffset(line1<line2 ? line1 : line2);
			len = Math.abs(textArea.getLineEndOffset(line1>line2 ? line1 : line2) - start);
			if(textArea.getText().charAt(start+len-1)=='\n') newLineAtTheEnd = true;
		} catch (BadLocationException ignorable) {
		}
		
		if(len==0) return;
		
		try {
			String text = doc.getText(start, len);
			text = sortAsc(text, newLineAtTheEnd);
			doc.remove(start, len);
			doc.insertString(start, text, null);
		} catch (BadLocationException ignorable) {
		}
	}

	private String sortAsc(String text, boolean newLineAtTheEnd) {
		String[] split = text.split("\n");
		
		Locale loc = new Locale(lp.getCurrentLanguage());
		Collator coll = Collator.getInstance(loc);
		
		if(ascending) {
			Arrays.sort(split, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return coll.compare(o1, o2);
				}
			});
		} else {
			Arrays.sort(split, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return coll.compare(o2, o1);
				}
			});
		}
		
		StringBuilder sb = new StringBuilder();
		
		boolean first = true;
		for(String s : split) {
			if(first) {
				first = false;
			} else {
				sb.append("\n");
			}
			sb.append(s);
		}
		
		if(newLineAtTheEnd) sb.append("\n");
		
		return sb.toString();
	}

}
