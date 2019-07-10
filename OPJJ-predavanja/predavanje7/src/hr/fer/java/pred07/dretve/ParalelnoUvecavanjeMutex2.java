package hr.fer.java.pred07.dretve;

public class ParalelnoUvecavanjeMutex2 {
	
	private volatile static int brojac;

	public static void main(String[] args) {
		PosaoDretve posao = new PosaoDretve(500000);
		
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
		
		public PosaoDretve(int brojUvecavanja) {
			this.brojUvecavanja = brojUvecavanja;
		}

		@Override
		public void run() {
			for(int i=0; i<brojUvecavanja; i++) {
				synchronized (this) {
					brojac++;
				}
			}
		}
		
	}

}
