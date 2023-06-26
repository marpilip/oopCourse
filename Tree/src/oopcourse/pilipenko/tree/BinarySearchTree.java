package oopcourse.pilipenko.tree;

import java.util.*;
import java.util.function.Consumer;

public class BinarySearchTree<E> {
    public TreeNode<E> root;
    private int size;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Comparator<E> comparator) {
    }

    public void insert(E data) {
        if (root == null) {
            root = new TreeNode<>(data);
            size++;
            return;
        }

        TreeNode<E> parent = null;
        TreeNode<E> currentNode = root;

        while (currentNode != null) {
            if (currentNode.compareTo(data) == 0) {
                return;
            }

            parent = currentNode;

            if (currentNode.compareTo(data) > 0) {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }
        }

        TreeNode<E> newNode = new TreeNode<>(data);

        if (parent.compareTo(data) > 0) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }

        size++;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode<E> node = queue.poll();

            if (node == null) {
                return "";
            }

            sb.append("Node: ").append(node.getData()).append(" [");

            if (node.getLeft() != null) {
                queue.offer(node.getLeft());
                sb.append("Left child: ").append(node.getLeft().getData());
            }

            if (node.getRight() != null) {
                queue.offer(node.getRight());
                sb.append(" Right child: ").append(node.getRight().getData());
            }

            sb.append(']');
            sb.append("\n");
        }

        return sb.toString();
    }

    public boolean contains(E data) {
        TreeNode<E> currentNode = root;

        while (currentNode != null) {
            if (currentNode.getData().equals(data)) {
                return true;
            }

            if (currentNode.compareTo(data) > 0) {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }
        }

        return false;
    }

    public int getSize() {
        return size;
    }

    public boolean remove(E data) {
        if (root == null) {
            return false;
        }

        TreeNode<E>[] nodeAndParent = getNodeAndParent(data);

        if (nodeAndParent == null) {
            return false;
        }

        TreeNode<E> nodeToRemove = nodeAndParent[0];

        if (nodeToRemove == root) {
            root = removeNode(nodeToRemove);
            size--;

            return true;
        }

        TreeNode<E> parent = nodeAndParent[1];

        if (parent.getLeft() == nodeToRemove) {
            parent.setLeft(removeNode(nodeToRemove));
        } else {
            parent.setRight(removeNode(nodeToRemove));
        }

        size--;
        return true;
    }

    private TreeNode<E> removeNode(TreeNode<E> nodeToRemove) {
        if (nodeToRemove.getLeft() == null && nodeToRemove.getRight() == null) {  // 1. list
            return null;
        }

        if (nodeToRemove.getLeft() == null || nodeToRemove.getRight() == null) { // 2. one child
            if (nodeToRemove.getRight() != null) {
                return nodeToRemove.getRight();
            }

            return nodeToRemove.getLeft();
        }

        TreeNode<E> minRightNode = nodeToRemove.getRight();  // 3. two child
        TreeNode<E> parentOfMinRightNode = nodeToRemove;

        while (minRightNode.getLeft() != null) {
            parentOfMinRightNode = minRightNode;
            minRightNode = minRightNode.getLeft();
        }

        if (parentOfMinRightNode != nodeToRemove) {
            parentOfMinRightNode.setLeft(minRightNode.getRight());
            minRightNode.setRight(nodeToRemove.getRight());
        }

        minRightNode.setLeft(nodeToRemove.getLeft());

        return minRightNode;
    }

    private TreeNode<E>[] getNodeAndParent(E data) {
        TreeNode<E> parent = null;
        TreeNode<E> node = root;

        if (root == null) {
            return null;
        }

        while (node != null && node.compareTo(data) != 0) {
            parent = node;

            if (node.compareTo(data) > 0) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }

        TreeNode<E>[] nodeAndParent = new TreeNode[2];
        nodeAndParent[0] = node;
        nodeAndParent[1] = parent;

        return nodeAndParent;
    }

    public void traverseBreadth(Consumer<E> consumer) {
        if (root == null) {
            return;
        }

        Queue<TreeNode<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode<E> currentNode = queue.poll();
            consumer.accept(currentNode.getData());

            if (currentNode.getLeft() != null) {
                queue.offer(currentNode.getLeft());
            }

            if (currentNode.getRight() != null) {
                queue.offer(currentNode.getRight());
            }
        }
    }

    public void traverseDepthWithRecursion(Consumer<E> consumer) {
        traverseDepthWithRecursion(root, consumer);
    }

    private void traverseDepthWithRecursion(TreeNode<E> node, Consumer<E> consumer) {
        if (node == null) {
            return;
        }

        consumer.accept(node.getData());

        traverseDepthWithRecursion(node.getLeft(), consumer);
        traverseDepthWithRecursion(node.getRight(), consumer);
    }

    public void traverseDepth(Consumer<E> consumer) {
        if (root == null) {
            return;
        }

        Deque<TreeNode<E>> deque = new ArrayDeque<>();

        deque.push(root);

        while (!deque.isEmpty()) {
            TreeNode<E> currentNode = deque.pop();
            consumer.accept(currentNode.getData());

            if (currentNode.getRight() != null) {
                deque.push(currentNode.getRight());
            }

            if (currentNode.getLeft() != null) {
                deque.push(currentNode.getLeft());
            }
        }
    }
}
