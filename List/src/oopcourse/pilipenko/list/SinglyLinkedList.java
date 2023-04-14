package oopcourse.pilipenko.list;

import oopcourse.pilipenko.list_node.ListItem;

public class SinglyLinkedList<T> {
    private ListItem<T> head;
    private int size;

    public SinglyLinkedList() {
        head = null;
        size = 0;
    }

    public T getHead() {
        if (head == null) {
            throw new IllegalArgumentException("Список пустой");
        }

        return head.getData();
    }

    public int getSize() {
        return size;
    }

    public T getItem(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс должен быть больше или равен 0 и меньше размера = " + size
                    + ". Индекс = " + index);
        }

        ListItem<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return current.getData();
    }

    public T replaceItem(int index, T item) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс должен быть больше или равен 0 и меньше размера = " + size
                    + ". Индекс = " + index);
        }

        ListItem<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        T oldData = current.getData();
        current.setData(item);

        return oldData;
    }

    public T removeItem(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс должен быть больше или равен 0 и меньше размера = " + size
                    + ". Индекс = " + index);
        }

        T removedItem;

        if (index == 0) {
            removedItem = head.getData();
            head = head.getNext();
        } else {
            ListItem<T> current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }

            removedItem = current.getData();
            current.setNext(current.getNext().getNext());
        }

        size--;
        return removedItem;
    }

    public void addFirst(T item) {
        head = new ListItem<>(item, head);
        size++;
    }

    public void addItem(int index, T item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс должен быть больше или равен 0 и меньше размера = " + size
                    + ". Индекс = " + index);
        }

        if (index == 0) {
            addFirst(item);
        } else {
            ListItem<T> current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }

            current.setNext(new ListItem<>(item, current.getNext()));
            size++;
        }
    }

    public boolean deleteItem(T item) {
        if (head == null) {
            return false;
        }

        if (head.getData().equals(item)) {
            head = head.getNext();
            size--;
            return true;
        }

        ListItem<T> current = head;

        while (current.getNext() != null) {
            if (current.getNext().getData().equals(item)) {
                current.setNext(current.getNext().getNext());
                size--;
                return true;
            }

            current = current.getNext();
        }

        return false;
    }

    public T removeFirst() {
        if (head == null) {
            throw new IllegalArgumentException("Лист пустой");
        }

        T removedData = head.getData();
        head = head.getNext();
        size--;

        return removedData;
    }

    public SinglyLinkedList<T> copy() {
        SinglyLinkedList<T> copy = new SinglyLinkedList<>();
        ListItem<T> current = head;

        for (int i = 0; i < size; i++) {
            copy.addItem(i, current.getData());
            current = current.getNext();
        }

        return copy;
    }

    public void reverse() {
        ListItem<T> previous = null;
        ListItem<T> current = head;

        while (current != null) {
            ListItem<T> next = current.getNext();
            current.setNext(previous);
            previous = current;
            current = next;
        }

        head = previous;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        ListItem<T> current = head;

        while (current != null) {
            stringBuilder.append(current.getData()).append(", ");
            current = current.getNext();
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return stringBuilder.append("]").toString();
    }
}
