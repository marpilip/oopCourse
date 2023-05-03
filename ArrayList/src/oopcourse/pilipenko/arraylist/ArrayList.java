package oopcourse.pilipenko.arraylist;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private E[] elements;
    private int capacity;
    private int elementsCount;

    public ArrayList() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
    }

    public ArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Вместимоть ArrayList'a должна быть больше 0. Вместимость : " + capacity);
        }

        elements = (E[]) new Object[capacity];
        this.capacity = capacity;
    }

    public void ensureCapacity(int capacity) {
        if (capacity > this.capacity) {
            elements = Arrays.copyOf(elements, capacity);
            this.capacity = capacity;
        }
    }

    public void ensureCapacity() {
        ensureCapacity(capacity * 2);
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
        private final int initialElementsCount = elementsCount;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < capacity;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Следующего элемента нет");
            }

            if (initialElementsCount != elementsCount) {
                throw new ConcurrentModificationException("Количество элементов было изменено. Ожидаемое количество = " +
                        initialElementsCount + ". Реальное = " + elementsCount);
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
        return Arrays.copyOf(elements, capacity);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < capacity) {
            return (T1[]) Arrays.copyOf(elements, capacity, a.getClass());
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
        if (elementsCount >= capacity) {
            ensureCapacity();
        }

        elements[elementsCount] = element;
        elementsCount += 1;

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
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addAll(elementsCount, c);

        return !c.isEmpty();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndex(index);

        if (c.size() == 0) {
            return false;
        }

        ensureCapacity(capacity + c.size());

        if (elementsCount - index > 0) {
            System.arraycopy(elements, index, elements, index + c.size(), elementsCount - index);
        }

        System.arraycopy(c.toArray(), 0, elements, index, elementsCount - index);

        elementsCount += c.size();

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            return false;
        }

        boolean isRemoved = false;

        for (int i = 0; i < capacity; i++) {
            if (c.contains(elements[i])) {
                remove(i);
                i--;
                isRemoved = true;
            }
        }

        elementsCount -= c.size();

        return isRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isRemoved = false;

        for (int i = 0; i < capacity; i++) {
            if (!c.contains(elements[i])) {
                remove(i);
                i--;
                isRemoved = true;
                elementsCount--;
            }
        }


        return isRemoved;
    }

    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            elements[i] = null;
        }

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

        elementsCount++;

        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index);

        if (capacity <= elementsCount) {
            ensureCapacity();
        }

        System.arraycopy(elements, index, elements, index + 1, elementsCount - index);

        elements[index] = element;
        elementsCount++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E removedElement = elements[index];

        if (index < capacity - 1) {
            System.arraycopy(elements, index + 1, elements, index, capacity - index - 1);
        }

        elements[capacity - 1] = null;
        elementsCount--;

        return removedElement;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= capacity) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < вместимоти ArrayList. Индекс = " + index
                    + ". Вместимоть ArrayList = " + capacity);
        }
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < capacity; i++) {
            if (Objects.equals(o, elements[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = capacity - 1; i >= 0; i--) {
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

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("]");

        return stringBuilder.toString();
    }
}
