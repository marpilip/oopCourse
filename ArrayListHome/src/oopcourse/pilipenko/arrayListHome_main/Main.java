package oopcourse.pilipenko.arrayListHome_main;

import oopcourse.pilipenko.arrayListHome.ArrayListHome;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<String> stringArrayList = new ArrayList<>();
        String file = "D:\\Java IT\\table.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                stringArrayList.add(line);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }

        System.out.println("ArrayList из файла: " + stringArrayList);

        ArrayList<Integer> numbers1 = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        ArrayList<Integer> numbers2 = new ArrayList<>(Arrays.asList(0, 0, 2, 32, 4, 4, 4, 7));
        ArrayListHome arrayListHome1 = new ArrayListHome(numbers1);
        System.out.println("ArrayList: " + arrayListHome1);
        arrayListHome1.deleteEvenNumbers();
        System.out.println("ArrayList после удаления четных чисел: " + arrayListHome1);

        ArrayListHome arrayListHome2 = new ArrayListHome(numbers2);
        System.out.println("ArrayList: " + arrayListHome2);
        System.out.println("ArrayList после удаления дубликатов: " + arrayListHome2.removeDuplicateNumbers());
    }
}