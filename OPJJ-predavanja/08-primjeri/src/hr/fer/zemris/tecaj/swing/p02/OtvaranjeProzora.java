package hr.fer.zemris.tecaj.swing.p02;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class OtvaranjeProzora {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		frame.setLocation(20, 20);
		frame.setSize(500, 200);
		
		frame.setDefaultCloseOperation(
			WindowConstants.DISPOSE_ON_CLOSE
		);
		
		frame.setVisible(true);
	}
}
