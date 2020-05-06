package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Topping;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import javafx.scene.text.Font;
import main.java.lucia.client.content.menu.pizza.ToppingType;

/**
 * Design Manager for Topping Selection in FXML
 */
public class ToppingDesigns {
    private ToppingType topping; // information for the topping selection
    private ToppingCoordinates toppingCoordinates; // information for the topping coordinates

    public ToppingDesigns(ToppingType topping) {
        this.topping = topping;
        toppingCoordinates = new ToppingCoordinates();
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
        button.setFont(Font.font("Modern No. 20"));
        //button.getStyleClass().add("ToppingsDefault");
        button.setStyle("-fx-font-size: 30; -fx-background-color: " + topping.getShortName() +
                        "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                        "-fx-alignment: center; -fx-font-size: 30; -fx-text-fill: " + topping.getTextColor());
        //button.getStylesheets().add("..\\..\\..\\css\\main.css"); //todo
        button.setLayoutX(getX);
        button.setLayoutY(getY);
        button.setPrefSize(getSizeX, getSizeY);

        return button;
    }

}
