package oopcourse.pilipenko.matrix;

import oopcourse.pilipenko.vector.Vector;

import java.util.Arrays;

public class Matrix {
    double[][] matrix;

    public Matrix(int rows, int columns) {
        this.matrix = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public Matrix(Matrix matrix) {
        int rows = matrix.matrix.length;
        int columns = matrix.matrix[0].length;
        this.matrix = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.matrix[i][j] = matrix.matrix[i][j];
            }
        }
    }

    public Matrix(double[][] matrix) {
        int max = 0;

        for (double[] doubles : matrix) {
            if (doubles.length > max) {
                max = doubles.length;
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length < max) {
                matrix[i] = Arrays.copyOf(matrix[i], max);
            }
        }

        this.matrix = matrix.clone();
    }

    public Matrix(Vector[] vectors) {
        int rows = vectors.length;
        int columns = vectors[0].getSize();
        matrix = new double[rows][columns];

        for (int i = 0; i < vectors.length; i++) {
            for (int j = 0; j < vectors[i].getSize(); j++) {
                matrix[i][j] = vectors[i].getComponent(j);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        for (double[] doubles : matrix) {
            stringBuilder.append("{");

            for (double d : doubles) {
                stringBuilder.append(d).append(" ");
            }

            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length()).append("}, ");
        }

        return stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("}").toString();
    }

    public int getRows() {
        return matrix.length;
    }

    public int getColumns() {
        return matrix[0].length;
    }

    public Vector getRow(int index) {
        return new Vector(matrix[index]);
    }

    public void setRow(int index, Vector vector) {
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

    public void transposition() {
        double[][] tempMatrix = new double[matrix[0].length][matrix.length];

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
        if (matrix.length != matrix[0].length) {
            throw new IllegalArgumentException("У неквадратной матрицы определителя не существует");
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
            for (int j = 0; j < matrix[0].length; j++) {
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
        if (matrix.length == 0 || vector.getSize() == 0) {
            return new Vector(0);
        }

        if (matrix[0].length != vector.getSize()) {
            throw new IllegalArgumentException("Количество столбцов матрицы не равно длине вектора");
        }

        Vector resultVector = new Vector(matrix.length);

        for (int i = 0; i < matrix.length; i++) {
            double sum = 0;

            for (int j = 0; j < matrix[0].length; j++) {
                sum += matrix[i][j] * vector.getComponent(j);
            }

            resultVector.setComponent(i, sum);
        }

        return resultVector;
    }

    public void add(Matrix addMatrix) {
        if (matrix.length != addMatrix.matrix.length || matrix[0].length != addMatrix.matrix[0].length) {
            throw new IllegalArgumentException("Матрицы разных размеров, невозможно сложить");
        }

        for (int i = 0; i < this.matrix.length; i++) {
            setRow(i, Vector.getSum(getRow(i), addMatrix.getRow(i)));
        }
    }

    public void subtract(Matrix subtractMatrix) {
        if (matrix.length != subtractMatrix.matrix.length || matrix[0].length != subtractMatrix.matrix[0].length) {
            throw new IllegalArgumentException("Матрицы разных размеров, невозможно отнять");
        }

        for (int i = 0; i < matrix.length; i++) {
            setRow(i, Vector.getDifference(getRow(i), subtractMatrix.getRow(i)));
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        Matrix resultMatrix = new Matrix(matrix1);
        resultMatrix.add(matrix2);

        return resultMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        Matrix resultMatrix = new Matrix(matrix1);
        resultMatrix.subtract(matrix2);

        return resultMatrix;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.matrix[0].length != matrix2.matrix.length) {
            throw new IllegalArgumentException("Число столбцов первой матрицы не равно числу строк второй матрицы.");
        }

        Matrix resultMatrix = new Matrix(matrix1.matrix.length, matrix2.matrix[0].length);

        for (int i = 0; i < matrix1.matrix.length; i++) {
            for (int j = 0; j < matrix2.matrix[0].length; j++) {
                for (int k = 0; k < matrix2.matrix.length; k++) {
                    resultMatrix.matrix[i][j] += matrix1.matrix[i][k] * matrix2.matrix[k][j];
                }
            }
        }

        return resultMatrix;
    }
}