package main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Sauce;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import main.java.lucia.client.content.menu.pizza.Sauce;

public class SauceDesigns {
    private Sauce sauce;
    private SauceCoordinates sauceCoordinates;

    public SauceDesigns(Sauce sauce) {
        this.sauce = sauce;
        sauceCoordinates = new SauceCoordinates();
    }

    public JFXButton initButtonDesign(JFXButton button, int getX, int getY, int getSizeX, int getSizeY) {
        button.setCursor(Cursor.OPEN_HAND);
        button.setTextFill(Paint.valueOf("white")); //todo
        button.setFont(Font.font("Modern No. 20"));
        //button.getStyleClass().add("ToppingsDefault");

        //  String hex = "#" + Integer.toHexString(topping.getButtonColor().getRGB()).substring(2).toUpperCase();
        //  button.setStyle("-fx-font-size: 30; -fx-background-color: " + hex);

        //button.getStylesheets().add("..\\..\\..\\css\\main.css"); //todo
        button.setLayoutX(getX);
        button.setLayoutY(getY);
        button.setPrefSize(getSizeX, getSizeY);

        return button;
    }
}
