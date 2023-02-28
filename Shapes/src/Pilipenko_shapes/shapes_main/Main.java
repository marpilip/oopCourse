package Pilipenko_shapes.shapes_main;

import Pilipenko_shapes.comparators.ShapeAreaComparator;
import Pilipenko_shapes.comparators.ShapePerimeterComparator;
import Pilipenko_shapes.shapes.Rectangle;
import Pilipenko_shapes.Shape;
import Pilipenko_shapes.shapes.Circle;
import Pilipenko_shapes.shapes.Square;
import Pilipenko_shapes.shapes.Triangle;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = {
                new Rectangle(1, 3),
                new Rectangle(1, 3),
                new Circle(4), new Circle(1),
                new Triangle(-101, 4, 5, 67, 7, 60),
                new Square(10),
                new Square(10)
        };

        System.out.println("Максимальная фигура по площади является фигура с параметрами: " + getShapeByMaxArea(shapes));
        System.out.println();
        System.out.println("Вторая максимальная фигура по периметру является фигура с параметрами: " + getShapeBySecondMaxPerimeterSize(shapes));
    }

    public static Shape getShapeByMaxArea(Shape[] shapes) {
        if (shapes.length == 0) {
            System.out.println("Массив пустой");
            return null;
        }

        Arrays.sort(shapes, new ShapeAreaComparator());
        return shapes[0];
    }

    public static Shape getShapeBySecondMaxPerimeterSize(Shape[] shapes) {
        if (shapes.length == 0) {
            System.out.println("Массив пустой");
            return null;
        }

        Arrays.sort(shapes, new ShapePerimeterComparator());
        return shapes[1];
    }
}