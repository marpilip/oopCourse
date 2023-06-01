package oopcourse.pilipenko.hash_table;

import java.util.*;

public class HashTable<E> implements Collection<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private final ArrayList<E>[] lists;
    private int size;
    private int changesCount;

    public HashTable() {
        //noinspection unchecked
        lists = new ArrayList[DEFAULT_CAPACITY];
    }

    public HashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Вместимость Hash Table должна быть > 0. Вместимость = " + capacity);
        }

        //noinspection unchecked
        lists = new ArrayList[capacity];
    }

    private int getIndex(Object o) {
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
        int index = getIndex(o);

        if (lists[index] == null) {
            return false;
        }

        return lists[index].contains(o);
    }

    private class HashTableIterator implements Iterator<E> {
        private final int changesCountBeforeIteratorPass = changesCount;
        private int currentIndex = -1;
        private Iterator<E> currentIterator;
        private int visitedElementsCount;

        @Override
        public boolean hasNext() {
            return visitedElementsCount < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Нет больше элементов");
            }

            if (changesCount != changesCountBeforeIteratorPass) {
                throw new ConcurrentModificationException("Произошли изменения во время прохода итератора");
            }

            if (currentIterator == null || !currentIterator.hasNext()) {
                currentIndex++;

                while (currentIndex < lists.length && (lists[currentIndex] == null || lists[currentIndex].isEmpty())) {
                    currentIndex++;
                }

                if (currentIndex < lists.length) {
                    currentIterator = lists[currentIndex].iterator();
                }
            }

            visitedElementsCount++;
            //noinspection DataFlowIssue
            return currentIterator.next();
        }

        @Override
        public void remove() {
            if (currentIterator == null) {
                throw new IllegalStateException("Невозможно вызвать remove() перед вызовом next()");
            }

            try {
                currentIterator.remove();
                changesCount++;
                size--;
                visitedElementsCount--;
            } catch (IllegalStateException e) {
                throw new IllegalStateException("Невозможно вызвать remove() больше одного раза перед вызовом next()");
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[size];
        int i = 0;

        for (E element : this) {
            objects[i] = element;
            i++;
        }

        return objects;
    }

    @Override
    public <T> T[] toArray(T[] a) throws ArrayIndexOutOfBoundsException {
        if (a.length < size) {
            //noinspection SingleStatementInBlock,unchecked
            return (T[]) Arrays.copyOf(toArray(), size, a.getClass());
        }

        int i = 0;

        for (E element : this) {
            //noinspection unchecked
            a[i + 1] = (T) element;
        }

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(E element) {
        int index = getIndex(element);

        if (lists[index] == null) {
            lists[index] = new ArrayList<>();
        }

        lists[index].add(element);
        size++;
        changesCount++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = getIndex(o);

        if (lists[index] != null && lists[index].remove(o)) {
            size--;
            changesCount++;
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
        boolean isChanged = false;

        for (E element : c) {
            add(element);
            isChanged = true;
        }

        return isChanged;
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

        boolean isRemoved = false;

        for (int i = 0; i < lists.length; i++) {

            if (lists[i] != null && lists[i].retainAll(c)) {
                isRemoved = true;
                size -= lists[i].size();

                if (lists[i].isEmpty()) {
                    lists[i] = null;
                }
            }
        }

        if (isRemoved) {
            changesCount++;
        }

        return isRemoved;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        for (ArrayList<E> list : lists) {
            if (list != null) {
                list.clear();
            }
        }

        changesCount++;
        size = 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(lists);
    }
}
