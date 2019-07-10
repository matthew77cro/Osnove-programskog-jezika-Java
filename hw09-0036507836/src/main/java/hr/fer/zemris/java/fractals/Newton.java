package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This is a program used for visualising fractals derived from Newton-Raphson iteration
 * @author Matija
 *
 */
public class Newton {
	
	public final static double convergenceTreshold = 1E-3;
	public final static double rootTreshold = 2E-3;
	public final static int maxIter = 1024;

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer."); 
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");	
		
		ComplexRootedPolynomial crp = null;
		try {
			crp = createPolynomialFromInput(sc);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return;
		}
		
		System.out.println("Image of fractal will appear shortly. Thank you.");

		sc.close();
		
		FractalViewer.show(new MojProducer(crp));
		
	}

	/**
	 * This method is used for loading data from standard input via given scanner
	 * @param sc scanner used for inputing data into program
	 * @return new polynomial created from roots given by the end user via standard input
	 */
	private static ComplexRootedPolynomial createPolynomialFromInput(Scanner sc) {

		List<Complex> roots = new ArrayList<Complex>();
		
		int i = 1;
		while(true) {
			System.out.printf("Root %d> ", i);
			if(sc.hasNextLine()) {
				String next = sc.nextLine();
				if(next.trim().equalsIgnoreCase("done")) {
					if(roots.size()!=0) break;
					System.out.println("There must be at least one root!");
					continue;
				}
				try {
					roots.add(Complex.parse(next.replace(" ", "")));
				} catch (IllegalArgumentException ex) {
					System.out.println("Input is not valid! " + ex.getMessage());
					continue;
				}
			}
			i++;
		}
		
		return new ComplexRootedPolynomial(Complex.ONE, roots.toArray(new Complex[roots.size()]));
	}
	
	/**
	 * Class used for parallelising calculations for fractals 
	 * @author Matija
	 *
	 */
	public static class PosaoIzracuna implements Callable<Void> {
		
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		short[] data;
		AtomicBoolean cancel;
		ComplexRootedPolynomial crp;
		ComplexPolynomial cp;
		ComplexPolynomial cpder;
		double convergenceTreshold;
		double rootTreshold;
		double maxIter;
		
		/**
		 * Creates and initialises new calculation for Newton fractals
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
		public PosaoIzracuna(double reMin, double reMax, double imMin, double imMax, 
				int width, int height, int yMin, int yMax, short[] data, AtomicBoolean cancel, 
				ComplexRootedPolynomial crp, ComplexPolynomial cp,
				ComplexPolynomial cpder, double convergenceTreshold, 
				double rootTreshold, double maxIter) {
			
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
			this.cancel = cancel;
			this.crp = crp;
			this.cp = cp;
			this.cpder = cpder;
			this.convergenceTreshold = convergenceTreshold;
			this.rootTreshold = rootTreshold;
			this.maxIter = maxIter;
			
		}
		
		@Override
		public Void call() throws Exception {
			
			NewtonFractal.calculate(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, cancel, 
					crp, cp, cpder, convergenceTreshold, rootTreshold, maxIter);
			
			return null;
		}
		
	}
	
	/**
	 * Producer of fractals used for giving the UI information about how
	 * to draw certain fractal (in this case Newton fractal).
	 * @author Matija
	 *
	 */
	public static class MojProducer implements IFractalProducer {
		
		private ComplexRootedPolynomial crp;
		private ComplexPolynomial cp;
		private ComplexPolynomial cpder;
		private ExecutorService pool;
		
		/**
		 * Creates and initialises a producer for calculating Newton fractal
		 * for given polynomial
		 * @param crp polynomial for which to calculate Newton fractal
		 */
		public MojProducer(ComplexRootedPolynomial crp) {
			this.crp = crp;
			this.cp = crp.toComplexPolynom();
			this.cpder = cp.derive();
			this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), 
					(runnable) -> {
						Thread t = new Thread(runnable);
						t.setDaemon(true);
						return t;
					});
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			short data[] = new short[width*height];
			final int brojTraka = Runtime.getRuntime().availableProcessors()*8;
			int brojYPoTraci = height / brojTraka;
			
			List<Future<Void>> rezultati = new ArrayList<>();
			
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==brojTraka-1) {
					yMax = height-1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, 
						yMax, data, cancel, crp, cp, cpder, Newton.convergenceTreshold, 
						Newton.rootTreshold, Newton.maxIter);
				rezultati.add(pool.submit(posao));
			}
			
			for(Future<Void> p : rezultati) {
				try {
					p.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			
			observer.acceptResult(data, (short) (cp.order()+1), requestNo);
		}
		
	}

}
