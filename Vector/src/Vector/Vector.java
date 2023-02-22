package Vector;

import java.util.Arrays;
import java.util.Objects;

public class Vector {
    int n;
    double[] components = new double[n];

    public Vector(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Размерность должна быть больше 0");
        }

        this.n = n;
        components = Arrays.copyOf(components, n);
    }

    public Vector(Vector vector) {
        n = vector.n;
        components = vector.components;
    }

    public Vector(double[] components) {
        this.components = components;
    }

    public Vector(int n, double[] components) {
        if (n < 0) {
            throw new IllegalArgumentException("Размерность должна быть больше 0");
        }

        if (components.length < n) {
            components = Arrays.copyOf(components, n);
        }

        this.n = n;
        this.components = components;
    }


    public int getSize() {
        return Math.max(n, components.length);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("{");
        String separator = ", ";

        for (int i = 0; i < getSize(); i++) {
            string.append(components[i]);

            if (i < getSize() - 1) {
                string.append(separator);
            }
        }

        return string + "}";
    }

    public Vector getSum(Vector vector) {
        if (this.getSize() < vector.getSize()) {
            this.components = Arrays.copyOf(this.components, vector.getSize());
        } else if (this.getSize() > vector.getSize()) {
            vector.components = Arrays.copyOf(vector.components, this.getSize());
        }

        for (int i = 0; i < this.getSize(); i++) {
            this.components[i] += vector.components[i];
        }

        return this;
    }

    public Vector getDifference(Vector vector) {
        if (this.getSize() < vector.getSize()) {
            this.components = Arrays.copyOf(this.components, vector.getSize());
        } else if (this.getSize() > vector.getSize()) {
            vector.components = Arrays.copyOf(vector.components, this.getSize());
        }

        for (int i = 0; i < this.getSize(); i++) {
            this.components[i] -= vector.components[i];
        }

        return this;
    }

    public Vector getScalarComposition(double scalar) {
        for (int i = 0; i < this.getSize(); i++) {
            this.components[i] *= scalar;
        }

        return this;
    }

    public Vector changeRotation() {
        for (int i = 0; i < this.getSize(); i++) {
            this.components[i] *= -1;
        }

        return this;
    }

    public double getLength() {
        double powSum = 0;
        for (int i = 0; i < this.getSize(); i++) {
            powSum += Math.pow(this.components[i], 2);
        }

        return Math.sqrt(Math.abs(powSum));
    }

    public double getComponent(int index) {
        return this.components[index];
    }

    public void setComponent(int index, double component) {
        this.components[index] = component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return n == vector.n && Arrays.equals(components, vector.components);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(n);
        result = 31 * result + Arrays.hashCode(components);
        return result;
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        if (vector1.getSize() < vector2.getSize()) {
            vector1.components = Arrays.copyOf(vector1.components, vector2.getSize());
        } else if (vector1.getSize() > vector2.getSize()) {
            vector2.components = Arrays.copyOf(vector2.components, vector1.getSize());
        }

        double[] components = new double[vector1.getSize()];

        for (int i = 0; i < components.length; i++) {
            components[i] = vector1.getComponent(i) + vector2.getComponent(i);
        }

        return new Vector(components);
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {
        if (vector1.getSize() < vector2.getSize()) {
            vector1.components = Arrays.copyOf(vector1.components, vector2.getSize());
        } else if (vector1.getSize() > vector2.getSize()) {
            vector2.components = Arrays.copyOf(vector2.components, vector1.getSize());
        }

        double[] components = new double[vector1.getSize()];

        for (int i = 0; i < components.length; i++) {
            components[i] = vector1.getComponent(i) - vector2.getComponent(i);
        }

        return new Vector(components);
    }

    public static Vector getScalarComposition(Vector vector1, Vector vector2) {
        if (vector1.getSize() < vector2.getSize()) {
            vector1.components = Arrays.copyOf(vector1.components, vector2.getSize());
        } else if (vector1.getSize() > vector2.getSize()) {
            vector2.components = Arrays.copyOf(vector2.components, vector1.getSize());
        }

        double[] components = new double[vector1.getSize()];

        for (int i = 0; i < components.length; i++) {
            components[i] = vector1.getComponent(i) * vector2.getComponent(i);
        }

        return new Vector(components);
    }
}