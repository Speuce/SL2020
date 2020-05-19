package main.java.lucia.client.content.structures;

import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.manager.impl.OrderManager;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;

/**
 * Custom Implemented Data type for tracking a current days' orders.
 * @author Matthew Kwiatkowski
 *
 * Theoretical Big-Oh runtimes of various operations in this data structure:
 * Add: Best: O(1) Worst: O(n) [this case occurs when the table needs to be resized].
 * Delete: O(1)
 * Contains: O(1)
 * Next: O(h/n) where h=number of elements, n=current maximum size
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
            int newSize = findNewSize(num);
            resizeTable(newSize);
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
        return new Iterator<Order>() {
            /**
             * The amount of elements pulled.
             */
            int found = 0;

            /**
             * The current index
             */
            int currIndex = -1;

            @Override
            public boolean hasNext() {
                return currIndex < max && found < size;
            }

            @Override
            public Order next() {
                currIndex++;
                while(currIndex < max && !lookupTable[currIndex]){
                    currIndex++;
                }
                if(currIndex < max){
                    return OrderManager.INSTANCE.getFromOrderNumber(currIndex);
                }
                return null;
            }
        };
    }

    @NotNull
    @Override
    public Order[] toArray() {
        return toArray(new Order[0]);
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(@NotNull T[] ts) {
        Order[] ret = new Order[size];
        int index = 0;
        for(Order o: this){
            ret[index] = o;
            index++;
        }
        return (T[]) ret;
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
        for(Order o: collection){
            add(o);
        }
        return true;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> collection) {
        collection.forEach(o ->{
            if(o instanceof Order){
                remove(o);
            }
        });
        return true;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> collection) {
        return true;
    }

    @Override
    public void clear() {
        for(int i = 0; i < max; i++){
            lookupTable[i] = false;
        }
        size = 0;
    }


}
