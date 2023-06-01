package oopcourse.pilipenko.list;

import java.util.NoSuchElementException;
import java.util.Objects;

public class SinglyLinkedList<E> {
    private ListItem<E> head;
    private int size;

    public SinglyLinkedList() {
    }

    public E getFirst() {
        if (head == null) {
            throw new NoSuchElementException("Список пустой");
        }

        return head.getData();
    }

    public int getSize() {
        return size;
    }

    private ListItem<E> getItemByIndex(int index) {
        ListItem<E> item = head;

        for (int i = 0; i < index; i++) {
            item = item.getNext();
        }

        return item;
    }

    public E get(int index) {
        checkIndex(index);

        return getItemByIndex(index).getData();
    }

    public E set(int index, E data) {
        checkIndex(index);

        ListItem<E> currentItem = getItemByIndex(index);

        E oldData = currentItem.getData();
        currentItem.setData(data);

        return oldData;
    }

    public E removeByIndex(int index) {
        checkIndex(index);

        if (index == 0) {
            return removeFirst();
        }

        ListItem<E> item = getItemByIndex(index - 1);

        E removedData = item.getNext().getData();
        item.setNext(item.getNext().getNext());

        size--;
        return removedData;
    }

    public void addFirst(E data) {
        head = new ListItem<>(data, head);
        size++;
    }

    public void add(int index, E data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс должен быть больше либо равен 0 и меньше либо равен размера = " + size
                    + ". Индекс = " + index);
        }

        if (index == 0) {
            addFirst(data);
            return;
        }

        ListItem<E> previousItem = getItemByIndex(index - 1);
        ListItem<E> currentItem = previousItem.getNext();

        previousItem.setNext(new ListItem<>(data, currentItem));
        size++;
    }

    public boolean removeByData(E data) {
        if (head == null) {
            return false;
        }

        if (Objects.equals(data, head.getData())) {
            head = head.getNext();
            size--;
            return true;
        }

        ListItem<E> currentItem = head;

        while (currentItem.getNext() != null) {
            if (Objects.equals(currentItem.getNext().getData(), data)) {
                currentItem.setNext(currentItem.getNext().getNext());
                size--;
                return true;
            }

            currentItem = currentItem.getNext();
        }

        return false;
    }

    public E removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("Список пустой");
        }

        E removedData = head.getData();
        head = head.getNext();
        size--;

        return removedData;
    }

    public SinglyLinkedList<E> copy() {
        SinglyLinkedList<E> copy = new SinglyLinkedList<>();
        ListItem<E> currentItem = head;
        ListItem<E> newItem = new ListItem<>(currentItem.getData());
        copy.head = newItem;
        copy.size++;
        ListItem<E> lastNewItem = newItem;

        while (currentItem.getNext() != null) {
            currentItem = currentItem.getNext();
            newItem = new ListItem<>(currentItem.getData());

            lastNewItem.setNext(newItem);
            lastNewItem = newItem;
            copy.size++;
        }

        return copy;
    }

    public void reverse() {
        ListItem<E> previousItem = null;
        ListItem<E> currentItem = head;

        while (currentItem != null) {
            ListItem<E> nextItem = currentItem.getNext();
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
        ListItem<E> item = head;

        while (item != null) {
            stringBuilder.append(item.getData()).append(", ");
            item = item.getNext();
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return stringBuilder.append(']').toString();
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс должен быть больше или равен 0 и меньше " + size
                    + ". Индекс = " + index);
        }
    }
}