package Pilipenko_shapes.shapes;

import Pilipenko_shapes.Shape;

public class Triangle implements Shape {
    private final double x1;
    private final double x2;
    private final double x3;
    private final double y1;
    private final double y2;
    private final double y3;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getX3() {
        return x3;
    }

    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }

    public double getY3() {
        return y3;
    }

    @Override
    public double getWidth() {
        return Math.max(Math.max(x1, x2), x3) - Math.min(x1, Math.min(x2, x3));
    }

    @Override
    public double getHeight() {
        return Math.max(Math.max(y1, y2), y3) - Math.min(y1, Math.min(y2, y3));
    }

    @Override
    public double getArea() {
        return 0.5 * Math.abs((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1));
    }

    @Override
    public double getPerimeter() {
        double sideLength12 = getSideLength(x1, y1, x2, y2);
        double sideLength23 = getSideLength(x2, y2, x3, y3);
        double sideLength31 = getSideLength(x3, y3, x1, y1);

        return sideLength12 + sideLength31 + sideLength23;
    }

    public double getSideLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    @Override
    public String toString() {
        return "Triangle: (" + x1 + ", " + y1 + "), " +
                "(" + x2 + ", " + y2 + "), " +
                "(" + x3 + ", " + y3 + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Triangle triangle = (Triangle) o;
        return triangle.x1 == x1 && triangle.y1 == y1 &&
                triangle.x2 == x2 && triangle.y2 == y2 &&
                triangle.x3 == x3 && triangle.y3 == y3;
    }

    @Override
    public int hashCode() {
        int hash = 2;
        return 31 * hash + Double.hashCode(x1) + Double.hashCode(y1) +
                Double.hashCode(x2) + Double.hashCode(y2) +
                Double.hashCode(x3) + Double.hashCode(y3);
    }
}