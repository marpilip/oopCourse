package oopcourse.pilipenko.tree_main;

import oopcourse.pilipenko.tree.BinarySearchTree;

import java.util.Arrays;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        binarySearchTree.insert(1);
        binarySearchTree.insert(2);
        binarySearchTree.insert(0);
        binarySearchTree.insert(545);
        binarySearchTree.insert(1923);
        binarySearchTree.insert(321);

        System.out.println("Дерево: " + binarySearchTree);

        System.out.println("Элемент 1 находится в дереве: " + binarySearchTree.contains(1));
        System.out.println("Элемент 5 находится в дереве: " + binarySearchTree.contains(5));
        System.out.println("Элемент 2 находится в дереве: " + binarySearchTree.contains(2));
        System.out.println("Элемент 0 находится в дереве: " + binarySearchTree.contains(0));
        System.out.println("Элемент 545 находится в дереве: " + binarySearchTree.contains(545));
        System.out.println("Элемент 1923 находится в дереве: " + binarySearchTree.contains(1923));
        System.out.println("Количество узлов в дереве: " + binarySearchTree.getSize());

        System.out.println("Удаление узла 545: " + binarySearchTree.remove(545));
        System.out.println("Дерево после удаления:");
        System.out.println(binarySearchTree);
        System.out.println("Количество узлов в дереве: " + binarySearchTree.getSize());

        binarySearchTree.insert(4);
        binarySearchTree.insert(2304);
        binarySearchTree.insert(-100);
        System.out.println(binarySearchTree);

        System.out.println("Обход с рекурсией в глубину:");
        binarySearchTree.traverseDepthWithRecursion(node -> System.out.print(node + "; "));
        System.out.println();

        System.out.println("Обход без рекурсии в глубину:");
        binarySearchTree.traverseDepth(node -> System.out.print(node + "; "));
        System.out.println();

        System.out.println("Обход в ширину:");
        binarySearchTree.traverseBreadth(node -> System.out.print(node + "; "));
    }
}