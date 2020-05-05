package main.java.lucia.Zach;

import javafx.scene.layout.Pane;

public class Test {
    public static void main(String[] args){
        Pane pane = new Pane();
        pane.setId("hi");
        changePane(pane);
        System.out.println("PANE TEST: " + pane.getId());
    }

    private static void changePane(Pane pane) {
        pane.setId("bye");
    }
}
