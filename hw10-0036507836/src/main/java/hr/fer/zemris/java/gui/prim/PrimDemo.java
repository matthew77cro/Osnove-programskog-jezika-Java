package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * This application is for demo purposes. Used for showcasing MVC pattern in JList components.
 * @author Matija
 *
 */
public class PrimDemo extends JFrame{
	
	private JList<Long> list1;
	private JList<Long> list2;
	private JButton button;
	
	/**
	 * Creates and initialises a prim demo app
	 */
	public PrimDemo() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGui();
		pack();
	}

	/**
	 * Initialises the gui and all of its components
	 */
	private void initGui() {
		this.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));

		PrimListModel model = new PrimListModel();
		list1 = new JList<Long>(model);
		list2 = new JList<Long>(model);
		
		JPanel panelLeft = new JPanel(new BorderLayout());
		panelLeft.add(list1);
		panelLeft.add(new JScrollPane(list1));
		
		JPanel panelRight = new JPanel(new BorderLayout());
		panelRight.add(list2);
		panelRight.add(new JScrollPane(list2));
		
		panel.add(panelLeft);
		panel.add(panelRight);
		
		JPanel bottomPanel = new JPanel(new GridLayout(1, 0));
		button = new JButton("Next");
		bottomPanel.add(button);
		
		button.addActionListener((e) -> {
			model.next();
		});
		
		this.add(panel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.PAGE_END);
	}

	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			PrimDemo frame = new PrimDemo();
			frame.setVisible(true);
		});
	}

}
