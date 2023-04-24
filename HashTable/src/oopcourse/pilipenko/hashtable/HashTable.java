package oopcourse.pilipenko.hashtable;

import java.util.*;

public class HashTable<T> implements Collection<T> {
    private int size;
    private ArrayList<T>[] table;
    private static final int DEFAULT_CAPACITY = 10;

    public HashTable() {
        table = new ArrayList[DEFAULT_CAPACITY];

        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            table[i] = new ArrayList<T>();
        }
    }

    public HashTable(int capacity) {
        table = new ArrayList[capacity];

        for (int i = 0; i < capacity; i++) {
            table[i] = new ArrayList<T>();
        }
    }

    public int hash(Object o) {
        return Math.abs(o.hashCode() % table.length);
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
        int index = hash(o);
        return table[index].contains(o);
    }

    private class HashTableIterator implements Iterator<T> {
        private int currentIndex = -1;
        private Iterator<T> currentIterator;

        @Override
        public boolean hasNext() {
            if (currentIterator == null && !currentIterator.hasNext()) {
                currentIndex++;

                while (currentIndex < size && table[currentIndex].isEmpty()) {
                    currentIndex++;
                }

                if (currentIndex < size) {
                    currentIterator = table[currentIndex].iterator();
                    return true;
                }

                return false;
            }

            return false;
        }

        @Override
        public T next() {
            if (currentIterator == null || !currentIterator.hasNext()) {
                throw new NoSuchElementException("Элемента не существует");
            }

            return currentIterator.next();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[size];
        int index = 0;

        for (int i = 0; i < size; i++) {
            if (table[i] != null) {
                objects[index++] = table[i];
            }
        }

        return objects;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            return ((T1[]) Arrays.copyOf(table, size, a.getClass()));
        }

        System.arraycopy(table, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(T t) {
        int index = hash(t);

        if (table[index].contains(t)) {
            return false;
        }

        table[index].add(t);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = hash(o);

        if (table[index].remove(o)) {
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
    public boolean addAll(Collection<? extends T> c) {
        boolean isAdded = false;
        for (T element : c) {
            if (add(element)) {
                isAdded = true;
            }
        }

        return isAdded;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isRemoved = false;
        for (Object element : c) {
            if (remove(element)) {
                isRemoved = true;
            }
        }

        return isRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isRetained = false;
        for (ArrayList<T> list : table) {
            if (list.retainAll(c)) {
                isRetained = true;
            }

            size = list.size();
        }

        return isRetained;
    }

    @Override
    public void clear() {
        for (ArrayList<T> list : table) {
            list.clear();
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(table);
    }
}
