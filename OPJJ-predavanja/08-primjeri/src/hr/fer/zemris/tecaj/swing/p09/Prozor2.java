package hr.fer.zemris.tecaj.swing.p09;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor2 extends JFrame {

	private static final long serialVersionUID = 1L;

	public Prozor2() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prozor2");
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(null);

		JLabel labela = new JLabel("Ovo je tekst!");
		labela.setBounds(10, 10, 100, 30);
		
		JButton button = new JButton("Stisni me još jednom!");
		Dimension dim = button.getPreferredSize();
		button.setBounds(10, 50, dim.width, dim.height);
		
		cp.add(labela);
		cp.add(button);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Prozor2 prozor = new Prozor2();
				prozor.setVisible(true);
			}
		});
	}
}
