package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.Sides;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import main.java.lucia.client.content.menu.item.descriptor.AddonDescriptor;

/**
 * Design Manager for Dinner Sides Selection in FXML
 */
public class SidesDesigns {
    private AddonDescriptor addonDescriptor; // information for the sides selection

    public SidesDesigns(AddonDescriptor addonDescriptor) {
        this.addonDescriptor = addonDescriptor;
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
      //  String hex = "#" + Integer.toHexString(addonDescriptor.getButtonColor().getRGB()).substring(2).toUpperCase();
    //    button.setStyle("-fx-font-size: 30; -fx-background-color: " + hex);
        //button.getStylesheets().add("..\\..\\..\\css\\main.css"); //todo
        button.setLayoutX(getX);
        button.setLayoutY(getY);
        button.setPrefSize(getSizeX, getSizeY);

        return button;
    }
}
