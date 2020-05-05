package main.java.lucia.client.content.utils.structures;

/**
 * A class for an object holding 3 items
 * @author Matt Kwiatkowski
 */
public class Tuple3<K, V, T> {

    //The first value
    private K val1;

    //The second value
    private V val2;

    //the third value
    private T val3;

    public Tuple3(K val1, V val2, T val3) {
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
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

    public T getVal3() {
        return val3;
    }

    public void setVal3(T val3) {
        this.val3 = val3;
    }

    public Tuple3<K, V, T> deepCopy(){
        return new Tuple3<K, V, T>(val1, val2, val3);
    }
}
