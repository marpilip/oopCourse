package oopcourse.pilipenko.arrayListHome;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListHome {
    public ArrayList<Integer> numbers;

    public ArrayListHome(ArrayList<Integer> numbers) {
        this.numbers = numbers;
    }

    public void deleteEvenNumbers() {
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) % 2 == 0) {
                numbers.remove(i);
            }
        }
    }

    public ArrayList<Integer> removeDuplicateNumbers() {
        ArrayList<Integer> integers = new ArrayList<>();

        for (int number : numbers) {
            if (!integers.contains(number)) {
                integers.add(number);
            }
        }

        return integers;
    }

    @Override
    public String toString() {
        return numbers.toString();
    }
}