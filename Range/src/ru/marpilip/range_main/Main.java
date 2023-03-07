package ru.marpilip.range_main;

import ru.marpilip.range.Range;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Range range1 = new Range(5, 10);
        Range range2 = new Range(30, 40);

        System.out.println("Пересечение заданных интервалов:");

        Range rangesIntersection = range1.getIntersection(range2);
        System.out.println(rangesIntersection);

        System.out.println("Объединение заданных интервалов:");

        Range[] rangesUnion = range1.getUnion(range2);
        System.out.println(Arrays.toString(rangesUnion));

        System.out.println("Разность заданных интервалов:");

        Range[] rangesDifference = range1.getDifference(range2);
        System.out.println(Arrays.toString(rangesDifference));
    }
}
