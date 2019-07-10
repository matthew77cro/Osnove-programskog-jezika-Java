package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * This program gives you a simple calculator like the one found in Windows XP.
 * @author Matija
 *
 */
public class Calculator extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel display;
	private JCheckBox inv;
	private CalcModel calc;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Calculator c = new Calculator();
			c.setVisible(true);
		});
	}

	/**
	 * Creates and initialises the window for the calculator app
	 */
	public Calculator() {
		this.setSize(697, 398);
		this.setTitle("Java Calculator v1.0");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.calc = new CalcModelImpl();
		initGui();
	}

	/**
	 * Initialises the gui and deploys all elements inside the frame
	 */
	private void initGui() {
		this.setLayout(new CalcLayout(10));
		
		display = new JLabel("0", SwingConstants.RIGHT);
		display.setOpaque(true);
		display.setBackground(Color.YELLOW);
		display.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		display.setFont(display.getFont().deriveFont(30f));
		this.add(display, new RCPosition(1, 1));
		
		this.calc.addCalcValueListener((model) -> SwingUtilities.invokeLater(
				() -> display.setText(model.toString())
		));
		
		CalcButtons buttons = new CalcButtons(calc);
		
		this.add(buttons.inv, new RCPosition(2, 1));
		this.add(buttons.log, new RCPosition(3, 1));
		this.add(buttons.ln, new RCPosition(4, 1));
		this.add(buttons.pow, new RCPosition(5, 1));
		this.add(buttons.sin, new RCPosition(2, 2));
		this.add(buttons.cos, new RCPosition(3, 2));
		this.add(buttons.tan, new RCPosition(4, 2));
		this.add(buttons.ctg, new RCPosition(5, 2));
		
		this.add(buttons.seven, new RCPosition(2, 3));
		this.add(buttons.eight, new RCPosition(2, 4));
		this.add(buttons.nine, new RCPosition(2, 5));
		this.add(buttons.four, new RCPosition(3, 3));
		this.add(buttons.five, new RCPosition(3, 4));
		this.add(buttons.six, new RCPosition(3, 5));
		this.add(buttons.one, new RCPosition(4, 3));
		this.add(buttons.two, new RCPosition(4, 4));
		this.add(buttons.three, new RCPosition(4, 5));
		this.add(buttons.zero, new RCPosition(5, 3));
		
		this.add(buttons.signChange, new RCPosition(5, 4));
		this.add(buttons.dot, new RCPosition(5, 5));
		
		this.add(buttons.equals, new RCPosition(1, 6));
		this.add(buttons.divide, new RCPosition(2, 6));
		this.add(buttons.times, new RCPosition(3, 6));
		this.add(buttons.minus, new RCPosition(4, 6));
		this.add(buttons.plus, new RCPosition(5, 6));
		
		this.add(buttons.clr, new RCPosition(1, 7));
		this.add(buttons.reset, new RCPosition(2, 7));
		this.add(buttons.push, new RCPosition(3, 7));
		this.add(buttons.pop, new RCPosition(4, 7));
		
		inv = new JCheckBox("Inv");
		inv.addActionListener((e) -> {
			buttons.invert();
		});
		this.add(inv, new RCPosition(5, 7));
	}

}
