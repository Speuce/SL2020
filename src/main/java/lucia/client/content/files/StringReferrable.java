package main.java.lucia.client.content.files;

public class StringReferrable<T> {

    /**
     * The String which will refer to this object in the reference map
     */
    private String referenceName;

    private T item;

    public StringReferrable(String referenceName, T item) {
        this.referenceName = referenceName;
        this.item = item;
    }

    public String getReferenceName(){
        return referenceName;
    }

    public T getItem(){
        return item;
    }
}
