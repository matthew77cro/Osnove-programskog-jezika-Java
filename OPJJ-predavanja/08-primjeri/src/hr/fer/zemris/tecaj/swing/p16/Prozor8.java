package hr.fer.zemris.tecaj.swing.p16;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor8 extends JFrame {

	private static final long serialVersionUID = 1L;

	public Prozor8() {
		setLocation(20, 50);
		setSize(300, 200);
		setTitle("Moj prvi prozor!");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(new Komponenta(), BorderLayout.CENTER);
	}

	private static class Komponenta extends JComponent {
		private static final long serialVersionUID = 1L;
		
		private BufferedImage bim1;
		private BufferedImage bim2;
		
		public Komponenta() {
			// Učitaj sliku 1
			try(InputStream is = Prozor8.class.getResourceAsStream("/jabuka.jpg")) {
				bim1 = ImageIO.read(is);
			} catch(IOException ex) {
			}
			
			// Nactaj sliku 2
			bim2 = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = bim2.createGraphics();
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, bim2.getWidth(), bim2.getHeight());
			g.setColor(Color.YELLOW);
			g.fillOval(0, 0, bim2.getWidth(), bim2.getHeight());
			g.dispose();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, getWidth(), getHeight());
			if(bim1!=null) {
				// Isprobati prvi način odnosno drugi način:
				// 1. način:
				g.drawImage(bim1, 0, 0, null);
				// 2. način:
				//g.drawImage(bim1, 0, 0, getWidth(), getHeight(), 0, 0, bim1.getWidth(), bim1.getHeight(), null);
			}
			g.drawImage(bim2, getWidth()-bim2.getWidth(), getHeight()-bim2.getHeight(), null);
		}
	}
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Prozor8();
			frame.setVisible(true);
		});
	}
}
