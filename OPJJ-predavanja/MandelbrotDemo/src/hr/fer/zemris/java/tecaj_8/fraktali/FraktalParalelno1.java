package hr.fer.zemris.java.tecaj_8.fraktali;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.mandelbrot.Mandelbrot;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

public class FraktalParalelno1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FractalViewer.show(new MojProducer());	
	}

	public static class PosaoIzracuna implements Runnable {
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
		
		@Override
		public void run() {
			
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
			
			Thread[] radnici = new Thread[2];
			for(int i = 0; i < radnici.length; i++) {
				int yMin = i == 0 ? 0 : height/2-1;
				int yMax = i == 0 ? height/2 : height-1;
				radnici[i] = new Thread(
					new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel)
				);
			}
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)m, requestNo);
		}
	}
}
