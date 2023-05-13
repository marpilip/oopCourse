package oopcourse.pilipenko.arraylist;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private E[] elements;
    private int capacity;
    private int elementsCount;
    private int changeCount;

    public ArrayList() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
    }

    public int getChangeCount() {
        return changeCount;
    }

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Вместимость ArrayList'a должна быть больше либо = 0. Вместимость: " + capacity);
        }

        elements = (E[]) new Object[capacity];
        this.capacity = capacity;
    }

    public void increaseCapacity(int capacity) {
        if (capacity > this.capacity) {
            elements = Arrays.copyOf(elements, capacity);
            this.capacity = capacity;
        }
    }

    public void increaseCapacity() {
        if (elements.length == 0) {
            increaseCapacity(DEFAULT_CAPACITY);
        }

        increaseCapacity(capacity * 2);
    }

    public void trimToSize() {
        if (elementsCount < capacity) {
            elements = Arrays.copyOf(elements, capacity);
            capacity = elementsCount;
        }
    }

    @Override
    public int size() {
        return elementsCount;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean isEmpty() {
        return elementsCount == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    private class ArrayListIterator implements Iterator<E> {
        private int currentIndex = -1;
        private final int initialElementsCount = changeCount;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < elementsCount;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Следующего элемента нет");
            }

            if (initialElementsCount != changeCount) {
                throw new ConcurrentModificationException("Количество изменений списка до использования итератора = " + initialElementsCount
                        + ". Во время прохода изменилось до = " + changeCount);
            }

            ++currentIndex;
            return elements[currentIndex];
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, elementsCount);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < elementsCount) {
            return (T[]) Arrays.copyOf(elements, elementsCount, a.getClass());
        }

        // noinspection SuspiciousSystemArraycopy
        System.arraycopy(elements, 0, a, 0, capacity);

        if (a.length > capacity) {
            a[capacity] = null;
        }

        return a;
    }

    @Override
    public boolean add(E element) {
        add(elementsCount, element);

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index == -1) {
            return false;
        }

        remove(index);
        changeCount++;

        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(elementsCount, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndex(index);

        if (c.isEmpty()) {
            return false;
        }

        increaseCapacity(capacity + c.size());

        if (elementsCount == index) {
            for (E element : c) {
                elements[elementsCount++] = element;
            }
        } else {
            int shiftCount = elementsCount - index;
            System.arraycopy(elements, index, elements, index + c.size(), shiftCount);

            int i = index;

            for (E element : c) {
                i++;
                elements[i] = element;
            }

            elementsCount += c.size();
        }

        changeCount++;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Переданная коллекция = null");
        }

        if (c.isEmpty()) {
            return false;
        }

        boolean isRemoved = false;

        for (int i = 0; i < elementsCount; i++) {
            if (c.contains(elements[i])) {
                remove(i);
                i--;
                isRemoved = true;
            }
        }

        changeCount++;

        return isRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isRemoved = false;

        for (int i = 0; i < elementsCount; i++) {
            if (!c.contains(elements[i])) {
                remove(i);
                i--;
                isRemoved = true;
            }
        }

        changeCount++;
        return isRemoved;
    }

    @Override
    public void clear() {
        if (isEmpty()) {
            return;
        }

        Arrays.fill(elements, null);

        changeCount++;
        elementsCount = 0;
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        return elements[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);

        E oldElement = elements[index];
        elements[index] = element;


        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index);

        if (capacity <= elementsCount) {
            increaseCapacity();
        }

        if (index == elementsCount) {
            elements[elementsCount++] = element;
        } else {
            System.arraycopy(elements, index, elements, index + 1, elementsCount - index);
            elements[index] = element;
            elementsCount++;
        }

        changeCount++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E removedElement = elements[index];

        if (index < elementsCount - 1) {
            System.arraycopy(elements, index + 1, elements, index, elementsCount - index - 1);
        }

        elements[elementsCount - 1] = null;
        elementsCount--;
        changeCount++;

        return removedElement;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= capacity) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < вместимости ArrayList. Индекс = " + index
                    + ". Вместимость ArrayList = " + capacity);
        }
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < elementsCount; i++) {
            if (Objects.equals(o, elements[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = elementsCount - 1; i >= 0; i--) {
            if (Objects.equals(o, elements[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public String toString() {
        if (elementsCount == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder(']');

        for (int i = 0; i < elementsCount; i++) {
            stringBuilder.append(elements[i]).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append(']');

        return stringBuilder.toString();
    }
}
