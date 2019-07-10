package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw17.jvdraw.actions.ActionContext;
import hr.fer.zemris.java.hw17.jvdraw.actions.ExportAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.OpenAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.SaveAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.SaveAsAction;
import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.components.JColorAreaListener;
import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * This is the main class of the JVDraw application
 * @author Matija
 *
 */
@SuppressWarnings("serial")
public class JVDraw extends JFrame {
	
	private Tool currentState;
	private Map<String, Tool> states = new HashMap<>();
	
	private JDrawingCanvas canvas;
	private JColorArea fgColor;
	private JColorArea bgColor;
	private DrawingModel model;
	
	private JToggleButton line;
	private JToggleButton circle;
	private JToggleButton filledCircle;
	private JMenu fileMenu;
	private JList<String> list;
	
	private ActionContext context;
	private Action save;
	private Action open;
	private Action saveAs;
	private Action export;
	
	/**
	 * Initializes the JFrame window
	 * @param title
	 */
	public JVDraw() {
		setTitle("JVDraw");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		model = new DrawingModelImpl();
		
		initGui();
		initListeners();
		initActions();
		initMenu();
		
		states.put("line", new LineTool(model, fgColor, canvas));
		states.put("circle", new CircleTool(model, fgColor, canvas));
		states.put("filledCircle", new FilledCircleTool(model, fgColor, bgColor, canvas));
	
		currentState = states.get("line");
		line.setSelected(true);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)(screen.getWidth()/1.5), (int)(screen.getHeight()/1.5));
		setLocationRelativeTo(null);
	}
	
	/**
	 * Initializes the gui elements
	 */
	private void initGui() {
		setLayout(new BorderLayout());
		
		//Constructing the toolbar
		JToolBar toolbar = new JToolBar();
		
		fgColor = new JColorArea(Color.BLACK);
		bgColor = new JColorArea(Color.WHITE);
		
		ButtonGroup bgroup = new ButtonGroup();
		line = new JToggleButton("Line");
		circle = new JToggleButton("Circle");
		filledCircle = new JToggleButton("Filled circle");
		bgroup.add(line);
		bgroup.add(circle);
		bgroup.add(filledCircle);
		
		toolbar.add(fgColor, BorderLayout.LINE_START);
		toolbar.add(bgColor, BorderLayout.LINE_END);
		toolbar.add(line);
		toolbar.add(circle);
		toolbar.add(filledCircle);
		
		this.add(toolbar, BorderLayout.PAGE_START);
		
		//Constructing a canvas
		canvas = new JDrawingCanvas(() -> currentState, model);
		this.add(canvas, BorderLayout.CENTER);
		
		//Constructing a list of drawn objects
		list = new JList<>();
		JScrollPane scroll = new JScrollPane(list);
		list.setModel(new DrawingObjectListModel(model));
		this.add(scroll, BorderLayout.LINE_END);
	}
	
	/**
	 * Initializes the listeners
	 */
	private void initListeners() {
		//Constructing the listener
		JColorAreaListener listener = new JColorAreaListener(fgColor, bgColor);
		this.add(listener, BorderLayout.PAGE_END);
		
		line.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JVDraw.this.currentState = states.get("line");
			}
		});
		circle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JVDraw.this.currentState = states.get("circle");
			}
		});
		filledCircle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JVDraw.this.currentState = states.get("filledCircle");
			}
		});
		
		list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        if (evt.getClickCount() == 2) {
		            int index = list.locationToIndex(evt.getPoint());
		            GeometricalObject clicked = model.getObject(index);
		            GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();
		            if(JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit " + clicked.toString(), JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						try {
							editor.checkEditing();
							editor.acceptEditing();
						} catch(Exception ex) {
							JOptionPane.showMessageDialog(JVDraw.this, "Input was invalid!", "Error", JOptionPane.ERROR_MESSAGE);
						}
		            }

		        }
		    }
		});
		
		list.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				char key = e.getKeyChar();
				int index = list.getSelectedIndex();
				if (index==-1) return;
				GeometricalObject obj = model.getObject(index);
				
				if(key=='+') {
					model.changeOrder(obj, 1);
				} else if(key=='-') {
					model.changeOrder(obj, -1);
				} else if(key==(char)127) { //DEL KEY
					model.remove(obj);
				}
			}
			
		});
	}
	
	/**
	 * Initializes the actions for menu items
	 */
	private void initActions() {
		this.context = new ActionContext();
		context.setModel(model);
		context.setFrame(this);
		this.open = new OpenAction("Open", KeyStroke.getKeyStroke("control O"), KeyEvent.VK_O, "Opens a file", context);
		this.save = new SaveAction("Save", KeyStroke.getKeyStroke("control S"), KeyEvent.VK_S, "Saves canvas to a file", context);
		this.saveAs = new SaveAsAction("Save as", KeyStroke.getKeyStroke("control I"), KeyEvent.VK_A, "Saves canvas to a file", context);
		this.export = new ExportAction("Export", KeyStroke.getKeyStroke("control E"), KeyEvent.VK_E, "Exports canvas to a file", context);
	}
	
	/**
	 * Initializes the window menubar with all menu items
	 */
	private void initMenu() {
		//Constructing the menubar
		JMenuBar menubar = new JMenuBar();
		fileMenu = new JMenu("File");
		
		JMenuItem open = new JMenuItem(this.open);
		JMenuItem save = new JMenuItem(this.save);
		JMenuItem saveAs = new JMenuItem(this.saveAs);
		JMenuItem export = new JMenuItem(this.export);
		
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(saveAs);
		fileMenu.add(export);
		
		menubar.add(fileMenu);
		
		setJMenuBar(menubar);
	}
	
	public static void main(String[] args) {
		new JVDraw().setVisible(true);
	}

}
