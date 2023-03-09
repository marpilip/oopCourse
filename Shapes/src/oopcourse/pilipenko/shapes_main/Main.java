package oopcourse.pilipenko.shapes_main;

import oopcourse.pilipenko.comparators.ShapeAreaComparator;
import oopcourse.pilipenko.comparators.ShapePerimeterComparator;
import oopcourse.pilipenko.shapes.Rectangle;
import oopcourse.pilipenko.shapes.Shape;
import oopcourse.pilipenko.shapes.Circle;
import oopcourse.pilipenko.shapes.Square;
import oopcourse.pilipenko.shapes.Triangle;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = {
                new Rectangle(1, 30),
                new Rectangle(10, 3),
                new Circle(4),
                new Circle(1),
                new Triangle(-101, 4, 5, 67, 7, 60),
                new Square(10),
                new Square(10)
        };

        System.out.println("Максимальная фигура по площади является фигура с параметрами: " + getShapeWithMaxArea(shapes));
        System.out.println();
        System.out.println("Вторая максимальная фигура по периметру является фигура с параметрами: " + getShapeWithSecondMaxPerimeter(shapes));
    }

    public static Shape getShapeWithMaxArea(Shape[] shapes) {
        if (shapes.length == 0) {
            throw new IllegalArgumentException("Массив пустой");
        }

        Arrays.sort(shapes, new ShapeAreaComparator());
        return shapes[shapes.length - 1];
    }

    public static Shape getShapeWithSecondMaxPerimeter(Shape[] shapes) {
        if (shapes.length == 0) {
            throw new IllegalArgumentException("Массив пустой");
        } else if (shapes.length == 1) {
            throw new IllegalArgumentException("Массив размера 1, нет второго объекта");
        }

        Arrays.sort(shapes, new ShapePerimeterComparator());
        return shapes[shapes.length - 2];
    }
}