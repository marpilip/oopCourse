package oopcourse.pilipenko.tree;

import java.util.Comparator;

class TreeNode<E> {
    private TreeNode<E> left;
    private TreeNode<E> right;
    private E data;
    private Comparator<E> comparator;

    public TreeNode(E data) {
        this.data = data;
    }

    public TreeNode(E data, TreeNode<E> left, TreeNode<E> right, Comparator<E> comparator) {
        this.data = data;
        this.left = left;
        this.right = right;
        this.comparator = comparator;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public int compareTo(E other) {
        if (comparator != null) {
            return comparator.compare(data, other);
        }

        return ((Comparable<E>) data).compareTo(other);
    }

    public TreeNode<E> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<E> left) {
        this.left = left;
    }

    public TreeNode<E> getRight() {
        return right;
    }

    public void setRight(TreeNode<E> right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "TreeNode data = " + data;
    }
}
