package oopcourse.pilipenko.lambdas;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Person> personsList = Arrays.asList(
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

        List<String> uniqueNames = getUniqueNames(personsList);
        System.out.println("Список уникальных имен: " + uniqueNames);

        printUniqueNames(personsList);

        List<Person> peopleYoungerThan18 = getPeopleYoungerThan18(personsList);
        System.out.println("Список людей младше 18: " + peopleYoungerThan18);
        System.out.println("Средний возраст людей младше 18 = " + getAveragePeopleAge(peopleYoungerThan18));

        System.out.println("Список людей со средним возрастом по именам: " + getAverageAgesByNames(personsList));
        printPeopleFrom20To45(personsList);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите количество чисел из которых необходимо взять корень:");
        int numbersCount = scanner.nextInt();

        printNumbersRoots(numbersCount);

        System.out.println("Введите количество чисел Фибоначчи:");
        int fibonacciNumbersCount = scanner.nextInt();

        printFibonacciNumbers(fibonacciNumbersCount);
    }

    public static void printUniqueNames(List<Person> personsList) {
        String names = getUniqueNames(personsList).stream()
                .collect(Collectors.joining(", ", "Имена: ", "."));

        System.out.println(names);
    }

    public static List<String> getUniqueNames(List<Person> personsList) {
        return personsList.stream()
                .map(Person::name)
                .distinct()
                .collect(Collectors.toList());
    }

    public static List<Person> getPeopleYoungerThan18(List<Person> personsList) {
        return personsList.stream()
                .filter(p -> p.age() < 18)
                .toList();
    }

    public static double getAveragePeopleAge(List<Person> personsList) {
        return personsList.stream()
                .mapToInt(Person::age)
                .average()
                .orElse(Double.NaN);
    }

    public static Map<String, Double> getAverageAgesByNames(List<Person> personsList) {
        return personsList.stream()
                .collect(Collectors.groupingBy(
                        Person::name,
                        Collectors.averagingInt(Person::age)
                ));
    }

    public static void printPeopleFrom20To45(List<Person> personsList) {
        List<String> peoplesNames = personsList.stream()
                .filter(x -> x.age() >= 20 && x.age() <= 45)
                .sorted(Comparator.comparingInt(Person::age).reversed())
                .map(Person::name)
                .toList();

        System.out.println("Список людей от 20 до 45 лет: " + peoplesNames);
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
                .iterate(new int[]{0, 1}, fibonacciNumbers -> new int[]{fibonacciNumbers[1], fibonacciNumbers[0] + fibonacciNumbers[1]})
                .map(fibonacciNumbers -> fibonacciNumbers[0])
                .limit(numbersCount)
                .forEach(System.out::println);
    }
}