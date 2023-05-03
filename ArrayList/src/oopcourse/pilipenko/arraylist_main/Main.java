package oopcourse.pilipenko.arraylist_main;

import oopcourse.pilipenko.arraylist.ArrayList;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> strings1 = new ArrayList<>();
        System.out.println("ArrayList после добавления элементов: " + strings1);
        strings1.add("a");
        strings1.add("b");
        strings1.add("c");
        strings1.add("d");
        strings1.add(1, "1");
        System.out.println("ArrayList после добавления элементов: " + strings1);

        System.out.println("Размер ArrayList = " + strings1.size());

        System.out.println("ArrayList содержит элемент \"с\": " + strings1.contains("c"));

        System.out.println("ArrayList после удаления элемента \"с\": " + strings1.remove("c") + " : " + strings1);

        ArrayList<String> strings2 = new ArrayList<>(4);
        strings2.add("c");
        strings2.add("d");
        strings2.add("e");
        strings2.add("f");
        System.out.println("ArrayList2 после добавления элементов: " + strings2);

        System.out.println("ArrayList содержит все элементы ArrayList2: " + strings1.containsAll(strings2));
        System.out.println("ArrayList после добавления элементов коллекции ArrayList2: " + strings1.addAll(strings2) + " : " + strings1);
        System.out.println("ArrayList после удаления элементов, содержащихся в коллекции ArrayList2: " + strings1.removeAll(strings2) + " : " + strings1);
        System.out.println("ArrayList после добавления элементов коллекции ArrayList2 на позицию под индексом 1: " + strings1.addAll(1, strings2)
                + " : " + strings1);

        strings1.add("j");
        strings1.add("h");
        strings1.add("i");
        System.out.println("ArrayList: " + strings1);
        System.out.println("ArrayList после удаления всех элементов, которые не содержатся в коллекции ArrayList2: " + strings1.retainAll(strings2)
                + " : " + strings1);

        strings1.trimToSize();
        System.out.println("ArrayList после уменьшения размера до количества элементов: " + strings1);

        strings1.clear();
        System.out.println("ArrayList после удаления всех элементов: " + strings1);

        System.out.println("ArrayList2 элемент под индексом 1: " + strings2.get(1));
        System.out.println("ArrayList2 после замены элемента под индексом 1 на \"g\": " + strings2.set(1, "g") + " : " + strings2);

        strings2.add(2, "r");
        System.out.println("ArrayList2 после добавления элемента под индексом 2: " + strings2);
        System.out.println("ArrayList2 после удаления элемента под индексом 2:" + strings2.remove(2) + " : " + strings2);

        System.out.println("ArrayList2 индекс элемента \"g\" = " + strings2.indexOf("g"));
        strings2.add(3, "g");
        System.out.println("ArrayList2: " + strings2);
        System.out.println("ArrayList2 индекс последнего элемента \"g\" = " + strings2.lastIndexOf("g"));
    }
}