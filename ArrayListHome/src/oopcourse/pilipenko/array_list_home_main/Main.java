package oopcourse.pilipenko.array_list_home_main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<String> strings = new ArrayList<>();
        String filePath = "D:\\Java IT\\table.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                strings.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }

        System.out.println("ArrayList из файла: " + strings);

        ArrayList<Integer> integers1 = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        ArrayList<Integer> integers2 = new ArrayList<>(Arrays.asList(0, 0, 2, 32, 4, 4, 4, 7));

        System.out.println("ArrayList: " + integers1);
        deleteEvenNumbers(integers1);
        System.out.println("ArrayList после удаления четных чисел: " + integers1);

        System.out.println("ArrayList: " + integers2);
        System.out.println("ArrayList без дубликатов: " + getWithoutDuplicateNumbers(integers2));
    }

    public static void deleteEvenNumbers(ArrayList<Integer> integers) {
        for (int i = 0; i < integers.size(); i++) {
            if (integers.get(i) % 2 == 0) {
                integers.remove(i);
                i--;
            }
        }
    }

    public static ArrayList<Integer> getWithoutDuplicateNumbers(ArrayList<Integer> integers) {
        ArrayList<Integer> integersWithoutDuplicateNumbers = new ArrayList<>(integers.size());

        for (Integer number : integers) {
            if (!integersWithoutDuplicateNumbers.contains(number)) {
                integersWithoutDuplicateNumbers.add(number);
            }
        }

        return integersWithoutDuplicateNumbers;
    }
}