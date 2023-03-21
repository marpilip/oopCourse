package oopcourse.pilipenko.matrix;

import oopcourse.pilipenko.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount <= 0 || columnsCount <= 0) {
            throw new IllegalArgumentException
                    ("Размеры матрицы не могут быть <= 0. Сейчас строки = " + rowsCount + " столбцы = " + columnsCount);
        }

       rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
           rows[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) { ////!!!!!!
        int rowsCount = matrix.rows.length;
        rows = new Vector[rowsCount];

        if (rowsCount == 0) {
            throw new IllegalArgumentException("Размеры матрицы не могут быть = 0");
        }

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public Matrix(double[][] components) {
        if (components.length == 0) {
            throw new IllegalArgumentException("Размеры матрицы не могут быть = 0");
        }

        int rowsCount = components.length;
        rows = new Vector[rowsCount];
        int maxLength = 0;

        for (double[] row : components) {
            if (row.length > maxLength) {
                maxLength = row.length;
            }
        }

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(Arrays.copyOf(components[i], maxLength));
        }
    }

    public Matrix(Vector[] vectors) {
        rows = new Vector[vectors.length];
        int maxVectorSize = 0;

        for (Vector vector : vectors) {
            if (vector.getSize() > maxVectorSize) {
                maxVectorSize = vector.getSize();
            }
        }

        for (int i = 0; i < vectors.length; i++) {
            double[] components = new double[maxVectorSize];

            for (int j = 0; j < vectors[i].getSize(); j++) {
                components[j] = vectors[i].getComponent(j);
            }

            components = Arrays.copyOf(components, maxVectorSize);
            rows[i] = new Vector(components);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (Vector vector : rows) {
            stringBuilder.append(vector).append(", ");
        }

        return stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("}").toString();
    }

    public int getRowsCount() {
        return rows.length;
    }

    public int getColumnsCount() {
        return rows[0].getSize();
    }

    public Vector getRow(int index) {
        if (index < 0 || index >= rows.length) {
            throw new IllegalArgumentException("Недопустимое значение индекса = " + index);
        }

        return new Vector(rows[index]);
    }

    public void setRow(int index, Vector vector) {
        if (index < 0 || index >= rows.length) {
            throw new IllegalArgumentException("Недопустимое значение индекса = " + index);
        }

        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException
                    ("Количество столбцов не равно размерности вектора. Размер вектора = "
                            + vector.getSize() + ". Количество столбцов = " + getColumnsCount());
        }

        rows[index] = vector;
    }

    public Vector getColumn(int index) {
        Vector vector = new Vector(rows.length);

        for (int i = 0; i < rows.length; i++) {
            vector.setComponent(i, rows[i].getComponent(index));
        }

        return vector;
    }

    public void transpose() {
        Vector[] columns = new Vector[getColumnsCount()];

        for (int i = 0; i < getColumnsCount(); i++) {
            columns[i] = getColumn(i);
        }

        rows = columns;
    }

    public void multiplyByScalar(int scalar) {
        for (Vector row : rows) {
            row.multiplyByScalar(scalar);
        }
    }

    private double getComponent(int rowIndex, int columnIndex) {
        return getRow(rowIndex).getComponent(columnIndex);
    }

    public double getDeterminant() {
        if (rows.length != getColumnsCount()) {
            throw new UnsupportedOperationException
                    ("У неквадратной матрицы определителя не существует. Количество столбцов: " + getColumnsCount()
                            + ". Количество строк = " + rows.length);
        }

        if (rows.length == 1) {
            return getComponent(0, 0);
        }

        if (rows.length == 2) {
            return getComponent(0, 0) * getComponent(1, 1)
                    - getComponent(0, 1) * getComponent(1, 0);
        }

        double determinant = 0;

        for (int i = 0; i < rows.length; i++) {
            Matrix minorMatrix = getMinorMatrix(i);
            determinant += Math.pow(-1, i) * getComponent(0, i) * minorMatrix.getDeterminant();
        }

        return determinant;
    }

    private Matrix getMinorMatrix(int columnToRemove) {
        Matrix minorMatrix = new Matrix(rows.length - 1, rows.length - 1);
        int rowIndex = 0;
        int columnIndex = 0;

        for (int i = 1; i < rows.length; i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                if (j != columnToRemove) {
                    minorMatrix.rows[rowIndex].setComponent(columnIndex, getComponent(i, j));
                    columnIndex++;
                }
            }

            rowIndex++;
            columnIndex = 0;
        }

        return minorMatrix;
    }

    public Vector multiplyByVector(Vector vector) {
        if (getColumnsCount() != vector.getSize()) {
            throw new IllegalArgumentException("Количество столбцов матрицы не равно длине вектора");
        }

        Vector resultVector = new Vector(rows.length);

        for (int i = 0; i < rows.length; i++) {
            double sum = 0;

            for (int j = 0; j < getColumnsCount(); j++) {
                sum += getComponent(i, j) * vector.getComponent(j);
            }

            resultVector.setComponent(i, sum);
        }

        return resultVector;
    }

    public void add(Matrix matrix) {
        checkSizeEquality(this, matrix);

        for (int i = 0; i < rows.length; i++) {
            setRow(i, Vector.getSum(this.getRow(i), matrix.getRow(i)));
        }
    }

    public void subtract(Matrix matrix) {
        checkSizeEquality(this, matrix);

        for (int i = 0; i < rows.length; i++) {
            setRow(i, Vector.getDifference(getRow(i), matrix.getRow(i)));
        }
    }

    public static void checkSizeEquality(Matrix matrix1, Matrix matrix2) {
        if (matrix1.rows.length != matrix2.rows.length || matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("Матрицы разных размеров. "
                    + "Матрица 1: количество столбцов = " + matrix1.getColumnsCount() + ", количество строк: = " + matrix1.getRowsCount()
                    + ". Матрица 2: количество столбцов = " + matrix2.getColumnsCount() + ", количество строк: = " + matrix2.getRowsCount());
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        checkSizeEquality(matrix1, matrix2);
        Matrix resultMatrix = new Matrix(matrix1);
        resultMatrix.add(matrix2);

        return resultMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        checkSizeEquality(matrix1, matrix2);
        Matrix resultMatrix = new Matrix(matrix1);
        resultMatrix.subtract(matrix2);

        return resultMatrix;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.rows.length) {
            throw new IllegalArgumentException("Число столбцов первой матрицы = " + matrix1.getColumnsCount() + " " +
                    "не равно числу строк второй матрицы = " + matrix2.rows.length);
        }

        Vector[] result = new Vector[matrix1.rows.length];

        for (int i = 0; i < matrix1.rows.length; i++) {
            double[] row = new double[matrix2.getColumnsCount()];

            for (int j = 0; j < matrix2.getColumnsCount(); j++) {
                double scalarProduct = Vector.getScalarProduct(matrix1.getRow(i), matrix2.getColumn(j));
                row[j] = scalarProduct;
            }

            result[i] = new Vector(row);
        }

        return new Matrix(result);
    }
}