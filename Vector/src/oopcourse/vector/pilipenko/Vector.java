package oopcourse.vector.pilipenko;

import java.util.Arrays;

public class Vector {
    private double[] components;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Недопустимое значение: " + size + ". Размерность должна быть больше 0");
        }

        this.components = new double[size];
    }

    public Vector(Vector vector) {
        this.components = vector.components.clone();
    }

    public Vector(double[] components) {
        if (components.length == 0) {
            throw new IllegalArgumentException("Недопустимое значение: " + components.length + ". Размерность должна быть больше 0");
        }

        this.components = components.clone();
    }

    public Vector(int size, double[] components) {
        if (size <= 0) {
            throw new IllegalArgumentException("Недопустимое значение: " + size + ". Размерность должна быть больше 0");
        }

        this.components = Arrays.copyOf(components, size);
    }


    public int getSize() {
        return components.length;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (double component : components) {
            stringBuilder.append(component).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());

        return stringBuilder + "}";
    }

    public void add(Vector vector) {
        if (components.length < vector.getSize()) {
            components = Arrays.copyOf(components, vector.getSize());
        }

        for (int i = 0; i < vector.getSize(); i++) {
            this.components[i] += vector.components[i];
        }
    }

    public void subtract(Vector vector) {
        if (components.length < vector.getSize()) {
            components = Arrays.copyOf(components, vector.getSize());
        }

        for (int i = 0; i < vector.getSize(); i++) {
            this.components[i] -= vector.components[i];
        }
    }

    public void multiplyByScalar(double scalar) {
        for (int i = 0; i < components.length; i++) {
            components[i] *= scalar;
        }
    }

    public Vector deployRotation() {
        multiplyByScalar(-1);

        return this;
    }

    public double getLength() {
        double sum = 0;

        for (double e : components) {
            sum += e * e;
        }

        return Math.sqrt(sum);
    }

    public double getComponent(int index) {
        return components[index];
    }

    public void setComponent(int index, double component) {
        components[index] = component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Vector vector = (Vector) o;
        return Arrays.equals(components, vector.components);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(components);
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        vector1.add(vector2);

        return vector1;
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {
        vector1.subtract(vector2);

        return vector1;
    }

    public static double getProductByScalar(Vector vector1, Vector vector2) {
        double result = 0;

        for (int i = 0; i < Math.max(vector1.getSize(), vector2.getSize()); i++) {
            result += vector1.components[i] * vector2.components[i];
        }

        return result;
    }
}