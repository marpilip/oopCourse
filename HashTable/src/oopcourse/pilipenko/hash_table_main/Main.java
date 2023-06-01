package oopcourse.pilipenko.hash_table_main;

import oopcourse.pilipenko.hash_table.HashTable;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        HashTable<Integer> hashTable = new HashTable<>(10);

        hashTable.add(1);
        hashTable.add(2);
        System.out.println("Hash Table после добавления элементов: " + hashTable);

        hashTable.add(123);
        hashTable.add(23010);
        hashTable.add(12333);
        System.out.println("Hash Table после добавления элементов: " + hashTable);

        hashTable.add(1);
        hashTable.add(5684);
        hashTable.add(14);
        System.out.println("Hash Table после добавления элементов: " + hashTable);
        System.out.println("Hash Table size = " + hashTable.size());

        System.out.println("Попытка удаления элемента 1: " + hashTable.remove(1) + ". Hash Table после удаления = " + hashTable);

        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(2);
        integers.add(233);
        integers.add(123);
        System.out.println("Hash Table содержит все элементы коллекции integers: " + hashTable.containsAll(integers));

        System.out.println("Добавление элементы коллекции integers: " + hashTable.addAll(integers) + ". Hash Table: " + hashTable);
        System.out.println("Удаление элементы коллекции integers: " + hashTable.removeAll(integers) + ". Hash Table: " + hashTable);

        integers.add(2);
        integers.add(12333);
        integers.add(14);
        System.out.println("Integers: " + integers);
        System.out.println("Исключение элементов, которые не содержатся в коллекции integers: " + hashTable.retainAll(integers)
                + ". Hash Table: " + hashTable);

        hashTable.clear();
        System.out.println("Hash Table после удаления всех элементов: " + hashTable);
    }
}