package hr.fer.zemris.java.tecaj_7;

import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPoolSimple {
	private Runnable redLittlePill = () -> {};
	private LinkedBlockingQueue<Runnable> jobs = new LinkedBlockingQueue<>();
	private Thread[] workers;
	
	public ThreadPoolSimple(int numberOfWorkers) {
		workers = new Thread[numberOfWorkers];
		for(int i = 0; i < numberOfWorkers; i++) {
			workers[i] = new Thread(() -> {
				while(true) {
					Runnable job = nextJob();
					if(job==redLittlePill) return;
					job.run();
				}
			});
			workers[i].start();
		}
	}
	
	public ThreadPoolSimple() {
		this(Runtime.getRuntime().availableProcessors());
	}
	
	public void addJob(Runnable job) {
		while(true) {
			try {
				jobs.put(job);
				return;
			} catch (InterruptedException e) {
			}
		}
	}

	public void shutdown() {
		for(int i = 0; i < workers.length; i++) {
			addJob(redLittlePill);
		}
	}
	
	private Runnable nextJob() {
		while(true) {
			try {
				Runnable job = jobs.take();
				return job;
			} catch (InterruptedException e) {
			}
		}
	}
	
}