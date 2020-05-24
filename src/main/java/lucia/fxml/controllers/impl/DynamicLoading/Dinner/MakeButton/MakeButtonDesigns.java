package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.MakeButton;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;

/**
 * Design Manager for Make Button in FXML
 */
public class MakeButtonDesigns {
    private MakeButtonCoordinates makeButtonCoordinates; // information for the make button coordinates
    private String defaultStyleString; //for css


    public MakeButtonDesigns() {
        makeButtonCoordinates = new MakeButtonCoordinates();

        defaultStyleString = "-fx-font-size: 39; -fx-background-color: #807f05" +
                "; -fx-background-radius: 20; fx-border-radius: 20; -fx-text-alignment: center;" +
                "-fx-alignment: center; -fx-font-size: 39; -fx-text-fill: white" +
                "; -fx-font-family: Century";
    }

    /**
     * Creates the initial designs for the button that is being created
     * <p>
     * Was originally in the SceneBuilder features,
     * but is being moved from fxml features to java features
     * <p>
     * For original design components check SceneBuilder FXMLs
     */
    public JFXButton initButtonDesign(JFXButton button, int getX, int getY, int getSizeX, int getSizeY) {
        button.setCursor(Cursor.OPEN_HAND);
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
}
