package hr.fer.zemris.tecaj.swing.p13;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor6 extends JFrame {

	private static final long serialVersionUID = 1L;


	public Prozor6() {
		setLocation(20, 50);
		setSize(300, 200);
		setTitle("Moj prvi prozor!");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JLabel labela = new JLabel(" ");
		labela.setHorizontalAlignment(SwingConstants.CENTER);
		labela.setOpaque(true);
		labela.setBackground(Color.YELLOW);

		JPanel bottomPanel = new JPanel(new GridLayout(1, 0));

		bottomPanel.add(new JLabel("Unesi broj: "));
		
		JTextField unosBroja = new JTextField();
		bottomPanel.add(unosBroja);
		
		JButton izracunaj = new JButton("Izračunaj");
		bottomPanel.add(izracunaj);
		
		izracunaj.addActionListener(e -> {
			obradi(labela, unosBroja.getText());
		});
		
		cp.add(labela, BorderLayout.CENTER);
		cp.add(bottomPanel, BorderLayout.PAGE_END);
	}

	private void obradi(JLabel labela, String text) {
		try {
			double broj = Double.parseDouble(text);
			labela.setText(Double.toString(broj*broj));
		} catch(NumberFormatException ex) {
			JOptionPane.showMessageDialog(
				this, 
				"Tekst "+text+" ne mogu pretvoriti u broj.", 
				"Pogreška", 
				JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Prozor6();
			frame.pack();
			frame.setVisible(true);
		});
	}
}
