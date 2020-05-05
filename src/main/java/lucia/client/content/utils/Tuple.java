package main.java.lucia.client.content.utils;

/**
 * A class for an object holding 2 items
 * @author Matt Kwiatkowski
 */
public class Tuple<K, V> {

    //The first value
    private K val1;

    //The second value
    private V val2;

    public Tuple(K val1, V val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    public K getVal1() {
        return val1;
    }

    public void setVal1(K val1) {
        this.val1 = val1;
    }

    public V getVal2() {
        return val2;
    }

    public void setVal2(V val2) {
        this.val2 = val2;
    }

    public Tuple<K, V> deepCopy(){
        return new Tuple<K, V>(val1, val2);
    }
}
