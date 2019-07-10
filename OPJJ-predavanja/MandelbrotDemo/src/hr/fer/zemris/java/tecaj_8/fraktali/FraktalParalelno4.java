package hr.fer.zemris.java.tecaj_8.fraktali;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.mandelbrot.Mandelbrot;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

public class FraktalParalelno4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FractalViewer.show(new MojProducer());	
	}

	public static class PosaoIzracuna extends RecursiveAction {
		private static final long serialVersionUID = 1L;

		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		static final int treshold = 16;
		AtomicBoolean cancel;
		
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
		}

		public void compute() {
			if(yMax-yMin+1 <= treshold) {
				computeDirect();
				return;
			}
			invokeAll(
				new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMin+(yMax-yMin)/2, m, data, cancel),
				new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin+(yMax-yMin)/2+1, yMax, m, data, cancel)
			);
		}

		public void computeDirect() {
			// Otkomentirati sljedeći redak da se vidi kako je napravljena dekompozicija:
			// System.out.println("Racunam od "+yMin+" do "+yMax);
			
			Mandelbrot.calculate(reMin, reMax, imMin, imMax, width, height, m, yMin, yMax, data, cancel);
			
		}
	}
	
	
	public static class MojProducer implements IFractalProducer {
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			short[] data = new short[width * height];

			ForkJoinPool pool = new ForkJoinPool();
			pool.invoke(new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, 0, height-1, m, data, cancel));
			pool.shutdown();
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)m, requestNo);
		}
	}
}
