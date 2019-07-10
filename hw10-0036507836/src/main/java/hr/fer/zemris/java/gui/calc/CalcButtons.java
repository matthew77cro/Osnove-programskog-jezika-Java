package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * This class models button behaviour for a calculator application
 * @author Matija
 *
 */
public class CalcButtons {
	
	private CalcModel calc;
	
	public final CalcButton inv;
	public final CalcButton log;
	public final CalcButton ln;
	public final CalcButton pow;
	public final CalcButton sin;
	public final CalcButton cos;
	public final CalcButton tan;
	public final CalcButton ctg;
	
	public final CalcButton plus;
	public final CalcButton minus;
	public final CalcButton times;
	public final CalcButton divide;
	public final CalcButton equals;
	public final CalcButton dot;
	public final CalcButton signChange;
	
	public final CalcButton clr;
	public final CalcButton reset;
	public final CalcButton push;
	public final CalcButton pop;
	
	public final CalcButton zero;
	public final CalcButton one;
	public final CalcButton two;
	public final CalcButton three;
	public final CalcButton four;
	public final CalcButton five;
	public final CalcButton six;
	public final CalcButton seven;
	public final CalcButton eight;
	public final CalcButton nine;
	
	private boolean isInverted;
	
	private Stack<Double> stack;
	
	/**
	 * Initialises and crates all buttons and their behaviour on the given calc model.
	 * @param calc calc model on which these buttons operate
	 */
	public CalcButtons(CalcModel calc) {
		this.calc = calc;
		this.stack = new Stack<Double>();
		
		inv = new CalcButton("1/x", false, (c) -> c.setValue(1/c.getValue()));
		log = new CalcButton("log", false, (c) -> c.setValue(isInverted ? Math.pow(10, c.getValue()) : Math.log(c.getValue())/Math.log(10)));
		ln = new CalcButton("ln", false, (c) -> c.setValue(isInverted ? Math.pow(Math.E, c.getValue()) : Math.log(c.getValue())));
		pow = new CalcButton("x^n", false, new CalcDoubleOperatorWrapper((a, b) -> isInverted ? Math.pow(a, 1.0/b) : Math.pow(a, b)));
		sin = new CalcButton("sin", false, (c) -> c.setValue(isInverted ? Math.asin(c.getValue()) : Math.sin(c.getValue())));
		cos = new CalcButton("cos", false, (c) -> c.setValue(isInverted ? Math.acos(c.getValue()) : Math.cos(c.getValue())));
		tan = new CalcButton("tan", false, (c) -> c.setValue(isInverted ? Math.atan(c.getValue()) : Math.tan(c.getValue())));
		ctg = new CalcButton("ctg", false, (c) -> c.setValue(isInverted ? Math.PI/2 - Math.atan(c.getValue()) : 1/Math.tan(c.getValue())));
		
		plus = new CalcButton("+", false, new CalcDoubleOperatorWrapper((a, b) -> a+b));
		minus = new CalcButton("-", false, new CalcDoubleOperatorWrapper((a, b) -> a-b));
		times = new CalcButton("*", false, new CalcDoubleOperatorWrapper((a, b) -> a*b));
		divide = new CalcButton("/", false, new CalcDoubleOperatorWrapper((a, b) -> a/b));
		equals = new CalcButton("=", false, (c) -> {
			if(c.isActiveOperandSet()) {
				c.setValue(c.getPendingBinaryOperation().applyAsDouble(
						c.getActiveOperand(), 
						c.getValue()));
				c.clearActiveOperand();
			}
		});
		
		dot = new CalcButton(".", false, (c) -> { try{ c.insertDecimalPoint(); } catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Error: number already has a decimal point", "Error", JOptionPane.ERROR_MESSAGE);
		} });
		signChange = new CalcButton("+/-", false, (c) -> { try{ c.swapSign(); } catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Error: number is not editable", "Error", JOptionPane.ERROR_MESSAGE);
		} });
		
		clr = new CalcButton("clr", false, (c) -> c.clear());
		reset = new CalcButton("reset", false, (c) -> {c.clearAll(); stack.clear();});
		push = new CalcButton("push", false, (c) -> stack.push(c.getValue()));
		pop = new CalcButton("pop", false, (c) -> { try{ c.setValue(stack.pop()); } catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Error: empty stack", "Error", JOptionPane.ERROR_MESSAGE);
		} });
		
		zero = new CalcButton("0", true, doJobIgnoreEditableError((c) -> c.insertDigit(0)));
		one = new CalcButton("1", true, doJobIgnoreEditableError((c) -> c.insertDigit(1)));
		two = new CalcButton("2", true, doJobIgnoreEditableError((c) -> c.insertDigit(2)));
		three = new CalcButton("3", true, doJobIgnoreEditableError((c) -> c.insertDigit(3)));
		four = new CalcButton("4", true, doJobIgnoreEditableError((c) -> c.insertDigit(4)));
		five = new CalcButton("5", true, doJobIgnoreEditableError((c) -> c.insertDigit(5)));
		six = new CalcButton("6", true, doJobIgnoreEditableError((c) -> c.insertDigit(6)));
		seven = new CalcButton("7", true, doJobIgnoreEditableError((c) -> c.insertDigit(7)));
		eight = new CalcButton("8", true, doJobIgnoreEditableError((c) -> c.insertDigit(8)));
		nine = new CalcButton("9", true, doJobIgnoreEditableError((c) -> c.insertDigit(9)));
	}
	
	/**
	 * Inverts buttons for log, ln, pow, sin, cos, tan and ctg so that they
	 * calculate inverted functions.
	 */
	public void invert() {
		if(!isInverted) {
			isInverted = true;
			log.setText("10^x");
			ln.setText("e^x");
			pow.setText("x^(1/n)");
			sin.setText("arcsin");
			cos.setText("arccos");
			tan.setText("arctan");
			ctg.setText("arcctg");
		} else {
			isInverted = false;
			log.setText("log");
			ln.setText("ln");
			pow.setText("x^n");
			sin.setText("sin");
			cos.setText("cos");
			tan.setText("tan");
			ctg.setText("ctg");
		}
	}
	
	/**
	 * Helper method for trying to enter number into a calculator when there is a
	 * number on the display that is not editable. This function ignorse it and
	 * overwrites the number
	 * @param job job that needs to be performed
	 * @return returns a job model that performs exact same job as given by the
	 * parameter only overwriting number on the display if it is not editable
	 */
	private static Consumer<CalcModel> doJobIgnoreEditableError(Consumer<CalcModel> job) {
		return (t) ->{
			if(!t.isEditable()) t.clear();
			job.accept(t);
		};
	}

	/**
	 * A helper class that models a button for the calculator app.
	 * @author Matija
	 *
	 */
	private class CalcButton extends JButton{

		private static final long serialVersionUID = 1L;

		/**
		 * Creates and initialises the button with text and colour and an action that it performs
		 * @param text displayed text on the button
		 * @param emphasize does this button need to be emphasised
		 * @param consumer models an action that button needs to perform
		 */
		public CalcButton(String text, boolean emphasize, Consumer<CalcModel> consumer) {
			super(text);
			this.setOpaque(true);
			this.setBackground(Color.CYAN);
			if(emphasize) this.setFont(this.getFont().deriveFont(30f));
			this.addActionListener((e) -> {
				new Thread(() -> {consumer.accept(calc);}).start();
			});
		}

	}
	
	/**
	 * Models an operator that a calculator button needs to perform on a specific calcmodel.
	 * @author Matija
	 *
	 */
	private static class CalcDoubleOperatorWrapper implements Consumer<CalcModel> {
		
		private DoubleBinaryOperator op;
		
		/**
		 * Creates and initialises the operator.
		 * @param op operator that this button needs to perform
		 */
		public CalcDoubleOperatorWrapper(DoubleBinaryOperator op) {
			this.op = op;
		}

		@Override
		public void accept(CalcModel t) {
			if(t.isActiveOperandSet()) {
				t.setActiveOperand(t.getPendingBinaryOperation().applyAsDouble(
						t.getActiveOperand(), 
						t.getValue()));
			} else {
				t.setActiveOperand(t.getValue());
			}
			t.clear();
			t.setPendingBinaryOperation(op);
		}
		
	}

}
