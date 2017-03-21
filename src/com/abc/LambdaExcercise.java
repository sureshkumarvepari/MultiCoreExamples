package com.abc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class LambdaExcercise {

	public static void main(String[] args) {

		List<Person> persons = Arrays.asList(new Person("aaa", "aab", 24), new Person("ddd", "dda", 53), new Person("bbb", "bba", 27), new Person(
				"eee", "eea", 24), new Person("ccc", "ccb", 45));

		//sort list by last name.
		Collections.sort(persons, (p1, p2) -> p1.getLastName().compareTo(p2.getLastName()));

		//that prints all elements in the list.
		printAll(persons, p -> true, p -> System.out.println(p));

		//prints all the people that has last beginning with c.
		printAll(persons, p -> ((Person) p).getLastName().startsWith("a"), p -> System.out.println(p));
	}

	private static void printAll(List<Person> persons, Predicate<Person> condition, Consumer<Person> consumer) {
		for (Person p : persons) {
			if (condition.test(p)) {
				try {
					consumer.accept(p);
				} catch (NullPointerException exception) {
					System.out.println("exception. " + exception.getMessage());
				}
			}
		}
	}
}
