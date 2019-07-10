package hr.fer.zemris.java.tecaj_7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BrojPI1 {

	public static void main(String[] args) {
		
		final int numberOfSamples = 100_000;

		ExecutorService pool = Executors.newFixedThreadPool(2);

		for(int i = 0; i < 1000; i++) {
			double pi = izracunajParalelno2(pool, numberOfSamples);
			//double pi = izracunajParalelno(pool, numberOfSamples);
			if(i==0) System.out.println("Pi = " + pi);
		}
		
		
		final int MEASURES = 1_000;
		long t0 = System.currentTimeMillis();
		for(int attempt = 0; attempt < MEASURES; attempt++) {
			izracunajParalelno2(pool, numberOfSamples);
			//izracunajParalelno(pool, numberOfSamples);
		}		
		long t1 = System.currentTimeMillis();
		System.out.println("Vrijeme operacije: "+ (t1-t0)/(double)MEASURES+" ms");
		
		pool.shutdown();
	}

	private static double izracunajParalelno(ExecutorService pool, int numberOfSamples) {
		class Posao implements Runnable {
			int inside;
			int samples;
			public Posao(int samples) {
				super();
				this.samples = samples;
			}
			@Override
			public void run() {
				inside = PIUtil.testNumbersInCircle(samples, new Random());
			}
		}
		Posao p1 = new Posao(numberOfSamples/2);
		Posao p2 = new Posao(numberOfSamples - numberOfSamples/2);
		List<Future<?>> rezultati = new ArrayList<Future<?>>();
		rezultati.add(pool.submit(p1));
		rezultati.add(pool.submit(p2));
		for(Future<?> f : rezultati) {
			while(true) {
				try {
					f.get();
					break;
				} catch (InterruptedException | ExecutionException e) {
				}
			}
		}
		return 4*(p1.inside+p2.inside)/(double)numberOfSamples;
	}

	private static double izracunajParalelno2(ExecutorService pool, int numberOfSamples) {
		class Posao implements Callable<Integer> {
			int samples;
			public Posao(int samples) {
				super();
				this.samples = samples;
			}
			@Override
			public Integer call() {
				int inside = PIUtil.testNumbersInCircle(samples, new Random());
				return inside;
			}
		}
		Posao p1 = new Posao(numberOfSamples/2);
		Posao p2 = new Posao(numberOfSamples - numberOfSamples/2);
		List<Future<Integer>> rezultati = new ArrayList<Future<Integer>>();
		rezultati.add(pool.submit(p1));
		rezultati.add(pool.submit(p2));
		int inside = 0;
		for(Future<Integer> f : rezultati) {
			while(true) {
				try {
					inside += f.get();
					break;
				} catch (InterruptedException | ExecutionException e) {
				}
			}
		}
		return 4*inside/(double)numberOfSamples;
	}
	
}