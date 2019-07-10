package hr.fer.zemris.java.fractals;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Utility class that has a static method that knows how to compute newton fractal directly without
 * parallelisation.
 * @author Matija
 *
 */
public class NewtonFractal {

	/**
	 * Calculates newton fractal with the given attributes
	 * @param reMin minimal value on the real axis
	 * @param reMax maximal value on the real axis
	 * @param imMin minimal value on the imaginary axis
	 * @param imMax maximal value on the imaginary axis
	 * @param width window width
	 * @param height window height
	 * @param yMin minimal value on the y axis
	 * @param yMax maximal value on the y axis
	 * @param data contains data for drawing the fractal
	 * @param cancel set to true if operation needs to be cancelled
	 * @param crp polynomial in rooted form from which fractal needs to be calculated
	 * @param cp polynomial form from which fractal needs to be calculated
	 * @param cpder first derivative of the polynomial form from which fractal needs to be calculated
	 * @param convergenceTreshold threshold for testing convergence
	 * @param rootTreshold threshold for finding closest root
	 * @param maxIter maximal number of iteration for convergence test
	 */
	public static void calculate(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin, int yMax, short[] data, AtomicBoolean cancel,
			ComplexRootedPolynomial crp, ComplexPolynomial cp, ComplexPolynomial cpder, 
			double convergenceTreshold, double rootTreshold, double maxIter) {
		
		int offset = yMin * width;
		
		for (int y = yMin; y <= yMax; y++) {
			if(cancel.get()) break;
			
			for(int x=0; x<width; x++) {				
				double re = x/(width-1.0)*(reMax-reMin)+reMin;
				double im = (height-1.0-y)/(height-1.0)*(imMax-imMin)+imMin;
				Complex c = new Complex(re, im);
				Complex zn = c;
				double module;
				int iter = 0;
				do {
					Complex numerator = cp.apply(zn);
					Complex denominator = cpder.apply(zn);
					Complex znold = zn;
					Complex fraction = numerator.divide(denominator);
					zn = zn.sub(fraction);
					module = znold.sub(zn).module();
					iter++;
				} while(module>convergenceTreshold && iter<maxIter);
				int index = crp.indexOfClosestRootFor(zn, rootTreshold);
				data[offset++]=(short)(index+1);
			}
		}
	}

}
