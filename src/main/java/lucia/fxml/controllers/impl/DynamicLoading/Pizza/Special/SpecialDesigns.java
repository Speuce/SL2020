package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Special;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;

/**
 * Design Manager for Specialty Pizzas in FXML
 */
public class SpecialDesigns {
    private SpecialtyPizzaDescriptor special; // information for the specialty pizza
    private SpecialCoordinates specialCoordinates; // information for the specialty coordinates
    public String defaultStyleString; // for css
    public String hoveredStyleString;
    public String selectedStyleString;

    public SpecialDesigns(SpecialtyPizzaDescriptor special) {
        this.special = special;
        specialCoordinates = new SpecialCoordinates();

        defaultStyleString = "-fx-font-size: 24; -fx-background-color: " + special.getDefaultColor() +
                "; -fx-background-radius: 15; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 24; -fx-text-fill: " + special.getTextColor() +
                "; -fx-font-family: Century";
        hoveredStyleString = "-fx-font-size: 24; -fx-background-color: " + special.getHoverColor() +
                "; -fx-background-radius: 15; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 24; -fx-text-fill: " + special.getTextColor() +
                "; -fx-font-family: Century";
        selectedStyleString = "-fx-font-size: 24; -fx-background-color: " + special.getSelectedColor() +
                "; -fx-background-radius: 15; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 24; -fx-text-fill: " + special.getTextColor() +
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
