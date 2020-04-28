package main.java.lucia.fxml.controllers.impl.main.Utils;

public interface ParentController<T> {

    void close();

    Object getCurrentItem();

    default void open(){};

    void setParent(T o);
}
