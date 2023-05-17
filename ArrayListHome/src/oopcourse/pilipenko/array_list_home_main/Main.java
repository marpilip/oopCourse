package oopcourse.pilipenko.array_list_home_main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> lines = new ArrayList<>();
        String filePath = "D:\\Java IT\\table.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }

        System.out.println("ArrayList из файла: " + lines);

        ArrayList<Integer> integers1 = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        ArrayList<Integer> integers2 = new ArrayList<>(Arrays.asList(0, 0, 2, 32, 4, 4, 4, 7));

        System.out.println("ArrayList: " + integers1);

        deleteEvenNumbers(integers1);
        System.out.println("ArrayList после удаления четных чисел: " + integers1);

        System.out.println("ArrayList: " + integers2);
        System.out.println("ArrayList без дубликатов: " + getIntegersWithoutDuplicates(integers2));
    }

    public static void deleteEvenNumbers(ArrayList<Integer> integers) {
        if (integers == null) {
            throw new NullPointerException("Список не может быть null");
        }

        for (int i = 0; i < integers.size(); i++) {
            if (integers.get(i) % 2 == 0) {
                integers.remove(i);
                i--;
            }
        }
    }

    public static ArrayList<Integer> getIntegersWithoutDuplicates(ArrayList<Integer> integers) {
        if (integers == null) {
            throw new NullPointerException("Список не может быть null");
        }

        ArrayList<Integer> integersWithoutDuplicates = new ArrayList<>(integers.size());

        for (Integer integer : integers) {
            if (!integersWithoutDuplicates.contains(integer)) {
                integersWithoutDuplicates.add(integer);
            }
        }

        return integersWithoutDuplicates;
    }
}