package oopcourse.pilipenko.matrix_main;

import oopcourse.pilipenko.matrix.Matrix;
import oopcourse.pilipenko.vector.Vector;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(4, 5);
        System.out.println(matrix1);

        double[][] doubles1 = {{1, 2}, {4, 5, 6}};
        Matrix matrix2 = new Matrix(doubles1);
        System.out.println(matrix2);
        System.out.println("components: " + Arrays.deepToString(doubles1));

        double[] doubles2 = {2, 3, 4, 6};
        double[] doubles3 = {5, 6, 7, 8, 9};
        Vector vector1 = new Vector(doubles2);
        Vector vector2 = new Vector(doubles3);

        Vector[] vectors = {vector1, vector2};
        Matrix matrix3 = new Matrix(vectors);
        System.out.println("Матрица из векторов: " + matrix3);

        Matrix matrix4 = new Matrix(matrix2);
        System.out.println("Копия матрицы: " + matrix4);
        System.out.println();

        System.out.println("Количество столбцов = " + matrix3.getColumnsCount());
        System.out.println("Количество строк = " + matrix3.getRowsCount());
        System.out.println(matrix3.getRow(1));

        double[] doubles4 = {20, 25, 30, 4, 5};
        Vector vector3 = new Vector(doubles4);
        matrix3.setRow(1, vector3);
        System.out.println("После изменение строки под индексом 1: " + matrix3.getRow(1));
        System.out.println(matrix3);
        System.out.println("Столбец индекса 1: " + matrix3.getColumn(1));
        System.out.println();

        matrix3.transpose();
        System.out.println("После транспонирования: " + matrix3);

        matrix3.multiplyByScalar(-1);
        System.out.println("Умножение на скаляр -1: " + matrix3);

        Matrix matrix5 = new Matrix(doubles1);
        double[] doubles5 = {2, -3, 5};
        Vector vector = new Vector(doubles5);
        System.out.println("Матрица до умножения на вектор: " + matrix5);
        System.out.println("Матрица после умножения на вектор: " + matrix5.multiplyByVector(vector));
        System.out.println();

        double[][] doubles6 = {{1, 2, 5, 32}, {7, 5, 6, 12}, {7, 20, 9, 10}, {1, 2, 3, 4}};
        Matrix matrix6 = new Matrix(doubles6);
        System.out.println("Определитель матрицы = " + matrix6.getDeterminant());
        System.out.println();

        double[][] doubles7 = {{1, 2}, {4, 5, 6}};
        Matrix matrix7 = new Matrix(doubles7);
        double[][] doubles8 = {{4, 50, 61}, {10, 20, 30}};
        Matrix matrix8 = new Matrix(doubles8);

        System.out.println("Первая матрица: " + matrix7);
        System.out.println("Вторая матрица: " + matrix8);
        matrix7.add(matrix8);
        System.out.println("Первая матрица после добавления к ней второй: " + matrix7);
        matrix7.subtract(matrix8);
        System.out.println("Первая матрица после вычитания второй: " + matrix7);

        System.out.println("Сумма двух матриц: " + Matrix.getSum(matrix7, matrix8));
        System.out.println("Разница двух матриц: " + Matrix.getDifference(matrix7, matrix8));

        double[][] doubles9 = {{10, 15}, {13, 34}, {12, 14}};
        Matrix matrix9 = new Matrix(doubles9);
        System.out.println("Произведение двух матриц: " + Matrix.getProduct(matrix8, matrix9));
    }
}