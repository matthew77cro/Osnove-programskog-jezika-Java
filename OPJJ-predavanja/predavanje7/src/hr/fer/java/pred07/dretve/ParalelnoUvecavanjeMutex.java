package hr.fer.java.pred07.dretve;

public class ParalelnoUvecavanjeMutex {
	
	private volatile static int brojac;

	public static void main(String[] args) {
		Object mutex = new Object();

		PosaoDretve posao = new PosaoDretve(500000, mutex);
		
		final int BROJ_RADNIKA = 5;
		
		Thread[] radnici = new Thread[BROJ_RADNIKA];
		for(int i=0; i<radnici.length; i++) {
			radnici[i] = new Thread(posao, "radnik " + (i+1));
		}
		for(Thread t : radnici) {
			t.start();
		}
		
		for(Thread t : radnici) {
			while(true) {
				try {
					t.join();
				} catch (InterruptedException e) {
					continue;
				}
				break;
			}
		}
		
		System.out.printf("Rezultat je %d.%n", brojac);
	}
	
	private static class PosaoDretve implements Runnable {
		
		private int brojUvecavanja;
		private Object controller;
		
		public PosaoDretve(int brojUvecavanja, Object controller) {
			this.brojUvecavanja = brojUvecavanja;
			this.controller = controller;
		}

		@Override
		public void run() {
			for(int i=0; i<brojUvecavanja; i++) {
				synchronized (controller) {
					brojac++;
				}
			}
		}
		
	}

}
