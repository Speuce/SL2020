package main.java.lucia.client.structures;

import java.util.ArrayList;

/**
 * A heap which automatically sorts the given input
 * based on the given key. Instead of using {@link Comparable}
 * directly on the data object, we use a key that implements
 * Comparable so that our heap can sort objects
 * using different methods whenever required without
 * having to change the structure of the given
 * data object.
 *
 * @author  Brett Downey
 */
public class Heap<T, K extends Comparable<K>> {

    /**
     * The array list which holds our nodes
     */
    private ArrayList<Node> heapList;

    /**
     * The heap constructor which initializes the needed
     * values for our heap
     */
    public Heap() {
        heapList = new ArrayList<>();
    }

    /**
     * Checks whether this heap is empty or not
     *
     * @return {@code true} if it is empty, {@code false}
     *          otherwise
     */
    public boolean isEmpty() {
        return heapList.isEmpty();
    }

    /**
     * Inserts the given key into the heap
     *
     * @param key The given key
     * @return {@code true} if the insert was successful, {@code false} otherwise.
     */
    public boolean insert(T object, K key) {
        Node newNode = new Node(object, key);
        heapList.add(newNode);
        trickleUp(heapList.size());
        return true;
    }

    /**
     * Trickles up the given index, in order to place the index in the proper
     * sorted position within the list
     */
    private void trickleUp(int index) {
        int parent = index - 1;
        Node sort = heapList.get(index);

        while (index > 0 && heapList.get(parent).key.compareTo(sort.key) < 0) {
            heapList.set(index, heapList.get(parent));
            index = parent;
            parent = parent - 1;
        }
        heapList.set(index, sort);
    }

    /**
     * The remove function which assumes a
     * non-empty list
     *
     * @return The root
     */
    public Node remove() {
        Node root = heapList.get(0);
        heapList.set(0, heapList.get(heapList.size() - 1));
        trickleDown(0);
        return root;
    }

    /**
     * Trickle down the heap based on the given index's
     * values
     *
     * @param index The index to trickle down if required
     */
    private void trickleDown(int index) {
        int largerChild;
        Node root = heapList.get(index);
        while (index < heapList.size() / 2) { // While the node has atleast one child
            int leftChild = 2 * index + 1;
            int rightChild = leftChild + 1;
            // find larger child
            if (rightChild < heapList.size() && heapList.get(leftChild).key.compareTo(heapList.get(rightChild).key) < 0) {
                largerChild = rightChild;
            } else {
                largerChild = leftChild;
            }

            // root >= largerChild?
            if (root.key.compareTo(heapList.get(largerChild).key) >= 0) {
                break;
            }
            // shift child up
            heapList.set(index, heapList.get(largerChild));
            index = largerChild;
        }
        heapList.set(index, root);
    }

    /**
     * Replaces the current key for the given index
     * with the new given key, and reorders the list depending
     * on where the index now should be located
     *
     * @param index The index to change
     * @param newKey The new key to change to
     * @return {@code true} if the index was valid, {@code false}
     *         otherwise
     */
    public boolean change(int index, K newKey) {
        if (index < 0 || index >= heapList.size()) {
            return false;
        }

        K oldValue = heapList.get(index).key;
        heapList.get(index).setKey(newKey);

        if (oldValue.compareTo(newKey) < 0) {
            trickleUp(index);
        } else {
            trickleDown(index);
        }
        return true;
    }

    /**
     * A private nested inner class to represent the heap's nodes
     */
    private class Node {

        private T data;

        private K key;

        Node(T data, K key) {
            this.data = data;
            this.key = key;
        }

        public T getData() {
            return data;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }
    }
}