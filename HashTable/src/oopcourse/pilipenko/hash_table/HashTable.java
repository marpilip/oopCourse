package oopcourse.pilipenko.hash_table;

import java.util.*;

public class HashTable<E> implements Collection<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private final ArrayList<E>[] lists;
    private int size;
    private int changeCount;

    public HashTable() {
        lists = new ArrayList[DEFAULT_CAPACITY];

        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            lists[i] = new ArrayList<>();
        }
    }

    public HashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Вместимость Hash Table должна быть > 0. Вместимость = " + capacity);
        }

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

    public int getChangeCount() {
        return changeCount;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        int index = getIndex(o);
        return lists[index].contains(o);
    }

    private class HashTableIterator implements Iterator<E> {
        private int currentIndex = -1;
        private Iterator<E> currentIterator;
        private int visitedElementCount;
        private final int changeCountBeforeIteratorPass = changeCount;

        @Override
        public boolean hasNext() {
            return visitedElementCount < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new IndexOutOfBoundsException("Нет больше элементов");
            }

            if (changeCount != changeCountBeforeIteratorPass) {
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

            visitedElementCount++;
            return currentIterator.next();
        }

        @Override
        public void remove() {
            if (currentIterator == null) {
                throw new IllegalStateException("Невозможно вызвать remove() перед вызовом next()");
            }

            try {
                currentIterator.remove();
                size--;
                visitedElementCount--;
            } catch (IllegalStateException e) {
                throw new IllegalStateException("Невозможно вызвать remove() больше одного раза перед вызовом next()");
            } catch (NullPointerException e) {
                throw new IllegalStateException("Невозможно вызвать remove() после того, как все элементы удалены");
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
            objects[i + 1] = element;
            i++;
        }

        return objects;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(lists, size, a.getClass());
        }

        int index = 0;

        for (ArrayList<E> list : lists) {
            for (E element : list) {
                a[index++] = (T) element;
            }
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
        changeCount++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = getIndex(o);

        if (lists[index].remove(o)) {
            size--;
            changeCount++;
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
            if (add(element)) {
                isChanged = true;
            }
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
            ArrayList<E> list = lists[i];
            if (lists[i] != null && list.retainAll(c)) {
                isRemoved = true;
                size -= list.size();
                if (lists[i].isEmpty()) {
                    lists[i] = null;
                }
            }
        }

        changeCount++;

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

        changeCount++;
        size = 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(lists);
    }
}
