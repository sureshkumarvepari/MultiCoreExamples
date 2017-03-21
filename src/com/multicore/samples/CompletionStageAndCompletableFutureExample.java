package com.multicore.samples;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import com.multicore.samples.ListOperations.TaskTime;

public class CompletionStageAndCompletableFutureExample {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		executeInParalellMode();
		executeInSingleThreadMode();
	}

	private static void executeInSingleThreadMode() {
		ListOperations listOperations = new ListOperations(0, 300000000, "single thread ");
		long findElementCount = listOperations.findElementCountsingleThrd();
		System.out.println("Element total count : " + findElementCount);
	}

	private static void executeInParalellMode() {
		ExecutorService executor = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors());
		final List<Instant> completionTimes = new ArrayList<Instant>();
		IntStream.range(0, 4).forEach(
				i -> {
					String name = "Thread " + i + " ";
					CompletableFuture<TaskTime> completableFuture = CompletableFuture.supplyAsync(
							new ListOperations(0, 30000000, name)::findElementCount, executor);
					completableFuture.thenAccept((taskTime) -> {
						System.out.println(name + "count " + taskTime.count);
						completionTimes.add(taskTime.completionTime);
					});
				});
		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			completionTimes.sort((i1, i2) -> i1.compareTo(i2));
		} catch (InterruptedException exception) {
			System.out.println(exception.getMessage());
		}
		System.out.println("Total time taken to complete it in parallell mode in mills "
				+ Duration.between(completionTimes.get(0), completionTimes.get(completionTimes.size() - 1)).toMillis());
	}
}
