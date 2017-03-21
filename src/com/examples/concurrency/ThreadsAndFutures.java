package com.examples.concurrency;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadsAndFutures {

	public String urlContents;
	
	public static void main(String[] args) throws Exception{
		// Spawn a thread to read a url
		ThreadsAndFutures t = new ThreadsAndFutures();
		WorkerRunnable runner = new WorkerRunnable(t);
		Thread thread = new Thread(runner);
		thread.start();
		thread.join();
		
		System.out.println("Thread result is: "+t.urlContents.substring(0,50));
		
		ExecutorService pool = Executors.newCachedThreadPool();
		Future<String> future = pool.submit(new WorkerCallable());
		String contents = future.get();
		
		System.out.println("Future result is: "+contents.substring(0,50));
	}
	
	public static String readUrl(String u) {
		String contents = "";
		try {
			URL url = new URL(u);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	            contents += inputLine;
	        }
	        in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return contents;
	}
}

class WorkerCallable implements Callable<String> {

	@Override
	public String call() throws Exception {
		System.out.println("Callable Started");
		String contents = ThreadsAndFutures.readUrl("http://drudgereport.com");
		System.out.println("Callable Finished");
		return contents;
	}
	
}




class WorkerRunnable implements Runnable {

	ThreadsAndFutures t;
	
	WorkerRunnable(ThreadsAndFutures t) {
		this.t=t;
	}
	
	@Override
	public void run() {
		System.out.println("Starting runnable");
		String contents = ThreadsAndFutures.readUrl("http://huffingtonpost.com");
		t.urlContents = contents;
		System.out.println("Finished runnable");
	}
	
}
