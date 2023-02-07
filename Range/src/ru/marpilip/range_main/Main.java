package ru.marpilip.range_main;

import ru.marpilip.range.Range;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите число, с которого начинается диапазон:");
        double from = scanner.nextDouble();

        System.out.println("Введите число, которым заканчивается диапазон:");
        double to = scanner.nextDouble();

        Range range = new Range(from, to);

        System.out.println("Длина вашего диапазона = " + range.getLength());

        System.out.println("Введите число, которое хотите проверить на принадлежность диапазону:");
        double numberInRange = scanner.nextDouble();

        System.out.println("Число " + numberInRange + " принадлежит заданному диапазону? " + range.isInside(numberInRange));
    }
}