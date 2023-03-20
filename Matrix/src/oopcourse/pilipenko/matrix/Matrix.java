package oopcourse.pilipenko.matrix;

import oopcourse.pilipenko.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private double[][] matrix;
    private Vector[] rowsCountVector; //matrix

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount <= 0 || columnsCount <= 0) {
            throw new IllegalArgumentException
                    ("Размеры матрицы не могут быть <= 0. Сейчас строки = " + rowsCount + " столбцы = " + columnsCount);
        }
        
        matrix = new double[rowsCount][columnsCount];
    }

    public Matrix(Matrix matrix) {
        int rowsCount = matrix.matrix.length;

        if (rowsCount == 0) {
            throw new IllegalArgumentException("Размеры матрицы не могут быть = 0");
        }

        int columnsCount = matrix.matrix[0].length;
        this.matrix = new double[rowsCount][columnsCount];

        for (int i = 0; i < rowsCount; i++) {
            this.matrix[i] = Arrays.copyOf(matrix.matrix[i], columnsCount);
        }
    }

    public Matrix(double[][] components) {
        if (components.length == 0) {
            throw new IllegalArgumentException("Размеры матрицы не могут быть = 0");
        }

        int maxLength = 0;

        for (double[] row : components) {
            if (row.length > maxLength) {
                maxLength = row.length;
            }
        }

        double[][] updatedComponents = components.clone();

        for (int i = 0; i < components.length; i++) {
            if (components[i].length < maxLength) {
                updatedComponents[i] = Arrays.copyOf(components[i], maxLength);
            }
        }

        this.matrix = updatedComponents;
    }

    public Matrix(Vector[] vectors) {
        int maxVectorSize = 0;

        for (Vector vector : vectors) {
            if (vector.getSize() > maxVectorSize) {
                maxVectorSize = vector.getSize();
            }
        }

        int rowsCount = vectors.length;
        int columnsCount = maxVectorSize;
        matrix = new double[rowsCount][columnsCount];

        for (int i = 0; i < vectors.length; i++) {
            for (int j = 0; j < vectors[i].getSize(); j++) {
                matrix[i][j] = vectors[i].getComponent(j);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        for (double[] row : matrix) {
            stringBuilder.append("{");

            for (double d : row) {
                stringBuilder.append(d).append(", ");
            }

            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("}, ");
        }

        return stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("}").toString();
    }

    public int getRowsCount() {
        return matrix.length;
    }

    public int getColumnsCount() {
        return matrix[0].length;
    }

    public Vector getRow(int index) {
        if (index < 0 || index >= matrix.length) {
            throw new IllegalArgumentException("Недопустимое значение индекса = " + index);
        }

        return new Vector(matrix[index]);
    }

    public void setRow(int index, Vector vector) {
        if (index < 0 || index >= matrix.length) {
            throw new IllegalArgumentException("Недопустимое значение индекса = " + index);
        }

        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException
                    ("Количество столбцов не равно размерности вектора. Размер вектора = "
                            + vector.getSize() + ". Количество столбцов = " + getColumnsCount());
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[index][j] = vector.getComponent(j);
            }
        }
    }

    public Vector getColumn(int index) {
        Vector vector = new Vector(matrix.length);

        for (int i = 0; i < matrix.length; i++) {
            vector.setComponent(i, matrix[i][index]);
        }

        return vector;
    }

    public void transpose() {
        double[][] tempMatrix = new double[getColumnsCount()][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                tempMatrix[j][i] = matrix[i][j];
            }
        }

        matrix = tempMatrix;
    }

    public void multiplyByScalar(int scalar) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] *= scalar;
            }
        }
    }

    public double getDeterminant() {
        if (matrix.length != getColumnsCount()) {
            throw new UnsupportedOperationException
                    ("У неквадратной матрицы определителя не существует. Количество столбцов: " + getColumnsCount()
                            + ". Количество строк = " + matrix.length);
        }

        if (matrix.length == 1) {
            return matrix[0][0];
        }

        if (matrix.length == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        double determinant = 0;

        for (int i = 0; i < matrix.length; i++) {
            Matrix minorMatrix = getMinorMatrix(i);
            determinant += Math.pow(-1, i) * matrix[0][i] * minorMatrix.getDeterminant(); // всегда нулевой столбец
        }

        return determinant;
    }

    private Matrix getMinorMatrix(int columnToRemove) {
        Matrix minorMatrix = new Matrix(matrix.length - 1, matrix.length - 1);
        int rowIndex = 0;
        int columnIndex = 0;

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                if (j != columnToRemove) {
                    minorMatrix.matrix[rowIndex][columnIndex] = matrix[i][j];
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

        Vector resultVector = new Vector(matrix.length);

        for (int i = 0; i < matrix.length; i++) {
            double sum = 0;

            for (int j = 0; j < getColumnsCount(); j++) {
                sum += matrix[i][j] * vector.getComponent(j);
            }

            resultVector.setComponent(i, sum);
        }

        return resultVector;
    }

    public void add(Matrix matrix) {
        checkSizeEquality(this, matrix);

        for (int i = 0; i < this.matrix.length; i++) {
            setRow(i, Vector.getSum(this.getRow(i), matrix.getRow(i)));
        }
    }

    public void subtract(Matrix matrix) {
        checkSizeEquality(this, matrix);

        for (int i = 0; i < this.matrix.length; i++) {
            setRow(i, Vector.getDifference(this.getRow(i), matrix.getRow(i)));
        }
    }

    public static void checkSizeEquality(Matrix matrix1, Matrix matrix2) {
        if (matrix1.matrix.length != matrix2.matrix.length || matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
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
        if (matrix1.getColumnsCount() != matrix2.matrix.length) {
            throw new IllegalArgumentException("Число столбцов первой матрицы = " + matrix1.getColumnsCount() + " " +
                    "не равно числу строк второй матрицы = " + matrix2.matrix.length);
        }

        Matrix resultMatrix = new Matrix(matrix1.matrix.length, matrix2.getColumnsCount());

        for (int i = 0; i < matrix1.matrix.length; i++) {
            for (int j = 0; j < matrix2.getColumnsCount(); j++) {
                for (int k = 0; k < matrix2.matrix.length; k++) {
                    resultMatrix.matrix[i][j] += matrix1.matrix[i][k] * matrix2.matrix[k][j];
                }
            }
        }

        return resultMatrix;
    }
}