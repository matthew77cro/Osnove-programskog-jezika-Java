package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.CaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ChangeLangAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CopyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CutAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.NewFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.PasteAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveFileAsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SortAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.StatisticsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.UniqueAction;
import hr.fer.zemris.java.hw11.jnotepadpp.listeners.StatusBarListener;
import hr.fer.zemris.java.hw11.jnotepadpp.listeners.TitleChanger;
import hr.fer.zemris.java.hw11.jnotepadpp.listeners.WindowClosingListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.impl.DefaultMultipleDocumentModel;

/**
 * Notepad application with some non-standard features.
 * @author Matija
 *
 */
public class JNotepadPP extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private MultipleDocumentModel documents;
	
	private Action newFile;
	private Action openFile;
	private Action saveFile;
	private Action saveFileAs;
	private Action closeFile;
	private Action exit;
	private Action copy;
	private Action cut;
	private Action paste;
	private Action statistics;
	private Action toUpperCase;
	private Action toLowerCase;
	private Action invertCase;
	private Action asc;
	private Action desc;
	private Action unique;
	
	private ImageIcon redSave;
	private ImageIcon greenSave;
	
	JPanel center;
	
	private JLabel length;
	private JLabel ln;
	private JLabel col;
	private JLabel sel;
	private JLabel clock;
	private Timer t;
	
	private FormLocalizationProvider flp;

	/**
	 * Creates and initialises Notepad application
	 */
	public JNotepadPP() {
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setTitle("JNotepad++");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.setSize((int)(0.9*screenSize.getWidth()), (int)(0.9*screenSize.getHeight()));
		this.setLocationRelativeTo(null);
		
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		
		loadIcons();
		this.documents = new DefaultMultipleDocumentModel(flp, redSave, greenSave);
		
		initActions();
		initGui();
		initListeners();
		initClock();
	}
	
	/**
	 * Loads icons for png files
	 */
	private void loadIcons() {
		InputStream redSave = this.getClass().getResourceAsStream("icons/red_save.png");
		InputStream greenSave = this.getClass().getResourceAsStream("icons/green_save.png");
		if(redSave==null || greenSave==null) {
			System.err.println("Error loading icons!");
			System.exit(-1);
		}
		
		try {
			byte[] redSaveb = redSave.readAllBytes();
			byte[] greenSaveb = greenSave.readAllBytes();
			redSave.close();
			greenSave.close();
			this.redSave = new ImageIcon(redSaveb);
			this.greenSave = new ImageIcon(greenSaveb);
		} catch (IOException e) {
			System.err.println("Error loading icons! " + e.getMessage());
			System.exit(-1);
		}
	}
	
	/**
	 * Initialises action for buttons, menu items
	 */
	private void initActions() {
		this.newFile = new NewFileAction("new", flp, KeyStroke.getKeyStroke("control N"), KeyEvent.VK_N, "Creates a new file", documents);
		this.openFile = new OpenFileAction("open_file", flp, KeyStroke.getKeyStroke("control O"), KeyEvent.VK_O, "Opens selected file in a new tab", documents, this);
		this.saveFile = new SaveFileAction("save", flp, KeyStroke.getKeyStroke("control S"), KeyEvent.VK_S, "Saves opened file to disk", documents, this);
		this.saveFileAs = new SaveFileAsAction("save_as", flp, KeyStroke.getKeyStroke("control shift S"), KeyEvent.VK_A, "Saves opened file to disk", documents, this);
		this.closeFile = new CloseFileAction("close", flp, KeyStroke.getKeyStroke("control W"), KeyEvent.VK_C, "Close currently opened file", documents, this);
		this.exit = new ExitAction("exit", flp, KeyStroke.getKeyStroke("control Q"), KeyEvent.VK_E, "Exit the application", documents, this);
	
		this.copy = new CopyAction("copy", flp, KeyStroke.getKeyStroke("control C"), KeyEvent.VK_C, "Copy currently selected text", documents);
		this.cut = new CutAction("cut", flp, KeyStroke.getKeyStroke("control X"), KeyEvent.VK_U, "Cut currently selected text", documents);
		this.paste = new PasteAction("paste", flp, KeyStroke.getKeyStroke("control V"), KeyEvent.VK_P, "Paste from clipboard", documents);
		
		this.statistics = new StatisticsAction("statistical_info", flp, KeyStroke.getKeyStroke("control I"), KeyEvent.VK_I, "Display statistical information about the document", documents, this);
		
		this.toUpperCase = new CaseAction("to_uppercase", flp, KeyStroke.getKeyStroke("control U"), KeyEvent.VK_U, "Changes case of selected text to uppercase.", documents, CaseAction.UPPER_CASE);
		this.toLowerCase = new CaseAction("to_lowercase", flp, KeyStroke.getKeyStroke("control L"), KeyEvent.VK_L, "Changes case of selected text to lowercase.", documents, CaseAction.LOWER_CASE);
		this.invertCase = new CaseAction("invert_case", flp, KeyStroke.getKeyStroke("control G"), KeyEvent.VK_I, "Invert case of selected text.", documents, CaseAction.INVERT);
	
		this.asc = new SortAction("ascending", flp, KeyStroke.getKeyStroke("control alt A"), KeyEvent.VK_A, "Sorts selected lines in ascending order.", documents, true);
		this.desc = new SortAction("descending", flp, KeyStroke.getKeyStroke("control alt D"), KeyEvent.VK_D, "Sorts selected lines in descending order.", documents, false);
		this.unique = new UniqueAction("unique", flp, KeyStroke.getKeyStroke("control alt U"), KeyEvent.VK_D, "Removes duplicate lines from selected part.", documents);
	}

	/**
	 * Initialises gui elements and adds the to the frame
	 */
	private void initGui() {
		this.setLayout(new BorderLayout());
		
		initMenuBar();
		
		center = new JPanel(new BorderLayout());
		center.add((JTabbedPane)documents, BorderLayout.CENTER);
		
		initToolBar();
		
		initStatusBar();
		this.add(center, BorderLayout.CENTER);
	}

	/**
	 * Initialises the status bar
	 */
	private void initStatusBar() {
		JPanel statusBar = new JPanel();
		statusBar.setLayout(new GridLayout(1, 3));
		
		length = new JLabel();
		ln = new JLabel();
		col = new JLabel();
		sel = new JLabel();
		clock = new JLabel("", SwingConstants.RIGHT);
		
		JPanel middle = new JPanel();
		middle.setBorder(BorderFactory.createEmptyBorder());
		middle.add(ln);
		middle.add(col);
		middle.add(sel);
		
		statusBar.add(length);
		statusBar.add(middle);
		statusBar.add(clock);
		
		center.add(statusBar, BorderLayout.PAGE_END);
	}

	/**
	 * Initialises the tool bar
	 */
	private void initToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.add(new JButton(newFile));
		toolBar.add(new JButton(openFile));
		toolBar.add(new JButton(saveFile));
		toolBar.add(new JButton(saveFileAs));
		
		toolBar.addSeparator();
		toolBar.add(new JButton(copy));
		toolBar.add(new JButton(cut));
		toolBar.add(new JButton(paste));
		
		toolBar.addSeparator();
		toolBar.add(new JButton(statistics));
		
		toolBar.addSeparator();
		toolBar.add(new JButton(closeFile));
		
		toolBar.addSeparator();
		toolBar.add(new JButton(exit));
		
		this.add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Initialises the menu bar
	 */
	private void initMenuBar() {
		JMenuBar jmb = new JMenuBar();
		
		JMenu file = new LJMenu("file", flp);
		JMenu edit = new LJMenu("edit", flp);
		JMenu help = new LJMenu("help", flp);
		JMenu langs = new LJMenu("languages", flp);
		JMenu tools = new LJMenu("tools", flp);
		
		file.add(new JMenuItem(newFile));
		file.add(new JMenuItem(openFile));
		file.add(new JMenuItem(saveFile));
		file.add(new JMenuItem(saveFileAs));
		file.addSeparator();
		file.add(new JMenuItem(closeFile));
		file.addSeparator();
		file.add(new JMenuItem(exit));
		
		edit.add(new JMenuItem(copy));
		edit.add(new JMenuItem(cut));
		edit.add(new JMenuItem(paste));
		
		help.add(new JMenuItem(statistics));
		
		langs.add(new JMenuItem(new ChangeLangAction("en", "English", "Changes current GUI language to english.")));
		langs.add(new JMenuItem(new ChangeLangAction("hr", "Hrvatski", "Changes current GUI language to hrvatski.")));
		langs.add(new JMenuItem(new ChangeLangAction("de", "Deutsch", "Changes current GUI language to deutsch.")));
		
		JMenu changeCase = new LJMenu("change_case" , flp);
		JMenu sort = new LJMenu("sort" , flp);
		changeCase.add(new JMenuItem(toUpperCase));
		changeCase.add(new JMenuItem(toLowerCase));
		changeCase.add(new JMenuItem(invertCase));
		sort.add(new JMenuItem(asc));
		sort.add(new JMenuItem(desc));
		tools.add(changeCase);
		tools.add(sort);
		tools.add(unique);
		
		jmb.add(file);
		jmb.add(edit);
		jmb.add(help);
		jmb.add(langs);
		jmb.add(tools);
		
		this.setJMenuBar(jmb);
	}
	
	/**
	 * Initialises all listeners needed for this application
	 */
	private void initListeners() {
		this.addWindowListener(new WindowClosingListener(exit));
		documents.addMultipleDocumentListener(new TitleChanger(this));
		documents.addMultipleDocumentListener(new StatusBarListener(length, ln, col, sel));
	}

	/**
	 * Initialises the clock displayed on the bottom right
	 */
	private void initClock() {
		t = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH) + 1;
				int day = c.get(Calendar.DAY_OF_MONTH);
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int minute = c.get(Calendar.MINUTE);
				int second = c.get(Calendar.SECOND);
				
				clock.setText(year + "/" + 
							  "0".repeat(month<10 ? 1 : 0) + month + "/" +
							  "0".repeat(day<10 ? 1 : 0) + day + " " +
							  "0".repeat(hour<10 ? 1 : 0) + hour + ":" +
							  "0".repeat(minute<10 ? 1 : 0) + minute + ":" +
							  "0".repeat(second<10 ? 1 : 0) + second);
			}
		});
		t.start();
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				t.stop();
			}
		});
	}

	public static void main(String[] args) {
		
		LocalizationProvider.getInstance().setLanguage("en");;
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
		
	}
	
}
