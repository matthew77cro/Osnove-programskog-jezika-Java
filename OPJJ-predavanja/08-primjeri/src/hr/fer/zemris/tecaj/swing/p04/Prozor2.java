package hr.fer.zemris.tecaj.swing.p04;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class Prozor2 extends JFrame {

	private static final long serialVersionUID = 1L;

	public Prozor2() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prozor1");
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
		
	}
	
	private void initGUI() {
		getContentPane().setLayout(null);
		
		JComponent komponenta1 = new JComponent() {
			private static final long serialVersionUID = 1L;
		};
		komponenta1.setLocation(10, 10);
		komponenta1.setSize(100, 40);
		komponenta1.setBorder(
			new BorderDecorator(
				BorderFactory.createLineBorder(Color.RED, 2)
			)
		);
		
		getContentPane().add(komponenta1);
	}

	static class BorderDecorator implements Border {
		private Border original;
		
		public BorderDecorator(Border original) {
			super();
			this.original = original;
		}

		@Override
		public Insets getBorderInsets(Component c) {
			return original.getBorderInsets(c);
		}
		
		@Override
		public boolean isBorderOpaque() {
			return original.isBorderOpaque();
		}
		
		@Override
		public void paintBorder(Component c, Graphics g, int x, int y,
				int width, int height) {
			original.paintBorder(c, g, x, y, width, height);
		}
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
