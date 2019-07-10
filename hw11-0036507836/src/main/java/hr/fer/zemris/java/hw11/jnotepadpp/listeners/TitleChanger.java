package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import java.util.Objects;

import javax.swing.JFrame;

import hr.fer.zemris.java.hw11.jnotepadpp.models.SimpleMultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * Class that changes the title of the frame according to which document is opened
 * @author Matija
 *
 */
public class TitleChanger extends SimpleMultipleDocumentListener{

	private JFrame frame;
	
	/**
	 * Creates the object
	 * @param frame frame for which title needs to be changes
	 * @throws NullPointerException if frame is null
	 */
	public TitleChanger(JFrame frame) {
		this.frame = Objects.requireNonNull(frame);
	}

	@Override
	public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		if(currentModel==null) {
			frame.setTitle("JNotepad++");
		} else {
			frame.setTitle((currentModel.getFilePath() == null ? "(unnamed)" : currentModel.getFilePath().toString()) + " - JNotepad++");
		}
	}
	
}
