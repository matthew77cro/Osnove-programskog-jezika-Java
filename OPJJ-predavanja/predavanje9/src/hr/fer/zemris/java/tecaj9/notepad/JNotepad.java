package hr.fer.zemris.java.tecaj9.notepad;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

public class JNotepad extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JTextArea editor;
	private Path openedFilePath;
	
	public JNotepad() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		setLocation(10, 10);
		setSize(500, 500);
		initGui();
		
		setLocationRelativeTo(null);
	}
	
	private void initGui() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		editor = new JTextArea();
		cp.add(new JScrollPane(editor), BorderLayout.CENTER);
		
		configureActions();
		createMenus();
		createToolbar();
	}

	private void configureActions() {
		openDocument.putValue(Action.NAME, "Open");
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocument.putValue(Action.SHORT_DESCRIPTION, "Open file from disk");
		
		saveDocument.putValue(Action.NAME, "Save");
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, "Save file to disk");
		
		deleteSelectedPart.putValue(Action.NAME, "Delete selection");
		deleteSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));
		deleteSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		deleteSelectedPart.putValue(Action.SHORT_DESCRIPTION, "Delection selected part from document if selection exists");
		deleteSelectedPart.setEnabled(false);
		
		toggleSelectedPart.putValue(Action.NAME, "Toggle caseing in selection");
		toggleSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F3"));
		toggleSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		toggleSelectedPart.putValue(Action.SHORT_DESCRIPTION, "Toggles character caseing in selected part if selection exist");
		toggleSelectedPart.setEnabled(false);
		
		exitApplication.putValue(Action.NAME, "Exit");
		exitApplication.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitApplication.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitApplication.putValue(Action.SHORT_DESCRIPTION, "Terminates application");
		
		editor.getCaret().addChangeListener((changeEvt) -> {
			boolean selectionExists = editor.getCaret().getDot() != editor.getCaret().getMark();
			deleteSelectedPart.setEnabled(selectionExists);
			toggleSelectedPart.setEnabled(selectionExists);
		});
	}

	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		
		JMenu file = new JMenu("File");
		file.add(new JMenuItem(openDocument));
		file.add(new JMenuItem(saveDocument));
		file.addSeparator();
		file.add(new JMenuItem(exitApplication));
		mb.add(file);
		
		JMenu edit = new JMenu("Edit");
		edit.add(new JMenuItem(toggleSelectedPart));
		edit.add(new JMenuItem(deleteSelectedPart));
		mb.add(edit);
		
		setJMenuBar(mb);
	}

	private void createToolbar() {
		JToolBar tb = new JToolBar();
		
		tb.add(new JButton(openDocument));
		tb.add(new JButton(saveDocument));
		tb.add(new JButton(toggleSelectedPart));
		
		getContentPane().add(tb, BorderLayout.PAGE_START);
	}
	
	private final Action openDocument = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("open file");
			if(jfc.showOpenDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			openedFilePath = jfc.getSelectedFile().toPath();
			
			if(!Files.isReadable(openedFilePath)) {
				JOptionPane.showMessageDialog(JNotepad.this, 
						"File is not readable", 
						"Error", 
						JOptionPane.ERROR_MESSAGE
				);
				return;
			}
			
			String text = null;
			try {
				text = Files.readString(openedFilePath);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JNotepad.this, 
						"Error while reading file", 
						"Error", 
						JOptionPane.ERROR_MESSAGE
				);
				return;
			}
			
			editor.setText(text);
//			try {
//				Document doc = editor.getDocument();
//				doc.remove(0, doc.getLength());
//				doc.insertString(0, text, null);
//			} catch (BadLocationException ignorable) {
//			}
			
		}
	};
	
	private final Action saveDocument = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(openedFilePath==null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("save file");
				if(jfc.showSaveDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JNotepad.this, 
							"Nothing saved", 
							"Information", 
							JOptionPane.INFORMATION_MESSAGE
					);
					return;
				}
				openedFilePath = jfc.getSelectedFile().toPath();
			}
			
			try {
				Files.writeString(openedFilePath, editor.getText());
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JNotepad.this, 
						"Document saved", 
						"Information",
						JOptionPane.INFORMATION_MESSAGE
				);
				return;
			}
			
			JOptionPane.showMessageDialog(JNotepad.this, 
					"Error while saving file", 
					"Error", 
					JOptionPane.INFORMATION_MESSAGE
			);
		}
	};
	
	private final Action deleteSelectedPart = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			Caret caret = editor.getCaret();
			try {
				doc.remove(Math.min(caret.getMark(), caret.getDot()), 
						Math.abs(caret.getMark()-caret.getDot()));
			} catch (BadLocationException ignorable) {
			}
		}
	};
	
	private final Action toggleSelectedPart = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			
			Caret caret = editor.getCaret();
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

		private String toggleText(String text) {
			char[] strchr = text.toCharArray();
			for(int i=0; i < strchr.length; i++) {
				char c = strchr[i];
				if(Character.isUpperCase(c)) {
					strchr[i] = Character.toLowerCase(c);
				} else if(Character.isLowerCase(c)) {
					strchr[i] = Character.toUpperCase(c);
				}
			}
			return new String(strchr);
		}
	};
	
	private final Action exitApplication = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	};

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepad().setVisible(true);
		});
	}

}
