package hr.fer.zemris.java.hw13;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for calculating sine and cosine function values of integer numbers between a and b.
 * Integer numbers represent degrees, not radians.
 * @author Matija
 *
 */
@SuppressWarnings("serial")
@WebServlet(name="trigonometric", urlPatterns= {"/trigonometric"})
public class TrigonometricServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String paramA = req.getParameter("a");
		String paramB = req.getParameter("b");
		
		int a = 0;
		int b = 360;
	
		try {
			a = Integer.parseInt(paramA);
		} catch (NumberFormatException ignorable) {
		}
		try {
			b = Integer.parseInt(paramB);
		} catch (NumberFormatException ignorable) {
		}
		
		if(a>b) {
			int c = a;
			a = b;
			b = c;
		}
		
		b = b>a+720 ? a+720 : b;
		
		Map<Integer, NumberPair> table = new LinkedHashMap<Integer, TrigonometricServlet.NumberPair>();
		for(int i=a; i<=b; i++) {
			double radian = ((double)i)/180*Math.PI;
			table.put(i, new NumberPair(Math.sin(radian), Math.cos(radian)));
		}
		
		req.setAttribute("table", table);
		
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
		
	}
	
	/**
	 * Class that represents a pair of two double numbers
	 * @author Matija
	 *
	 */
	public static class NumberPair {
		
		private double a;
		private double b;
		
		/**
		 * Initialises the pair of numbers
		 * @param a first number
		 * @param b second number
		 */
		public NumberPair(double a, double b) {
			this.a = a;
			this.b = b;
		}

		/**
		 * Returns the first number in the pair
		 * @return the first number in the pair
		 */
		public double getA() {
			return a;
		}
		
		/**
		 * Returns the second number in the pair
		 * @return the second number in the pair
		 */
		public double getB() {
			return b;
		}
		
	}

}
