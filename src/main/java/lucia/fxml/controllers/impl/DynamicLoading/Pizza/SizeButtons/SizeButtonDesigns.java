package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.SizeButtons;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;

/**
 * Design Manager for Size Buttons in FXML
 */
public class SizeButtonDesigns {
    private int button; // information for the button
    private SizeButtonCoordinates sizeButtonCoordinates; // information for the button coordinates
    public String defaultStyleString; // for css
    public String hoveredStyleString;
    public String selectedStyleString;

    public SizeButtonDesigns(int button) {
        this.button = button;
        sizeButtonCoordinates = new SizeButtonCoordinates();

        defaultStyleString = "-fx-font-size: 30; -fx-background-color: #400D0D" +
                "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 30; -fx-text-fill: white" +
                "; -fx-font-family: Century";
        hoveredStyleString = "-fx-font-size: 30; -fx-background-color: #401E1A" +
                "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 30; -fx-text-fill: white" +
                "; -fx-font-family: Century";
        selectedStyleString = "-fx-font-size: 30; -fx-background-color: #871406" +
                "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 30; -fx-text-fill: white" +
                "; -fx-font-family: Century";
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
        // button.setTextFill(Paint.valueOf("white")); //todo
        // button.setFont(Font.font("Modern No. 20"));
        //button.getStyleClass().add("ToppingsDefault");

        // String hex = "#" + Integer.toHexString(special.getButtonColor().getRGB()).substring(2).toUpperCase();
        button.setStyle(defaultStyleString);

        button.setLayoutX(getX);
        button.setLayoutY(getY);
        button.setPrefSize(getSizeX, getSizeY);

        return button;
    }

    /**
     * ACCESSORS and MODIFIERS
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

