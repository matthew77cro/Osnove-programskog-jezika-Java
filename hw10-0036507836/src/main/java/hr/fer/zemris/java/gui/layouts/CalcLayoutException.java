package hr.fer.zemris.java.gui.layouts;

/**
 * Models an exception thrown when an illegal action is performed on
 * calc layout.
 * @author Matija
 *
 */
public class CalcLayoutException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CalcLayoutException() {
		super();
	}
	
	public CalcLayoutException(String msg) {
		super(msg);
	}

}
