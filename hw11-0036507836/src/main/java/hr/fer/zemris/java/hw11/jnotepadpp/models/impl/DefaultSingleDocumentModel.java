package hr.fer.zemris.java.hw11.jnotepadpp.models.impl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

public class DefaultSingleDocumentModel implements SingleDocumentModel{

	private Path path;
	private String text;
	private JTextArea textbox;
	private List<SingleDocumentListener> listeners;
	
	private boolean modified;
	
	public DefaultSingleDocumentModel(Path path, String text) {
		this.path = path;
		this.text = Objects.requireNonNull(text);
		this.textbox = new JTextArea(this.text);
		this.listeners = new ArrayList<>();
		this.modified = false;
		
		this.textbox.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				modification();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				modification();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
			
			private void modification() {
				DefaultSingleDocumentModel.this.text = textbox.getText();
				DefaultSingleDocumentModel.this.modified = true;
				notifyDocumentModified();
			}
		});
	}

	@Override
	public JTextArea getTextComponent() {
		return textbox;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		this.path = Objects.requireNonNull(path);
		notifyDocumentPathChanged();
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		notifyDocumentModified();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}
	
	private void notifyDocumentModified() {
		for(var listener : listeners) {
			listener.documentModifyStatusUpdated(this);
		}
	}
	
	private void notifyDocumentPathChanged() {
		for(var listener : listeners) {
			listener.documentFilePathUpdated(this);
		}
	}
	
}
