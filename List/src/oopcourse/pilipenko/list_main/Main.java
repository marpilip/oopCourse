package oopcourse.pilipenko.list_main;

import oopcourse.pilipenko.list.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> linkedList1 = new SinglyLinkedList<>();
        linkedList1.add(0, 31);
        linkedList1.add(1, 32);
        linkedList1.add(2, 11);
        linkedList1.add(3, 35);
        System.out.println("Linked linkedList1 после заполнения узлами: " + linkedList1);

        linkedList1.addFirst(10);
        System.out.println("Linked linkedList1 после добавления узла вперед: " + linkedList1);

        System.out.println("Linked linkedList1 size = " + linkedList1.getSize());

        System.out.println("Linked linkedList1 head = " + linkedList1.getFirst());

        System.out.println("Удаление первого узла = " + linkedList1.removeFirst() + "; Linked linkedList1 head = " + linkedList1.getFirst());
        System.out.println("Linked linkedList1: " + linkedList1);

        System.out.println("Удаление узла под индексом 2 = " + linkedList1.removeByIndex(2) + "; Linked linkedList1: " + linkedList1);

        System.out.println("Элемент узла под индексом 1 = " + linkedList1.get(1));

        System.out.println("Замена элемента узла под индексом 1 = " + linkedList1.set(1, 14)
                + "; Linked linkedList1: " + linkedList1);

        System.out.println("Попытка удалить элемент 13: " + linkedList1.removeByData(13));

        System.out.println("LinkedList1: " + linkedList1);
        System.out.println("Копия Linked linkedList1: " + linkedList1.copy());

        linkedList1.reverse();
        System.out.println("Разворот Linked linkedList1: " + linkedList1);

        SinglyLinkedList<String> linkedList2 = new SinglyLinkedList<>();
        System.out.println(linkedList2);
    }
}