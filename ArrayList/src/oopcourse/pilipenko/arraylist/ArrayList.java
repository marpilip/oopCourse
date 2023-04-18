package oopcourse.pilipenko.arraylist;

import java.util.*;

public class ArrayList<T> implements List<T> {
    private T[] array;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayList() {
        array = (T[]) new Object[DEFAULT_CAPACITY];
    }

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Размер массива должен быть больше 0. Размер : " + capacity);
        }

        array = (T[]) new Object[capacity];
    }

    public void ensureCapacity(int capacity) {
        if (capacity > size) {
            array = Arrays.copyOf(array, capacity);
        }
    }

    public void trimToSize() {
        if (array.length > size) {
            array = Arrays.copyOf(array, size);
        }
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
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                return true;
            }
        }

        return false;
    }

    private class ArrayListIterator implements Iterator<T> {
        private int currentIndex = -1;
        private int initialLength = array.length;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Следующего элемента нет");
            }

            if (initialLength != array.length) {
                throw new ConcurrentModificationException("Длина массива была изменена. Ожидаемая длина = " +
                        initialLength + ". Реальная = " + array.length);
            }

            ++currentIndex;
            return array[currentIndex];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            return (T1[]) Arrays.copyOf(array, size, a.getClass());
        }

        System.arraycopy(array, 0, a, 0, size);

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(T element) {
        ensureCapacity(size + 1);
        array[size++] = element;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (indexOf(o) >= 0 && contains(o)) {
            remove(indexOf(o));
            return true;
        }

        return false;
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
    public boolean addAll(Collection<? extends T> c) {
        ensureCapacity(size + c.size());

        for (T element : c) {
            array[size++] = element;
        }

        return !c.isEmpty();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и <= размера массива. Индекс = " + index
                    + " Размер массива = " + size);
        }

        if (c.size() == 0) {
            return false;
        }

        if (index == size) {
            return addAll(c);
        }

        ensureCapacity(size + c.size());

        for (int i = size - 1; i >= index; i--) {
            array[i + c.size()] = array[i];
        }

        int i = index;

        for (T element : c) {
            array[i++] = element;
        }

        size += c.size();

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Объект не существует");
        }

        boolean isRemoved = false;

        for (int i = 0; i < size; i++) {
            if (c.contains(array[i])) {
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
            throw new NullPointerException("Объект не существует");
        }

        boolean isRemoved = false;

        for (int i = 0; i < size; i++) {
            if (!c.contains(array[i])) {
                remove(i);
                i--;
                isRemoved = true;
            }
        }

        return isRemoved;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }

        size = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < размера массива. Индекс = " + index
                    + ". Размер массива = " + size);
        }

        return array[index];
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < размера массива. Индекс = " + index
                    + ". Размер массива = " + size);
        }

        T oldElement = array[index];
        array[index] = element;

        return oldElement;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < размера массива. Индекс = " + index
                    + ". Размер массива = " + size);
        }

        ensureCapacity(size + 1);
        System.arraycopy(array, index, array, index + 1, size - index);

        array[index] = element;
        size++;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс должен быть >= 0 и < размера массива. Индекс = " + index
                    + ". Размер массива = " + size);
        }

        T removedElement = array[index];

        if (index < size - 1) {
            System.arraycopy(array, index + 1, array, index, size - index - 1);
        }

        array[size - 1] = null;
        size--;

        return removedElement;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        }

        for (int i = size - 1; i >= 0; i--) {
            if (array[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
