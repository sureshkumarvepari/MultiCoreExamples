package com.multicore.samples;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ListOperations {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ListOperations(int startIndex, int lastIndex, String name) {
		super();
		this.name = name;
	}

	public TaskTime findElementCount() {
		System.out.println(name + "start time " + LocalDate.now() + ":" + LocalTime.now());
		List<Integer> intList = createRandomIntArray(22500000);
		Instant start = Instant.now();
		long count = 0;
		for (int i = 0; i < 22500000; i++) {
			count = checkFor70(intList, count, i);
		}
		Instant end = Instant.now();
		TaskTime taskTime = new TaskTime();
		taskTime.count = count;
		taskTime.completionTime = end; 
		
		System.out.println(name + "end time " + LocalDate.now() + ":" + LocalTime.now());
		System.out.println(name + "elapsed time in mills " + Duration.between(start, end).toMillis());
		return taskTime;
	}

	private long checkFor70(List<Integer> intList, long count, int i) {
		if (intList.get(i) == 70) {
			count++;
		}
		return count;
	}

	public long findElementCountsingleThrd(/*int element*/) {
		System.out.println(name + "start time " + LocalDate.now() + ":" + LocalTime.now());
		List<Integer> intList = createRandomIntArray(90000000);
		Instant start = Instant.now();
		long count = 0;
		for (int i = 0; i < 90000000; i++) {
			count = checkFor70(intList, count, i);
		}
		System.out.println(name + "end time " + LocalDate.now() + ":" + LocalTime.now());
		System.out.println(name + "elapsed time in mills " + Duration.between(start, Instant.now()).toMillis());
		return count;
	}

	private List<Integer> createRandomIntArray(int numberOfElements) {
		List<Integer> list = new ArrayList<Integer>(numberOfElements);
		for (int i = 0; i < numberOfElements; i++) {
			list.add(i, (int) (Math.random() * 100));
		}
		return list;
	}
	
	public class TaskTime {
		long count;
		Instant completionTime;		
	}
}
