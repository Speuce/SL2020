package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerItems;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import main.java.lucia.client.content.menu.item.descriptor.Descriptor;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Topping.ToppingCoordinates;

/**
 * Design Manager for Dinners in FXML
 */
public class DinnerDesigns {
    private Descriptor item; // information for the dinner selection
    private ToppingCoordinates toppingCoordinates; // information for the dinner coordinates
    private String defaultStyleString; //for css
    private String hoveredStyleString;
    private String selectedStyleString;


    public DinnerDesigns(Descriptor item) {
        this.item = item;
        toppingCoordinates = new ToppingCoordinates();

        defaultStyleString = "-fx-font-size: 25; -fx-background-color: " + item.getDefaultColor() +
                "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 25; -fx-text-fill: " + item.getTextColor() +
                "; -fx-font-family: 'Modern No. 20'";
        hoveredStyleString = "-fx-font-size: 25; -fx-background-color: " + item.getHoverColor() +
                "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 25; -fx-text-fill: " + item.getTextColor() +
                "; -fx-font-family: 'Modern No. 20'";
        selectedStyleString = "-fx-font-size: 25; -fx-background-color: " + item.getSelectedColor() +
                "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 25; -fx-text-fill: " + item.getTextColor() +
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
        button.setWrapText(true);
//        button.setTextFill(Paint.valueOf("white")); //todo
//        button.setFont(Font.font("Modern No. 20"));
        //button.getStyleClass().add("ToppingsDefault");
      //  String hex = "#" + Integer.toHexString(simpleItemDescriptor.getButtonColor().getRGB()).substring(2).toUpperCase();
     //   button.setStyle("-fx-font-size: 30; -fx-background-color: " + hex);
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
