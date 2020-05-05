package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerModules;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * Design Manager for Dinner Modules in FXML
 */
public class DinnerModuleDesigns {
    private String item; // information for the dinner selection
    private DinnerModuleCoordinates dinnerModuleCoordinates; // information for the dinner coordinates

    public DinnerModuleDesigns(String item) {
        this.item = item;
        dinnerModuleCoordinates = new DinnerModuleCoordinates();
    }

    /**
     *  Creates the initial designs for the button that is being created
     *
     *  Was originally in the SceneBuilder features,
     *  but is being moved from fxml features to java features
     *
     *  For original design components check SceneBuilder FXMLs
     *
     *
     *  In this case, many of the designs will be hard coded as there is no custom classes
     *      except the fact that the list can be updated
     */
    public JFXButton initButtonDesign(JFXButton button, int getX, int getY, int getSizeX, int getSizeY) {
        button.setCursor(Cursor.OPEN_HAND);
        button.setTextFill(Paint.valueOf("white")); //todo
        button.setFont(Font.font("Modern No. 20"));
        //button.getStyleClass().add("ToppingsDefault");
        //  String hex = "#" + Integer.toHexString(simpleItemDescriptor.getButtonColor().getRGB()).substring(2).toUpperCase();
        //   button.setStyle("-fx-font-size: 30; -fx-background-color: " + hex);
        //button.getStylesheets().add("..\\..\\..\\css\\main.css"); //todo
        button.setLayoutX(getX);
        button.setLayoutY(getY);
        button.setPrefSize(getSizeX, getSizeY);

        return button;
    }
}
