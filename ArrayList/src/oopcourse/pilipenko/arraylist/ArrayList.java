package oopcourse.pilipenko.arraylist;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private E[] elements;
    private int elementsCount;
    private int changesCount;

    public ArrayList() {
        //noinspection unchecked
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Вместимость ArrayList'a должна быть больше либо = 0. Вместимость: " + capacity);
        }

        //noinspection unchecked
        elements = (E[]) new Object[capacity];
    }

    public void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            elements = Arrays.copyOf(elements, capacity);
        }
    }

    public void ensureCapacity() {
        if (elements.length == 0) {
            //noinspection unchecked
            elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            ensureCapacity(elements.length * 2);
        }
    }

    public void trimToSize() {
        if (elementsCount < elements.length) {
            elements = Arrays.copyOf(elements, elementsCount);
        }
    }

    @Override
    public int size() {
        return elementsCount;
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
        private final int expectedChangesCount = changesCount;
        private int currentIndex = -1;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < elementsCount;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Следующего элемента нет");
            }

            if (expectedChangesCount != changesCount) {
                throw new ConcurrentModificationException("Список изменился во время прохода итератором");
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
            //noinspection unchecked
            return (T[]) Arrays.copyOf(elements, elementsCount, a.getClass());
        }

        // noinspection SuspiciousSystemArraycopy
        System.arraycopy(elements, 0, a, 0, elementsCount);
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

        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Переданная коллекция = null");
        }

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
        if (c == null) {
            throw new NullPointerException("Переданная коллекция = null");
        }

        checkIndexForAdd(index);

        if (c.isEmpty()) {
            return false;
        }

        ensureCapacity(elements.length + c.size());

        if (index != elementsCount) {
            int shift = elementsCount - index;
            System.arraycopy(elements, index, elements, index + c.size(), shift);
        }

        int i = index;

        for (E element : c) {
            i++;
            elements[i] = element;
        }

        elementsCount += c.size();
        changesCount++;

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

        return isRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Переданная коллекция = null");
        }

        boolean isRemoved = false;

        for (int i = 0; i < elementsCount; i++) {
            if (!c.contains(elements[i])) {
                remove(i);
                i--;
                isRemoved = true;
            }
        }

        return isRemoved;
    }

    @Override
    public void clear() {
        if (isEmpty()) {
            return;
        }

        Arrays.fill(elements, 0, elementsCount, null);

        changesCount++;
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
        checkIndexForAdd(index);

        if (elementsCount >= elements.length) {
            ensureCapacity();
        }

        if (index != elementsCount) {
            System.arraycopy(elements, index, elements, index + 1, elementsCount - index);
        }

        elements[index] = element;
        elementsCount++;
        changesCount++;
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
        changesCount++;

        return removedElement;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= elementsCount) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < вместимости ArrayList. Индекс = " + index
                    + ". Вместимость ArrayList = " + elementsCount);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > elementsCount) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и <= вместимости ArrayList. Индекс = " + index
                    + ". Вместимость ArrayList = " + elementsCount);
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

        StringBuilder stringBuilder = new StringBuilder("[");

        for (int i = 0; i < elementsCount; i++) {
            stringBuilder.append(elements[i]).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append(']');

        return stringBuilder.toString();
    }
}