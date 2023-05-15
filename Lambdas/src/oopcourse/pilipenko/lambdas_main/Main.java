package oopcourse.pilipenko.lambdas_main;

import oopcourse.pilipenko.lambdas.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Person> personList = Arrays.asList(
                new Person("Stepan", 56),
                new Person("Makar", 32),
                new Person("Olga", 12),
                new Person("Maks", 43),
                new Person("Sveta", 25),
                new Person("Andrey", 30),
                new Person("Katya", 14),
                new Person("Elena", 1),
                new Person("Natasha", 15),
                new Person("Petya", 25),
                new Person("Makar", 10)
        );

        printUniqueNames(personList);
        System.out.println(getPeopleYoungerThan18(personList));
        System.out.println(getMap(personList));
        printPeopleFrom20To45(personList);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите число до которого вывести корни чисел:");
        int numbersCount = scanner.nextInt();
        printNumbersRoots(numbersCount);

        System.out.println("Введите количество чисел Фибоначчи:");
        int fibonacciNumbersCount = scanner.nextInt();
        printFibonacciNumbers(fibonacciNumbersCount);
    }

    public static void printUniqueNames(List<Person> personList) {
        String names = personList.stream()
                .map(Person::name)
                .distinct()
                .collect(Collectors.joining(", "));

        System.out.println("Имена: " + names);
    }


    public static List<Person> getPeopleYoungerThan18(List<Person> personList) {
        List<Person> peopleYoungerThan18 = personList.stream()
                .filter(x -> x.age() < 18)
                .toList();

        double averageAge = peopleYoungerThan18.stream()
                .mapToInt(Person::age)
                .summaryStatistics().getAverage();

        System.out.println("Average year = " + averageAge);

        return peopleYoungerThan18;
    }

    public static Map<String, Double> getMap(List<Person> personList) {
        return personList.stream()
                .collect(Collectors.groupingBy(
                        Person::name,
                        Collectors.averagingInt(Person::age)
                ));
    }

    public static void printPeopleFrom20To45(List<Person> personList) {
        List<String> people = personList.stream()
                .filter(x -> x.age() >= 20 && x.age() <= 45)
                .sorted(Comparator.comparingInt(Person::age).reversed())
                .map(Person::name)
                .collect(Collectors.toList());

        System.out.println(people);
    }

    public static void printNumbersRoots(int numbersCount) {
        DoubleStream roots = IntStream
                .iterate(0, x -> x + 1)
                .mapToDouble(Math::sqrt)
                .limit(numbersCount);
        roots.forEach(System.out::println);
    }

    public static void printFibonacciNumbers(int numbersCount) {
        Stream
                .iterate(new int[]{0, 1}, fib -> new int[]{fib[1], fib[0] + fib[1]})
                .limit(numbersCount)
                .forEach(fib -> System.out.println(fib[0] + " "));
    }
}