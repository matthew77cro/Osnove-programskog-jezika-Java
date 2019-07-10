package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentModel;

/**
 * Action that displays statistics of currently opened document
 * @author Matija
 *
 */
public class StatisticsAction extends LocalizableAction{

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
	public StatisticsAction(String key, ILocalizationProvider lp, KeyStroke acceleratorKey, int mnemonicKey, String shortDescription, MultipleDocumentModel documents, JFrame frame) {
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
		if(documents.getCurrentDocument()==null) {
			JOptionPane.showMessageDialog(frame, lp.getString("stat_error"), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String text = documents.getCurrentDocument().getTextComponent().getText();
		long charCount = text.length();
		long charCountNoWhitespace = 0;
		long lineCount = 1;
		
		char[] strchr = text.toCharArray();
		for(char c : strchr) {
			if(c != ' ' && c != '\n' && c != '\t') charCountNoWhitespace++;
			if(c == '\n') lineCount++;
		}
		
		String msg =  String.format(lp.getString("stats"),
				charCount, charCountNoWhitespace, lineCount);
		
		JOptionPane.showMessageDialog(frame, msg, "Statistical information", JOptionPane.INFORMATION_MESSAGE);
	}

}
