package oopcourse.pilipenko.tree;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<T>> {
    public TreeNode<T> root;
    private int size;

    public BinarySearchTree() {
    }

    public void insert(T data) {
        root = insert(root, data);
        size++;
    }

    private TreeNode<T> insert(TreeNode<T> node, T data) {
        if (node == null) {
            return new TreeNode<>(data);
        }

        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(insert(node.getLeft(), data));
        } else {
            node.setRight(insert(node.getRight(), data));
        }

        return node;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringRecursive(root, sb);
        return sb.toString();
    }

    private void toStringRecursive(TreeNode<T> current, StringBuilder sb) {
        if (current == null) {
            sb.append("null ");
            return;
        }

        sb.append(current);
    }

    public boolean search(T data) {
        return search(root, data);
    }

    private boolean search(TreeNode<T> root, T data) {
        if (root.getData().equals(data)) {
            return true;
        }

        if (data.compareTo(root.getData()) < 0) {
            if (root.getLeft() != null) {
                return search(root.getLeft(), data);
            }

            return false;
        }

        if (root.getRight() != null) {
            return search(root.getRight(), data);
        }

        return false;
    }

    public int getSize() {
        return size;
    }

    public boolean remove(T data) {
        if (!search(data)) {
            throw new NoSuchElementException("Элемента " + data + " нет в дереве");
        }

        if (root == null) {
            throw new NullPointerException("Узел не существует");
        }

        TreeNode<T> nodeToRemove = getNode(root, data);

        if (nodeToRemove == null) {
            return false;
        }

        if (nodeToRemove == root) {
            root = removeNode(nodeToRemove);
            size--;

            return true;
        }

        TreeNode<T> parent = getParent(data);

        if (parent.getLeft() == nodeToRemove) {
            parent.setLeft(removeNode(nodeToRemove));
        } else {
            parent.setRight(removeNode(nodeToRemove));
        }

        size--;
        return true;
    }

    private TreeNode<T> removeNode(TreeNode<T> nodeToRemove) {
        if (nodeToRemove.getLeft() == null && nodeToRemove.getRight() == null) {  // 1. list
            return null;
        }

        if (nodeToRemove.getLeft() == null || nodeToRemove.getRight() == null) { // 2. one child
            if (nodeToRemove.getRight() != null) {
                return nodeToRemove.getRight();
            }

            return nodeToRemove.getLeft();
        }

        TreeNode<T> minRight = nodeToRemove.getRight();  // 3. two child
        TreeNode<T> parentOfMinRight = nodeToRemove;

        while (minRight.getLeft() != null) {
            parentOfMinRight = minRight;
            minRight = minRight.getLeft();
        }

        nodeToRemove.setData(minRight.getData());

        if (parentOfMinRight == nodeToRemove) {
            nodeToRemove.setRight(minRight.getRight());
        } else {
            parentOfMinRight.setLeft(minRight.getRight());
        }

        return nodeToRemove;
    }

    private TreeNode<T> getParent(T data) {
        TreeNode<T> parent = null;
        TreeNode<T> node = root;

        while (node != null && !node.getData().equals(data)) {
            parent = node;

            if (data.compareTo(node.getData()) < 0) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }

        return parent;
    }

    public TreeNode<T> getNode(TreeNode<T> node, T data) {
        if (node == null || node.getData().equals(data)) {
            return node;
        }

        if (data.compareTo(node.getData()) < 0) {
            return getNode(node.getLeft(), data);
        }

        return getNode(node.getRight(), data);
    }

    public void breadthTraversal() {
        if (root == null) {
            return;
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode<T> currentNode = queue.poll();
            System.out.println("Node data = " + currentNode.getData());

            if (currentNode.getLeft() != null) {
                queue.offer(currentNode.getLeft());
            }

            if (currentNode.getRight() != null) {
                queue.offer(currentNode.getRight());
            }
        }
    }

    public void depthTraversalWithRecursion() {
        depthTraversalWithRecursion(root);
    }

    private void depthTraversalWithRecursion(TreeNode<T> root) {
        if (root == null) {
            return;
        }

        System.out.println("Node data = " + root.getData());

        depthTraversalWithRecursion(root.getLeft());
        depthTraversalWithRecursion(root.getRight());
    }

    public void depthTraversalWithoutRecursion() {
        if (root == null) {
            return;
        }

        Stack<TreeNode<T>> stack = new Stack<>();

        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode<T> currentNode = stack.pop();
            System.out.println("Node data = " + currentNode.getData());

            if (currentNode.getRight() != null) {
                stack.push(currentNode.getRight());
            }

            if (currentNode.getLeft()!= null){
                stack.push(currentNode.getLeft());
            }
        }
    }
}
