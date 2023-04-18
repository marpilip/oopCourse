package oopcourse.pilipenko.matrix;

import oopcourse.pilipenko.vector.Vector;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount <= 0 || columnsCount <= 0) {
            throw new IllegalArgumentException("Размеры матрицы не могут быть <= 0. " +
                    "Количество строк = " + rowsCount + " Количество столбцов = " + columnsCount);
        }

        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) {
        int rowsCount = matrix.rows.length;
        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public Matrix(double[][] components) {
        if (components.length == 0 || components[0].length == 0) {
            throw new IllegalArgumentException("Количество строк должно быть больше 0. Количество строк = " + components.length);
        }

        int columnsCount = 0;

        for (double[] component : components) {
            if (component.length == 0) {
                columnsCount++;
            }
        }

        if (columnsCount == components.length){
            throw new IllegalArgumentException("Количество столбцов должно быть больше 0. Количество столбцов = " + components[0].length);
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
            rows[i] = new Vector(maxLength, components[i]);
        }
    }

    public Matrix(Vector[] rows) {
        if (rows.length == 0) {
            throw new IllegalArgumentException("Количество строк не может быть = 0");
        }

        int maxVectorSize = 0;

        for (Vector vector : rows) {
            if (vector.getSize() > maxVectorSize) {
                maxVectorSize = vector.getSize();
            }
        }

        this.rows = new Vector[rows.length];

        for (int i = 0; i < rows.length; i++) {
            Vector vector = new Vector(maxVectorSize);
            vector.add(rows[i]);
            this.rows[i] = vector;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (Vector vector : rows) {
            stringBuilder.append(vector).append(", ");
        }

        return stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append('}').toString();
    }

    public int getRowsCount() {
        return rows.length;
    }

    public int getColumnsCount() {
        return rows[0].getSize();
    }

    public Vector getRow(int index) {
        if (index < 0 || index >= rows.length) {
            throw new IndexOutOfBoundsException("Недопустимое значение индекса = " + index +
                    ". Индекс должен принимать значения от 0 до + " + (rows.length - 1));
        }

        return new Vector(rows[index]);
    }

    public void setRow(int index, Vector vector) {
        if (index < 0 || index >= rows.length) {
            throw new IndexOutOfBoundsException("Недопустимое значение индекса = " + index +
                    ". Индекс должен принимать значения от 0 до + " + (rows.length - 1));
        }

        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("Размерность вектора не равна количеству столбцов. Размер вектора = "
                    + vector.getSize() + ". Количество столбцов = " + getColumnsCount());
        }

        rows[index] = new Vector(vector);
    }

    public Vector getColumn(int index) {
        if (index < 0 || index >= getColumnsCount()) {
            throw new IndexOutOfBoundsException("Недопустимое значение индекса = " + index +
                    ". Индекс должен принимать значения от 0 до + " + (getColumnsCount() - 1));
        }

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
        if (rowIndex < 0 || rowIndex >= rows.length) {
            throw new IllegalArgumentException("Недопустимое значение индекса строки = " + rowIndex +
                    ". Индекс должен принимать значения от 0 до + " + (rows.length - 1));
        }

        if (columnIndex < 0 || columnIndex >= getColumnsCount()) {
            throw new IllegalArgumentException("Недопустимое значение индекса столбца = " + columnIndex +
                    ". Индекс должен принимать значения от 0 до + " + (getColumnsCount() - 1));
        }

        return rows[rowIndex].getComponent(columnIndex);
    }

    public double getDeterminant() {
        if (rows.length != getColumnsCount()) {
            throw new UnsupportedOperationException("У неквадратной матрицы определителя не существует. Количество строк: " + rows.length
                    + ". Количество столбцов = " + getColumnsCount());
        }

        if (rows.length == 1) {
            return rows[0].getComponent(0);
        }

        if (rows.length == 2) {
            return rows[0].getComponent(0) * rows[1].getComponent(1)
                    - rows[0].getComponent(1) * rows[1].getComponent(0);
        }

        double determinant = 0;

        for (int i = 0; i < rows.length; i++) {
            Matrix minorMatrix = getMinorMatrix(i);
            determinant += Math.pow(-1, i) * rows[0].getComponent(i) * minorMatrix.getDeterminant();
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
            throw new IllegalArgumentException("Размер вектора не равен количеству столбцов");
        }

        Vector resultVector = new Vector(rows.length);

        for (int i = 0; i < rows.length; i++) {
            double component = Vector.getScalarProduct(rows[i], vector);
            resultVector.setComponent(i, component);
        }

        return resultVector;
    }

    public void add(Matrix matrix) {
        checkSizeEquality(this, matrix);

        for (int i = 0; i < rows.length; i++) {
            rows[i].add(matrix.rows[i]);
        }
    }

    public void subtract(Matrix matrix) {
        checkSizeEquality(this, matrix);

        for (int i = 0; i < rows.length; i++) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    private static void checkSizeEquality(Matrix matrix1, Matrix matrix2) {
        if (matrix1.rows.length != matrix2.rows.length || matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("Матрицы разных размеров. "
                    + "Матрица 1: количество строк = " + matrix1.getRowsCount() + ", количество столбцов: = " + matrix1.getColumnsCount()
                    + ". Матрица 2: количество строк = " + matrix2.getRowsCount() + ", количество столбцов: = " + matrix2.getColumnsCount());
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
            throw new IllegalArgumentException("Число столбцов первой матрицы = " + matrix1.getColumnsCount() +
                    " не равно числу строк второй матрицы = " + matrix2.rows.length);
        }

        Vector[] resultRows = new Vector[matrix1.rows.length];
        double[] row = new double[matrix2.getColumnsCount()];

        for (int i = 0; i < matrix1.rows.length; i++) {
            for (int j = 0; j < matrix2.getColumnsCount(); j++) {
                row[j] = Vector.getScalarProduct(matrix1.rows[i], matrix2.getColumn(j));
            }

            resultRows[i] = new Vector(row);
        }

        return new Matrix(resultRows);
    }
}