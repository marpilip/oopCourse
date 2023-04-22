package oopcourse.pilipenko.list;

import java.util.NoSuchElementException;

public class SinglyLinkedList<T> {
    private ListItem<T> head;
    private int size;

    public SinglyLinkedList() {
    }

    public T getFirst() {
        if (head == null) {
            throw new NoSuchElementException("Список пустой");
        }

        return head.getData();
    }

    public int getSize() {
        return size;
    }

    public T get(int index) {
        checkIndex(index);

        ListItem<T> item = head;

        for (int i = 0; i < index; i++) {
            item = item.getNext();
        }

        return item.getData();
    }

    public T set(int index, T data) {
        checkIndex(index);

        ListItem<T> currentItem = head;

        for (int i = 0; i < index; i++) {
            currentItem = currentItem.getNext();
        }

        T oldData = currentItem.getData();
        currentItem.setData(data);

        return oldData;
    }

    public T removeByIndex(int index) {
        checkIndex(index);

        T removedData;

        if (index == 0) {
            return removeFirst();
        }

        ListItem<T> item = head;

        for (int i = 0; i < index - 1; i++) {
            item = item.getNext();
        }

        removedData = item.getNext().getData();
        item.setNext(item.getNext().getNext());

        size--;
        return removedData;
    }

    public void addFirst(T data) {
        head = new ListItem<>(data, head);
        size++;
    }

    public void add(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс должен быть больше 0 и меньше размера = " + size
                    + ". Индекс = " + index);
        }

        if (index == 0) {
            addFirst(data);
            return;
        }

        ListItem<T> currentItem = head;

        for (int i = 0; i < index - 1; i++) {
            currentItem = currentItem.getNext();
        }

        currentItem.setNext(new ListItem<>(data, currentItem.getNext()));
        size++;

    }

    public boolean removeByValue(T data) {
        if (head == null) {
            return false;
        }

        if (data == null && head.getData() == null) {
            head = head.getNext();
            size--;
            return true;
        }

        if (head.getData().equals(data)) {
            head = head.getNext();
            size--;
            return true;
        }

        ListItem<T> currentItem = head;

        while (currentItem.getNext() != null) {
            if (currentItem.getNext().getData().equals(data)) {
                currentItem.setNext(currentItem.getNext().getNext());
                size--;
                return true;
            }

            if (data == null && currentItem.getNext().getData() == null) {
                currentItem.setNext(currentItem.getNext().getNext());
                size--;
                return true;
            }

            currentItem = currentItem.getNext();
        }

        return false;
    }

    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("Лист пустой");
        }

        T removedData = head.getData();
        head = head.getNext();
        size--;

        return removedData;
    }

    public SinglyLinkedList<T> copy() {
        SinglyLinkedList<T> copy = new SinglyLinkedList<>();
        ListItem<T> currentItem = head;

        while (currentItem != null) {
            copy.addFirst(currentItem.getData());
            currentItem = currentItem.getNext();
        }

        return copy;
    }

    public void reverse() {
        ListItem<T> previousItem = null;
        ListItem<T> currentItem = head;

        while (currentItem != null) {
            ListItem<T> nextItem = currentItem.getNext();
            currentItem.setNext(previousItem);
            previousItem = currentItem;
            currentItem = nextItem;
        }

        head = previousItem;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        ListItem<T> item = head;

        while (item != null) {
            stringBuilder.append(item.getData()).append(", ");
            item = item.getNext();
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return stringBuilder.append(']').toString();
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс должен быть больше или равен 0 и меньше размера = " + size
                    + ". Индекс = " + index);
        }
    }
}