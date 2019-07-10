package hr.fer.zemris.java.tecaj_7;

import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
	private Runnable redLittlePill = () -> {};
	private LinkedBlockingQueue<JobInfo> jobs = new LinkedBlockingQueue<>();
	private Thread[] workers;
	
	public ThreadPool(int numberOfWorkers) {
		workers = new Thread[numberOfWorkers];
		for(int i = 0; i < numberOfWorkers; i++) {
			workers[i] = new Thread(() -> {
				while(true) {
					JobInfo jobInfo = nextJob();
					if(jobInfo.job==redLittlePill) return;
					try {
						jobInfo.job.run();
					} finally {
						jobInfo.markExecuted();
					}
				}
			});
			workers[i].start();
		}
	}

	public int numberOfWorkers() {
		return workers.length;
	}
	
	public void shutdown() {
		for(int i = 0; i < workers.length; i++) {
			addJob(redLittlePill);
		}
	}
	
	public ThreadPool() {
		this(Runtime.getRuntime().availableProcessors());
	}
	
	public JobInfo addJob(Runnable job) {
		JobInfo jobInfo = new JobInfo(job);
		while(true) {
			try {
				jobs.put(jobInfo);
				return jobInfo;
			} catch (InterruptedException e) {
			}
		}
	}
	
	private JobInfo nextJob() {
		while(true) {
			try {
				JobInfo job = jobs.take();
				return job;
			} catch (InterruptedException e) {
			}
		}
	}
	
	public static class JobInfo {
		private boolean executed = false;
		private Runnable job;
		
		public JobInfo(Runnable job) {
			super();
			this.job = job;
		}
		
		private synchronized void markExecuted() {
			executed = true;
			notifyAll();
		}
		
		public synchronized boolean isExecuted() {
			return executed;
		}
		
		public synchronized void waitUntilNotExecuted() throws InterruptedException {
			while(!executed) {
				wait();
			}
		}
		
	}
}