package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.Caret;

import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * This class represents a listener of MultipleDocument object and updates
 * the status bar accordingly.
 * @author Matija
 *
 */
public class StatusBarListener implements MultipleDocumentListener{

	private JLabel length;
	private JLabel ln;
	private JLabel col;
	private JLabel sel;
	
	private ChangeListener docListen;
	private SingleDocumentModel currentDoc;
	
	/**
	 * Creates the status bar listener object
	 * @param length label in which to display document length
	 * @param ln label in which to display line number
	 * @param col label in which to display column number
	 * @param sel label in which to display length of selected text
	 * @throws NullPointerException if any of params is null
	 */
	public StatusBarListener(JLabel length, JLabel ln, JLabel col, JLabel sel) {
		this.length = Objects.requireNonNull(length);
		this.ln = Objects.requireNonNull(ln);
		this.col = Objects.requireNonNull(col);
		this.sel = Objects.requireNonNull(sel);
		this.docListen = new SingleDocListen();
	}

	@Override
	public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		this.currentDoc = currentModel;
		if(previousModel!=null) {
			previousModel.getTextComponent().getCaret().removeChangeListener(docListen);
		}
		if(currentModel!=null) {
			currentModel.getTextComponent().getCaret().addChangeListener(docListen);
		}
		docListen.stateChanged(null);
	}

	@Override
	public void documentAdded(SingleDocumentModel model) {
	}

	@Override
	public void documentRemoved(SingleDocumentModel model) {
	}
	
	/**
	 * Change listener that listens for caret position changes in the document.
	 * @author Matija
	 *
	 */
	private class SingleDocListen implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			if(currentDoc==null) {
				length.setText("");
				ln.setText("");
				col.setText("");
				sel.setText("");
			} else {
				Caret c = currentDoc.getTextComponent().getCaret();
				int dot = c.getDot();
				int mark = c.getMark();
				
				String textUntilCaret = currentDoc.getTextComponent().getText().substring(0, dot);
				int line = count(textUntilCaret, '\n') + 1;
				int column = dot - textUntilCaret.lastIndexOf('\n');
				length.setText("length : " + Integer.toString(currentDoc.getTextComponent().getText().length()));
				ln.setText("Ln : " + line);
				col.setText("Col : " + column);
				sel.setText("Sel : " + Math.abs(dot-mark));
			}
		}
		
		/**
		 * Counts how many characters c are in the string s
		 * @param s string in which to count
		 * @param c character to be counted
		 * @return number of occurrences of the character c in the string s
		 */
		private int count(String s, char c) {
			int count = 0;
			
			char[] chars = s.toCharArray();
			for(char chr : chars) {
				if(chr==c) count ++;
			}
			
			return count;
		}
	}

}
