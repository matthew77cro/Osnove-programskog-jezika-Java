package hr.fer.zemris.tecaj.swing.p05;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor1 extends JFrame {

	private static final long serialVersionUID = 1L;

	public Prozor1() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prozor1");
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
		
	}
	
	private void initGUI() {
		getContentPane().setLayout(null);
		
		JComponent komponenta1 = new Elipticna();
		komponenta1.setLocation(10, 10);
		komponenta1.setSize(100, 40);
		komponenta1.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		komponenta1.setOpaque(true);
		komponenta1.setBackground(Color.YELLOW);
		
		getContentPane().add(komponenta1);
	}

	static class Elipticna extends JComponent {
		
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			Insets ins = getInsets();
			Dimension dim = getSize();
			Rectangle r = new Rectangle(
					ins.left, 
					ins.top, 
					dim.width-ins.left-ins.right,
					dim.height-ins.top-ins.bottom);
			if(isOpaque()) {
				g.setColor(getBackground());
				g.fillRect(r.x, r.y, r.width, r.height);
			}
			g.setColor(getForeground());
			g.drawOval(r.x, r.y, r.width-1, r.height-1);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Prozor1 prozor = new Prozor1();
				prozor.setVisible(true);
			}
		});
	}
}
