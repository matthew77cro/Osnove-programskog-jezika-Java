package hr.fer.zemris.java.gui.calc.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.DoubleBinaryOperator;

/**
 * Creates a model of a simple calculator like one used in Windows XP
 * @author Matija
 *
 */
public class CalcModelImpl implements CalcModel{
	
	private boolean isEditable;
	private boolean isNegative;
	private String value;
	private double doubleValue;
	
	private double activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private boolean isActiveOperandSet;
	
	private Set<CalcValueListener> listeners;
	
	/**
	 * Creates and initialises the model
	 */
	public CalcModelImpl() {
		isEditable = true;
		isNegative = false;
		value = "";
		doubleValue = 0;
		this.listeners = new HashSet<CalcValueListener>();
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(Objects.requireNonNull(l));
	}
	
	/**
	 * This method notifies all registered listeners that the display value of
	 * this calculator has changed.
	 */
	private void notifyAllListeners() {
		for(var listener : listeners) {
			listener.valueChanged(this);
		}
	}

	@Override
	public double getValue() {
		return (isNegative ? -1 : 1) * doubleValue;
	}

	@Override
	public void setValue(double value) {
		this.doubleValue = Math.abs(value);
		
		if(Double.isInfinite(value)) {
			this.value = "Infinity";
		} else if (Double.isNaN(value)) {
			this.value = "NaN";
		} else {
			this.value = Double.toString(this.doubleValue);
		}
		
		this.isNegative = value < 0;
		this.isEditable = false;
		notifyAllListeners();
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		this.isNegative = false;
		this.value = "";
		this.doubleValue = 0;
		this.isEditable = true;
		notifyAllListeners();
	}

	@Override
	public void clearAll() {
		clear();
		this.isActiveOperandSet = false;
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable) throw new CalculatorInputException("This model is not editable.");
		
		this.isNegative = !isNegative;
		notifyAllListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!isEditable) throw new CalculatorInputException("This model is not editable.");
		if(this.value.isEmpty()) throw new CalculatorInputException("Empty number");
		if(this.value.contains(".")) throw new CalculatorInputException("Invalid action. Value already contains a decimal point!");
		
		this.value = this.value + ".";
		notifyAllListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!isEditable) throw new CalculatorInputException();
		if(doubleValue*10+digit > Double.MAX_VALUE) throw new CalculatorInputException();
		
		try {
			doubleValue = Double.parseDouble(value + digit);
		} catch (Exception ex) {
			throw new CalculatorInputException("Invalid digit concatenation.");
		}
		
		if(value.equals("0")) {
			value = Integer.toString(digit);
		} else {
			value = value + digit;
		}
		notifyAllListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return isActiveOperandSet;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet) throw new IllegalStateException();
		
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		this.isActiveOperandSet = true;
	}

	@Override
	public void clearActiveOperand() {
		this.isActiveOperandSet = false;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(isNegative ? "-" : "");
		
		if(value.isEmpty()) {
			sb.append("0");
		} else {
			sb.append(value);
		}
		
		return sb.toString();
	}

}
