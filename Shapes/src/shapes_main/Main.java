package shapes_main;

import Shapes.Rectangle;
import Shapes.Shapes;
import Shapes.Circle;
import Shapes.Square;
import Shapes.Triangle;

import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Shapes[] shapes = {new Rectangle(1, 3), new Rectangle(1, 3), new Circle(4), new Circle(1), new Triangle(1, 4, 5, 67, 7, 6), new Square(10), new Square(10)};

        System.out.println("Максимальная фигура по площади является фигура с параметрами: " + getShapeMaxArea(shapes).toString());
        System.out.println();
        System.out.println("Вторая максимальная фигура по периметру является фигура с параметрами: " + getSecondShapeMaxPerimeter(shapes).toString());
    }

    public static Shapes getShapeMaxArea(Shapes[] shapes) {
        Arrays.sort(shapes, new SortShapesByArea());
        return shapes[0];
    }

    public static Shapes getSecondShapeMaxPerimeter(Shapes[] shapes) {
        Arrays.sort(shapes, new SortShapesByPerimeter());
        return shapes[1];
    }
}

class SortShapesByArea implements Comparator<Shapes> {

    @Override
    public int compare(Shapes o1, Shapes o2) {
        return Double.compare(o2.getArea(), o1.getArea());
    }
}

class SortShapesByPerimeter implements Comparator<Shapes> {

    @Override
    public int compare(Shapes o1, Shapes o2) {
        return Double.compare(o2.getPerimeter(), o1.getPerimeter());
    }
}
