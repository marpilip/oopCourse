package oopcourse.vector.main.pilipenko;

import oopcourse.vector.pilipenko.Vector;

public class Main {
    public static void main(String[] args) {
        double[] components1 = {1, 3, 6};
        double[] components2 = {1, 2, 3};
        double[] components3 = {1, 2, 4, 5};

        Vector vector1 = new Vector(5, components1);
        Vector vector2 = new Vector(3, components2);
        Vector vector3 = new Vector(3, components3);
        Vector vector4 = new Vector(3);


        System.out.println(vector1);
        System.out.println(vector4);

        System.out.println("Vector 1: " + vector1);
        System.out.println("Vector 2: " + vector2);

        vector1.add(vector2);
        System.out.println("Sum with vector 1: " + vector1);

        vector1.subtract(vector2);
        System.out.println("Difference with vector 1: " + vector1);

        vector1.multiplyByScalar(5);
        System.out.println("Composition with vector 1: " + vector1);

        System.out.println();

        System.out.println("Rotation change: " + vector1.deployRotation());
        System.out.println("Length of vector 3: " + vector3.getLength());
        System.out.println(vector3);

        System.out.println();

        System.out.println("Component with index 1: " + vector3.getComponent(1));
        vector3.setComponent(1, 3);
        System.out.println("New component with index 1: " + vector3.getComponent(1));

        System.out.println();

        System.out.println("Vectors are equals: " + vector2.equals(vector3));

        System.out.println();

        System.out.println("Vector 1: " + vector2);
        System.out.println("Vector 2: " + vector3);
        System.out.println("Static method sum: " + Vector.getSum(vector2, vector3));
        System.out.println("Static method difference: " + Vector.getDifference(vector2, vector3));
        System.out.println("Static method scalar composition: " + Vector.getProductByScalar(vector2, vector3));
    }
}