package oopcourse.pilipenko.list_main;

import oopcourse.pilipenko.list.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> linkedList = new SinglyLinkedList<>();
        linkedList.add(0, 31);
        linkedList.add(1, 32);
        linkedList.add(2, 11);
        linkedList.add(3, 35);
        System.out.println("Linked list после заполнения узлами: " + linkedList);

        linkedList.addFirst(10);
        System.out.println("Linked list после добавления узла вперед: " + linkedList);

        System.out.println("Linked list size = " + linkedList.getSize());

        System.out.println("Linked list head = " + linkedList.getFirst());

        System.out.println("Удаление первого узла = " + linkedList.removeFirst() + "; Linked list head = " + linkedList.getFirst());
        System.out.println("Linked list: " + linkedList);

        System.out.println("Удаление узла под индексом 2 = " + linkedList.removeByIndex(2) + "; Linked list: " + linkedList);

        System.out.println("Элемент узла под индексом 1 = " + linkedList.get(1));

        System.out.println("Замена элемента узла под индексом 1 = " + linkedList.set(1, 14)
                + "; Linked list: " + linkedList);

        System.out.println("Попытка удалить элемент 13: " + linkedList.removeByValue(13));

        System.out.println("Копия Linked list: " + linkedList.copy());

        linkedList.reverse();
        System.out.println("Разворот Linked list: " + linkedList);

        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        System.out.println(list);
    }
}