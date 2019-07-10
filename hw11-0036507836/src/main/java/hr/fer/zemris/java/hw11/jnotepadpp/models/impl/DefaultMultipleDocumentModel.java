package hr.fer.zemris.java.hw11.jnotepadpp.models.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.JNotepadPPException;
import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{
	
	private static final long serialVersionUID = 1L;
	
	private Set<Path> paths;
	private List<SingleDocumentModel> documents;
	private SingleDocumentModel currentDocument;
	private ILocalizationProvider lp;
	
	private List<MultipleDocumentListener> listeners;
	
	private ImageIcon redSave;
	private ImageIcon greenSave;

	public DefaultMultipleDocumentModel(ILocalizationProvider lp, ImageIcon redSave, ImageIcon greenSave) {
		this.lp = lp;
		this.paths = new HashSet<Path>();
		this.documents = new ArrayList<>();
		this.listeners = new ArrayList<>();
		
		this.redSave = redSave;
		this.greenSave = greenSave;
		
		this.addChangeListener((evt) -> {
			int currentTab = getSelectedIndex();
			changeCurrentDocument(currentTab==-1 ? null : documents.get(currentTab));
		});
	}
	
	private SingleDocumentModel addNewDocument(Path p, String text, String name, String tip) {
		SingleDocumentModel newDoc = new DefaultSingleDocumentModel(p, text);
		
		newDoc.addSingleDocumentListener(new SingleDocumentListener() {
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				int index = DefaultMultipleDocumentModel.this.documents.indexOf(model);
				if(index==-1) return;
				if(model.isModified()) {
					DefaultMultipleDocumentModel.this.setIconAt(index, redSave);
				} else {
					DefaultMultipleDocumentModel.this.setIconAt(index, greenSave);
				}
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				int index = DefaultMultipleDocumentModel.this.documents.indexOf(model);
				if(index==-1) return;
				DefaultMultipleDocumentModel.this.setTitleAt(index, model.getFilePath().getFileName().toString());
				DefaultMultipleDocumentModel.this.setToolTipTextAt(index, model.getFilePath().toString());
			}
		});
		
		int index = getTabCount();
		documents.add(newDoc);
		notifyDocumentAdded(newDoc);
		insertTab(name, null, new JScrollPane(newDoc.getTextComponent()), tip, index);
		setSelectedIndex(index);
		if(p==null) {
			newDoc.setModified(true);
		} else {
			newDoc.setModified(false);
		}
		
		return newDoc;
	}
	
	private void removeDocument(SingleDocumentModel oldDoc) {
		documents.remove(oldDoc);
		notifyDocumentRemoved(oldDoc);
	}

	private void changeCurrentDocument(SingleDocumentModel newDoc) {
		SingleDocumentModel oldDoc = currentDocument;
		currentDocument = newDoc;
		notifyCurrentDocumentChanged(oldDoc, newDoc);
	}
	
	private void notifyCurrentDocumentChanged(SingleDocumentModel oldDoc, SingleDocumentModel newDoc) {
		for(var listener : listeners) {
			listener.currentDocumentChanged(oldDoc, newDoc);
		}
	}
	
	private void notifyDocumentAdded(SingleDocumentModel newDoc) {
		for(var listener : listeners) {
			listener.documentAdded(newDoc);
		}
	}
	
	private void notifyDocumentRemoved(SingleDocumentModel oldDoc) {
		for(var listener : listeners) {
			listener.documentRemoved(oldDoc);
		}
	}
	
	@Override
	public SingleDocumentModel createNewDocument() {
		return addNewDocument(null, "", "(" + lp.getString("unnamed") + ")", "(" + lp.getString("unnamed") + ")");
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if(path==null) throw new NullPointerException("Path must not be null!");
		
		if(!paths.add(path)) {
			int tabIndex = -1;
			SingleDocumentModel doc = null;
			for(int i=0, stop=documents.size(); i<stop; i++) {
				SingleDocumentModel next = documents.get(i);
				if(path.equals(next.getFilePath())) {
					tabIndex = i;
					doc = next;
					break;
				}
			}
			setSelectedIndex(tabIndex);
			return doc;
		}
		
		String text;
		try {
			text = Files.readString(path);
		} catch (IOException e) {
			throw new JNotepadPPException("Error reading from " + path.toString());
		}
		
		return addNewDocument(path, text, path.getFileName().toString(), path.toString());
	}
	
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if(model==null) throw new NullPointerException("model must not be null!");
		if(newPath==null && model.getFilePath()==null)
			throw new NullPointerException("Path associated with the model and newPath must not "
					+ "both be null at the same time!");
		if(newPath!=null && !newPath.equals(model.getFilePath()) && paths.contains(newPath))
			throw new JNotepadPPException("File with that path is already opened in another tab!");
		
		if(newPath==null) newPath = model.getFilePath();
		
		try {
			Files.createFile(newPath);
		} catch (IOException e1) {
		}
		
		try (BufferedWriter writer = Files.newBufferedWriter(newPath, StandardOpenOption.WRITE)) {
			writer.write(model.getTextComponent().getText());
		} catch (IOException e) {
			throw new JNotepadPPException("Error communicating with the file system!");
		}
		model.setModified(false);
		
		if(!newPath.equals(model.getFilePath())) { //if file path changed
			paths.remove(model.getFilePath());
			model.setFilePath(newPath);
			paths.add(model.getFilePath());
			changeCurrentDocument(model);
		}
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		if(documents.size()==0) throw new JNotepadPPException("No documents opened!");
		
		int index = documents.indexOf(model);
		if(index==-1) throw new JNotepadPPException("Document is not opened!");
		removeDocument(model);
		if(model.getFilePath()!=null) paths.remove(model.getFilePath());
		removeTabAt(index);
	}
	
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		if(index<0 || index>=documents.size()) throw new IndexOutOfBoundsException();
		return documents.get(index);
	}
	
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

}
