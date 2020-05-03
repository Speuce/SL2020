package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Special;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;

/**
 * Design Manager for Specialty Pizzas in FXML
 */
public class SpecialDesigns {
    private SpecialtyPizzaDescriptor special; // information for the specialty pizza
    private SpecialCoordinates specialCoordinates; // information for the specialty coordinates

    public SpecialDesigns(SpecialtyPizzaDescriptor special) {
        this.special = special;
        specialCoordinates = new SpecialCoordinates();
    }

    /**
     *  Creates the initial designs for the button that is being created
     *
     *  Was originally in the SceneBuilder features,
     *  but is being moved from fxml features to java features
     *
     *  For original design components check SceneBuilder FXMLs
     */
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
