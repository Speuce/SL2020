package main.java.lucia.client.content.structures;

import main.java.lucia.client.content.order.Order;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;

/**
 * Custom Implemented Data type for tracking a current days' orders.
 * @author Matthew Kwiatkowski
 */
public class OrderTable implements Collection<Order> {

    /**
     * A table of boolean values, indexed according to each order's order number,
     * representing if the given order is in the collection or not.
     */
    private boolean[] lookupTable;

    /**
     * The number of elements currently represented
     */
    private int size;

    /**
     * The current maximum size.
     */
    private int max;

    public OrderTable(int initMax){
        lookupTable = new boolean[initMax];
        size = 0;
        max = initMax;
    }

    /**
     * Make sure the table is big enough for the given order number.
     * @param num the order number to check
     */
    private void checkSize(int num){
        assert(lookupTable.length == max);
        if(max <= num){

        }
    }

    /**
     * Find an appropriate size for upgrading the table
     * @param num the number to insert into the table
     * @return the next size of the table that will include num
     */
    private int findNewSize(int num){
        int currSize = 2*max;
        while(currSize <= num){
            currSize *=2;
        }
        return currSize;
    }

    /**
     * Resizes the table to the given size.
     * @param newSize the new size of the table.
     */
    private void resizeTable(int newSize){
        boolean[] newTable = new boolean[newSize];
        if (max >= 0) System.arraycopy(lookupTable, 0, newTable, 0, max);
        max = newSize;
        lookupTable = newTable;
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
        if(o instanceof Order){
            return lookupTable[((Order)o).getOrderNumber()];
        }
        return false;
    }

    @NotNull
    @Override
    public Iterator<Order> iterator() {
        return null;
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] ts) {
        return null;
    }

    @Override
    public boolean add(Order item) {
        checkSize(item.getOrderNumber());
        if(!lookupTable[item.getOrderNumber()]){
            lookupTable[item.getOrderNumber()] = true;
            size++;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if(o instanceof Order){
            Order or = (Order) o;
            if(or.getOrderNumber() < max){
                if(lookupTable[or.getOrderNumber()]){
                    lookupTable[or.getOrderNumber()] = false;
                    size--;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Order> collection) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {
        for(int i = 0; i < max; i++){
            lookupTable[i] = false;
        }
        size = 0;
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("Equals method not defined.");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("hashcode method not defined.");
    }
}
