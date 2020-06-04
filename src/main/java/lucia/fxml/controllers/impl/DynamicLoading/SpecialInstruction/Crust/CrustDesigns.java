package main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Crust;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import main.java.lucia.client.content.menu.pizza.Crust;

/**
 * Design Manager for Crust Selection in FXML
 */
public class CrustDesigns {
    private Crust crust; // information for the crust selection
    private CrustCoordinates crustCoordinates; // information for the crust coordinates
    public String defaultStyleString; // for css
    public String hoveredStyleString;
    public String selectedStyleString;

    public CrustDesigns(Crust crust) {
        this.crust = crust;
        crustCoordinates = new CrustCoordinates();

        defaultStyleString = "-fx-font-size: 30; -fx-background-color: #D3D3D3 " +
                "; -fx-background-radius: 20; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 30; -fx-text-fill: white" +
                "; -fx-font-family: 'Modern No. 20'";
        hoveredStyleString = "-fx-font-size: 30; -fx-background-color: #2A2929" +
                "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 30; -fx-text-fill: white" +
                "; -fx-font-family: 'Modern No. 20'";
        selectedStyleString = "-fx-font-size: 30; -fx-background-color: #353535" +
                "; -fx-background-radius: 5; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 30; -fx-text-fill: white" +
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
