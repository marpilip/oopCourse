package oopcourse.pilipenko.hash_table;

import java.util.*;

public class HashTable<E> implements Collection<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private final int capacity;
    private final ArrayList<E>[] lists;
    private int size;

    public HashTable() {
        lists = new ArrayList[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;

        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            lists[i] = new ArrayList<>();
        }
    }

    public HashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Вместимость Hash Table должна быть > 0. Вместимость = " + capacity);
        }

        lists = new ArrayList[capacity];
        this.capacity = capacity;

        for (int i = 0; i < capacity; i++) {
            lists[i] = new ArrayList<>();
        }
    }

    private int getHashIndex(Object o) {
        if (o == null) {
            return 0;
        }

        return Math.abs(o.hashCode() % lists.length);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        int index = getHashIndex(o);
        return lists[index].contains(o);
    }

    private class HashTableIterator implements Iterator<E> {
        private int currentIndex = -1;
        private Iterator<E> currentIterator;
        private int visitedCount = 0;

        @Override
        public boolean hasNext() {
            return visitedCount < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new IndexOutOfBoundsException("Нет больше элементов");
            }

            if (currentIterator == null || !currentIterator.hasNext()) {
                currentIndex += 1;

                while (currentIndex < lists.length && (lists[currentIndex] == null || lists[currentIndex].isEmpty())) {
                    currentIndex += 1;
                }

                if (currentIndex < lists.length) {
                    currentIterator = lists[currentIndex].iterator();
                }
            }

            visitedCount += 1;
            return currentIterator.next();
        }

        public void remove() {
            currentIterator.remove();
            size--;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[size];
        int index = 0;

        for (E element : this) {
            if (element != null) {
                objects[index + 1] = element;
            }
        }

        return objects;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            return (T1[]) Arrays.copyOf(a, size, a.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(lists, 0, a, 0, capacity);

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(E element) {
        int index = getHashIndex(element);
        lists[index].add(element);
        size++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = getHashIndex(o);

        if (lists[index].remove(o)) {
            size--;
            return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E element : c) {
            add(element);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (size == 0) {
            return false;
        }

        boolean isRemoved = false;
        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            if (c.contains(iterator.next())) {
                iterator.remove();
                isRemoved = true;
            }
        }

        return isRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (size == 0) {
            return false;
        }

        boolean isRetained = false;

        for (ArrayList<E> list : lists) {
            if (list.retainAll(c)) {
                isRetained = true;
            }

            size -= list.size();
        }

        return isRetained;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        for (ArrayList<E> list : lists) {
            list.clear();
        }

        size = 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(lists);
    }
}
