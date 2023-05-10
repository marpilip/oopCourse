package oopcourse.pilipenko.tree;

public class TreeNode<T extends Comparable<T>> {
    private TreeNode<T> left;
    private TreeNode<T> right;
    private T data;

    public TreeNode(T data){
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public int compareTo(TreeNode<T> other) {
        return data.compareTo(other.data);
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    public void setRight(TreeNode<T> right) {
        this.right = right;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node {data = ").append(data);

        if (left != null) {
            sb.append(", left = ").append(left);
        } else {
            sb.append(", left = ").append("null");
        }

        if (right != null) {
            sb.append(", right = ").append(right);
        } else {
            sb.append(", right = ").append("null");
        }

        sb.append("}");
        return sb.toString();
    }
}
