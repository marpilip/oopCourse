package oopcourse.pilipenko.list_main;

import oopcourse.pilipenko.list.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> linkedList = new SinglyLinkedList<>();
        linkedList.addItem(0, 31);
        linkedList.addItem(1, 32);
        linkedList.addItem(2, 11);
        linkedList.addItem(3, 35);
        System.out.println("Linked list после заполнения узлами :" + linkedList);

        linkedList.addFirst(10);
        System.out.println("Linked list после добавления узла вперед: " + linkedList);

        System.out.println("Linked list size = " + linkedList.getSize());

        System.out.println("Linked list head = " + linkedList.getHead());

        System.out.println("Удаление первого узла: = " + linkedList.removeFirst() + " Linked list: " + linkedList.getHead());

        System.out.println("Удаление узла под индексом 2 = " + linkedList.removeItem(2) + " Linked list: " + linkedList);

        System.out.println("Элемент узла под индексом 1 = " + linkedList.getItem(1));

        linkedList.replaceItem(1, 14);
        System.out.println("Linked list после замены элемента узла под индексом 1 = " + linkedList);

        System.out.println("Попытка удалить элемент 13: " + linkedList.deleteItem(13));

        System.out.println("Копия Linked list : " + linkedList.copy());

        linkedList.reverse();
        System.out.println("Разворот Linked list: " + linkedList);
    }
}