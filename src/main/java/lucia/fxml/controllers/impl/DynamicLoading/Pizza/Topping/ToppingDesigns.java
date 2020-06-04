package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Topping;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.PizzaOrderManager;

/**
 * Design Manager for Topping Selection in FXML
 */
public class ToppingDesigns {
    private ToppingType topping; // information for the topping selection
    private ToppingCoordinates toppingCoordinates; // information for the topping coordinates
    public String defaultStyleString; // for css
    public String hoveredStyleString;
    public String selectedStyleString;
    public String selectedStyleStringEasy;
    public String selectedStyleStringExtra;
    public String selectedStyleStringXExtra;

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
        selectedStyleStringEasy = "-fx-font-size: 30; -fx-background-color: #807F05" +
                "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 30; -fx-text-fill: " + topping.getTextColor() +
                "; -fx-font-family: 'Modern No. 20'";
        selectedStyleStringExtra = "-fx-font-size: 30; -fx-background-color: #7A4507" +
                "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 30; -fx-text-fill: " + topping.getTextColor() +
                "; -fx-font-family: 'Modern No. 20'";
        selectedStyleStringXExtra = "-fx-font-size: 30; -fx-background-color: #7A0075" +
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

    /**
     * Checks if the given button has one of the selected style strings
     */
    public boolean isSelectedStyleString(JFXButton button) {
        return button.getStyle().equalsIgnoreCase(selectedStyleString) ||
                button.getStyle().equalsIgnoreCase(selectedStyleStringEasy) ||
                button.getStyle().equalsIgnoreCase(selectedStyleStringExtra) ||
                button.getStyle().equalsIgnoreCase(selectedStyleStringXExtra);
    }

    /**
     * Determines which selected style string will be used if it is easy -> extra
     */
    public String getSelectedStyleString() {
        if(PizzaOrderManager.getPizzaOrderInstance().easyToExtraOption == 1)
            return selectedStyleStringEasy;
        else if(PizzaOrderManager.getPizzaOrderInstance().easyToExtraOption == 3)
            return selectedStyleStringExtra;
        else if(PizzaOrderManager.getPizzaOrderInstance().easyToExtraOption == 4)
            return selectedStyleStringXExtra;

        return selectedStyleString;
    }

    public void setSelectedStyleString(String selectedStyleString) {
        this.selectedStyleString = selectedStyleString;
    }
}
