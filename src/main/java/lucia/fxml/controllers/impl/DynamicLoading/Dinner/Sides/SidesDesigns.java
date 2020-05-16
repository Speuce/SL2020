package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.Sides;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import main.java.lucia.client.content.menu.item.descriptor.AddonDescriptor;

/**
 * Design Manager for Dinner Sides Selection in FXML
 */
public class SidesDesigns {
    private AddonDescriptor addonDescriptor; // information for the sides selection
    private String defaultStyleString; // for css
    private String hoveredStyleString;
    private String selectedStyleString;

    public SidesDesigns(AddonDescriptor addonDescriptor) {
        this.addonDescriptor = addonDescriptor;
        defaultStyleString = "-fx-font-size: 30; -fx-background-color: " + addonDescriptor.getDefaultColor() +
                "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 30; -fx-text-fill: " + addonDescriptor.getTextColor() +
                "; -fx-font-family: 'Modern No. 20'";
        hoveredStyleString = "-fx-font-size: 30; -fx-background-color: " + addonDescriptor.getHoverColor() +
                "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 30; -fx-text-fill: " + addonDescriptor.getTextColor() +
                "; -fx-font-family: 'Modern No. 20'";
        selectedStyleString = "-fx-font-size: 30; -fx-background-color: " + addonDescriptor.getSelectedColor() +
                "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 30; -fx-text-fill: " + addonDescriptor.getTextColor() +
                "; -fx-font-family: 'Modern No. 20'";
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
//        button.setTextFill(Paint.valueOf("white")); //todo
//        button.setFont(Font.font("Modern No. 20"));
        //button.getStyleClass().add("ToppingsDefault");
      //  String hex = "#" + Integer.toHexString(addonDescriptor.getButtonColor().getRGB()).substring(2).toUpperCase();
    //    button.setStyle("-fx-font-size: 30; -fx-background-color: " + hex);
        //button.getStylesheets().add("..\\..\\..\\css\\main.css"); //todo
        button.setStyle(defaultStyleString);
        button.setLayoutX(getX);
        button.setLayoutY(getY);
        button.setPrefSize(getSizeX, getSizeY);

        return button;
    }

    /**
     * GETTERS AND SETTERS
     *
     * for CSS strings
     */

    public String getDefaultStyleString() {
        return defaultStyleString;
    }

    public void setDefaultStyleString(String defaultStyleString) {
        this.defaultStyleString = defaultStyleString;
    }

    public String getHoveredStyleString() {
        return hoveredStyleString;
    }

    public void setHoveredStyleString(String hoveredStyleString) {
        this.hoveredStyleString = hoveredStyleString;
    }

    public String getSelectedStyleString() {
        return selectedStyleString;
    }

    public void setSelectedStyleString(String selectedStyleString) {
        this.selectedStyleString = selectedStyleString;
    }
}
