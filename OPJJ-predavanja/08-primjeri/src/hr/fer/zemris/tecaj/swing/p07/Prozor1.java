package hr.fer.zemris.tecaj.swing.p07;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
		
		final Sat komponenta1 = new Sat();
		komponenta1.setLocation(10, 10);
		komponenta1.setSize(100, 40);
		komponenta1.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		komponenta1.setOpaque(true);
		komponenta1.setBackground(Color.YELLOW);
		
		getContentPane().add(komponenta1);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				komponenta1.zaustavi();
			}
		});
	}

	static class Sat extends JComponent {

		private static final long serialVersionUID = 1L;
		
		volatile String vrijeme;
		volatile boolean stopRequested;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		public Sat() {
			updateTime();
			
			Thread t = new Thread(()->{
				while(true) {
					try {
						Thread.sleep(500);
					} catch(Exception ex) {}
					if(stopRequested) break;
					SwingUtilities.invokeLater(()->{
						updateTime();
					});
				}
				System.out.println("Zaustavljen.");
			});
			t.setDaemon(true);
			t.start();
		}
		
		private void zaustavi() {
			stopRequested = true;
		}
		
		private void updateTime() {
			vrijeme = formatter.format(LocalTime.now());
			repaint();
		}

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
			
			FontMetrics fm = g.getFontMetrics();
			int w = fm.stringWidth(vrijeme);
			int h = fm.getAscent();
			
			g.drawString(vrijeme, r.x + (r.width-w)/2, r.y+r.height-(r.height-h)/2);
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
