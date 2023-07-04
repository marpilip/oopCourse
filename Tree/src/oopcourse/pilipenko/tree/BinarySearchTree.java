package oopcourse.pilipenko.tree;

import java.util.*;
import java.util.function.Consumer;

public class BinarySearchTree<E> {
    public TreeNode<E> root;
    private int size;
    private Comparator<E> comparator;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public int compareTo(E data1, E data2) {
        if (comparator != null) {
            return comparator.compare(data1, data2);
        }

        if (data1 == null && data2 == null) {
            return 0;
        }

        if (data1 == null) {
            return -1;
        }

        if (data2 == null) {
            return 1;
        }

        return ((Comparable<E>) data1).compareTo(data2);
    }

    public void insert(E data) {
        if (root == null) {
            root = new TreeNode<>(data);
            size++;
            return;
        }

        TreeNode<E> currentNode = root;

        while (currentNode != null) {
            if (compareTo(currentNode.getData(), data) > 0) {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(new TreeNode<>(data));
                    size++;
                    return;
                }

                currentNode = currentNode.getLeft();
            } else {
                if (currentNode.getRight() == null) {
                    currentNode.setRight(new TreeNode<>(data));
                    size++;
                    return;
                }

                currentNode = currentNode.getRight();
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        traverseBreadth(data -> {
            sb.append("Node: ").append(data).append(" [");
            TreeNode<E> node = getNodeAndParent(data)[0];

            if (node != null) {
                if (node.getLeft() != null) {
                    sb.append("Left child: ").append(node.getLeft().getData());
                }

                if (node.getRight() != null) {
                    sb.append(" Right child: ").append(node.getRight().getData());
                }
            }

            sb.append(']');
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }

    public boolean contains(E data) {
        TreeNode<E> currentNode = root;

        while (currentNode != null) {
            if (currentNode.getData() == null && data == null) {
                return true;
            }

            int comparisonResult = compareTo(currentNode.getData(), data);

            if (comparisonResult == 0) {
                return true;
            }

            if (comparisonResult > 0) {
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
            //noinspection unchecked
            TreeNode<E>[] nodeAndParent = new TreeNode[2];
            nodeAndParent[0] = null;
            nodeAndParent[1] = null;

            return nodeAndParent;
        }

        while (node != null && compareTo(node.getData(), data) != 0) {
            parent = node;

            if (compareTo(node.getData(), data) > 0) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }

        if (node == null) {
            //noinspection unchecked
            TreeNode<E>[] nodeAndParent = new TreeNode[2];
            nodeAndParent[0] = null;
            nodeAndParent[1] = null;

            return nodeAndParent;
        }

        //noinspection unchecked
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
