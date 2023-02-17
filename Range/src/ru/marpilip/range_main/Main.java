package ru.marpilip.range_main;

import ru.marpilip.range.Range;

public class Main {
    public static void main(String[] args) {
        Range range1 = new Range(10, 15);
        Range range2 = new Range(7, 14);

        System.out.println("Пересечение заданных интервалов:");

        if (range1.getIntersection(range1, range2) == null) {
            System.out.println("Пересечение пусто");
        } else {
            System.out.println("Пересечение: от " + range1.getIntersection(range1, range2).getFrom() + " до " + range1.getIntersection(range1, range2).getTo());
        }

        System.out.println("Объединение заданных интервалов:");

        if (range1.getCombining(range1, range2) == null) {
            System.out.println("Объединение пусто");
        } else {
            int i = 0;
            while (range1.getCombining(range1, range2)[i] != null) {
                System.out.println("Интервал " + (i + 1) + " : от " + range1.getCombining(range1, range2)[i].getFrom() + " до " + range1.getCombining(range1, range2)[i].getTo());
                i++;
            }
        }

        System.out.println("Разность заданных интервалов: ");

        if (range1.getDifference(range1, range2) == null) {
            System.out.println("Разность пуста");
        } else {
            int i = 0;
            while (range1.getDifference(range1, range2)[i] != null) {
                System.out.println("Интервал " + (i + 1) + " : от " + range1.getDifference(range1, range2)[i].getFrom() + " до " + range1.getDifference(range1, range2)[i].getTo());
                i++;
            }
        }
    }
}