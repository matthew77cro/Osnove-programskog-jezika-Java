package hr.fer.zemris.tecaj.swing.p12;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor5 extends JFrame {

	private static final long serialVersionUID = 1L;

	public Prozor5() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prozor1");
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
		
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JLabel labela = new JLabel();
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 0));
		
		cp.add(labela, BorderLayout.CENTER);
		cp.add(buttonsPanel, BorderLayout.PAGE_END);

		ActionListener action = a -> {
			JButton b = (JButton)a.getSource();
			labela.setText(b.getText());
		};
		
		JButton[] buttons = new JButton[4];
		for(int i = 0; i < 4; i++) {
			buttons[i] = new JButton("Gumb "+(i+1));
			buttons[i].addActionListener(action);
			buttonsPanel.add(buttons[i]);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Prozor5 prozor = new Prozor5();
				prozor.setVisible(true);
			}
		});
	}
}
