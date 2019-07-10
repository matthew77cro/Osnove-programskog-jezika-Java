package hr.fer.zemris.java.tecaj_7;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class BrojPI2a {

	public static void main(String[] args) {
		
		final int numberOfSamples = 100_000;

		ForkJoinPool pool = new ForkJoinPool();

		for(int i = 0; i < 1000; i++) {
			double pi = izracunajParalelno3(pool, numberOfSamples);
			if(i==0) System.out.println("Pi = " + pi);
		}
		
		
		final int MEASURES = 1_000;
		long t0 = System.currentTimeMillis();
		for(int attempt = 0; attempt < MEASURES; attempt++) {
			izracunajParalelno3(pool, numberOfSamples);
		}		
		long t1 = System.currentTimeMillis();
		System.out.println("Vrijeme operacije: "+ (t1-t0)/(double)MEASURES+" ms");
		
		pool.shutdown();
	}

	private static double izracunajParalelno3(ForkJoinPool pool, int numberOfSamples) {
		class Posao extends RecursiveAction {
			private static final long serialVersionUID = 1L;
			int inside;
			int samples;
			public Posao(int samples) {
				super();
				this.samples = samples;
			}
			@Override
			protected void compute() {
				if(samples <= 10_000) {
					computeDirect();
					return;
				}
				Posao p1 = new Posao(samples/2); 
				Posao p2 = new Posao(samples - samples/2); 
				invokeAll(p1, p2);
				inside = p1.inside + p2.inside;
			}
			private void computeDirect() {
				inside = PIUtil.testNumbersInCircle(samples, new Random());
			}
		}
		Posao p = new Posao(numberOfSamples);
		pool.invoke(p);
		return 4*p.inside/(double)numberOfSamples;
	}
	
}