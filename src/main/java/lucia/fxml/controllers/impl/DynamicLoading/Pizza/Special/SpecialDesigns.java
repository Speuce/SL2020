package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Special;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;

public class SpecialDesigns {
    private SpecialtyPizzaDescriptor special;
    private SpecialCoordinates specialCoordinates;

    public SpecialDesigns(SpecialtyPizzaDescriptor special) {
        this.special = special;
        specialCoordinates = new SpecialCoordinates();
    }

    public JFXButton initButtonDesign(JFXButton button, int getX, int getY, int getSizeX, int getSizeY) {
        button.setCursor(Cursor.OPEN_HAND);
        button.setTextFill(Paint.valueOf("white")); //todo
        button.setFont(Font.font("Modern No. 20"));
        //button.getStyleClass().add("ToppingsDefault");

       // String hex = "#" + Integer.toHexString(special.getButtonColor().getRGB()).substring(2).toUpperCase();
        // button.setStyle("-fx-font-size: 30; -fx-background-color: " + hex);

        button.setLayoutX(getX);
        button.setLayoutY(getY);
        button.setPrefSize(getSizeX, getSizeY);

        return button;
    }

}
