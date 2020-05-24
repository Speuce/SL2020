package main.java.lucia.client.content.structures;

import java.util.ArrayList;

/**
 * An implementation of a binary search tree
 *
 * @author Brett Downey
 */
public class BinarySearchTree<T extends Comparable<T>> {

    /**
     * The size of this tree
     */
    private int size;

    /**
     * The root node
     */
    private Node root;

    /**
     * Creates a new binary search tree with an {@code null}
     * root and a size of 0. Therefore the tree is empty.
     */
    public BinarySearchTree() {
        size = 0;
        root = null;
    }

    /**
     * Inserts an item into the binary search tree,
     * if the root is null, it will create the root, else
     * it will find the proper position within the
     * tree for the given item using {@link Comparable}
     *
     * @param insert The item to add to the binary search tree
     */
    public void insert(T insert) {
        if(root == null) {
            root = new Node(insert);
            size++;
        } else {
            insertNonRoot(insert, root);
        }
    }

    /**
     * Gets the size of this binary search tree
     *
     * @return The size
     */
    public int getSize() {
        return size;
    }

    /**
     * Searches for the given item within this
     * binary search tree.
     *
     * @param item The item to search for
     * @return {@code false} if the item is not found,
     *         {@code true} otherwise.
     */
    public boolean search(T item) {
        return root != null && binarySearch(item, root);
    }

    /**
     * Returns the height of the BinarySearchTree, which is the
     * longest path from a root to a leaf node.
     *
     * @return The tree's height
     */
    public int height() {
        if(root == null) {
            return -1;
        } else {
            return findHeight(root, -1);
        }
    }

    /**
     * Performs an in order traversal of
     * the tree and returns the given array list with
     * the sorted elements
     */
    public ArrayList<T> inOrder() {
        ArrayList<T> sorted = new ArrayList<>(0);
        inOrderTraversal(root, sorted);
        return sorted;
    }

    /**
     * A recursive private function which inserts an item
     * into the binary search tree if the {@link Node} root
     * is @NotNull
     *
     * @param item The item to add to the binary search tree
     * @param current The current node within the tree that the recursive
     *                method is on
     */
    private void insertNonRoot(T item, Node current) {
        if(item.compareTo(current.getValue()) < 0) {
            // Less than, therefore goes to the left
            if(current.leftChild == null) {
                current.leftChild = new Node(item);
                size++;
            } else {
                insertNonRoot(item, current.leftChild);
            }
        } else {
            // Greater than or equal to, therefore goes to the right
            if(current.rightChild == null) {
                current.rightChild = new Node(item);
                size++;
            } else {
                insertNonRoot(item, current.rightChild);
            }
        }
    }

    /**
     * Preforms a binary search on this binary search tree.
     *
     * @param theItem The item to search for
     * @param current The current node within the tree that the recursive
     *                method is on
     * @return {@code false} if the item is not found,
     *         {@code true} otherwise.
     */
    private boolean binarySearch(T theItem, Node current) {
        int compareTo = theItem.compareTo(current.getValue());
        if(compareTo == 0) {
            /* The item has been found, return true */
            return true;
        } else if(compareTo < 0) {
            /* The item is less than, therefore it may be to the left */
            if(current.leftChild == null) {
                return false;
            } else {
                return binarySearch(theItem, current.leftChild);
            }
        } else {
            /* The item is greater than, therefore it may be to the right */
            if(current.rightChild == null) {
                return false;
            } else {
                return binarySearch(theItem, current.rightChild);
            }
        }
    }

    /**
     * Finds the height of the binary search tree recursively.
     *
     * @param current The current node within the tree that the recursive
     *                method is on
     * @return The height of the tree
     */
    private int findHeight(Node current, int depth) {
        if(current == null) {
            return depth;
        }
        depth++;
        int maxLeft = findHeight(current.leftChild, depth);
        int maxRight = findHeight(current.rightChild, depth);
        return Math.max(maxLeft, maxRight);
    }

    /**
     * An in order traversal of this binary search tree, which
     * adds the items to a sorted array list
     *
     * @param current The current node
     */
    private void inOrderTraversal(Node current, ArrayList<T> sorted) {
        if (current != null) {
            inOrderTraversal(current.leftChild(), sorted);
            sorted.add(current.getValue());
            inOrderTraversal(current.rightChild(), sorted);
        }
    }

    /**
     * A nested private class representing the Node
     * for this binary search tree
     */
    private class Node {

        private T value;

        private Node leftChild;

        private Node rightChild;

        /**
         * Create a node containing a String value
         *
         * @param value The value to give the node
         */
        public Node(T value) {
            this.value = value;
        }

        /**
         * Returns the value of this node
         *
         * @return The string value of this node
         */
        public T getValue() {
            return value;
        }

        /**
         * Returns the left child of this node
         *
         * @return The left child
         */
        public Node leftChild() {
            return leftChild;
        }

        /**
         * Returns the right child of this node
         *
         * @return The right child
         */
        public Node rightChild() {
            return rightChild;
        }
    }
}