package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Topping;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import main.java.lucia.client.content.menu.item.descriptor.ToppingType;

/**
 * Design Manager for Topping Selection in FXML
 */
public class ToppingDesigns {
    private ToppingType topping; // information for the topping selection
    private ToppingCoordinates toppingCoordinates; // information for the topping coordinates
    private String defaultStyleString; // for css
    private String hoveredStyleString;
    private String selectedStyleString;

    public ToppingDesigns(ToppingType topping) {
        this.topping = topping;
        toppingCoordinates = new ToppingCoordinates();

        defaultStyleString = "-fx-font-size: 30; -fx-background-color: " + topping.getShortName() +
                "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 30; -fx-text-fill: " + topping.getTextColor() +
                "; -fx-font-family: 'Modern No. 20'";
        hoveredStyleString = "-fx-font-size: 30; -fx-background-color: " + topping.getHoverColor() +
                "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 30; -fx-text-fill: " + topping.getTextColor() +
                "; -fx-font-family: 'Modern No. 20'";
        selectedStyleString = "-fx-font-size: 30; -fx-background-color: " + topping.getSelectedColor() +
                "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 30; -fx-text-fill: " + topping.getTextColor() +
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
        //button.setFont(Font.font("'Modern No. 20'"));
        //button.getStyleClass().add("ToppingsDefault");
        button.setStyle(defaultStyleString);
        //button.getStylesheets().add("..\\..\\..\\css\\main.css"); //todo
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
