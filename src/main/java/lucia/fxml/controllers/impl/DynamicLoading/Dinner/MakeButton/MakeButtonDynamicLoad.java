package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.MakeButton;

import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.Pane;

/**
 * Dynamic Loading Manager for the Make Button in the FXML
 */
public class MakeButtonDynamicLoad {
    private Pane parentPane;
    private MakeButtonCoordinates mC = new MakeButtonCoordinates();

    public MakeButtonDynamicLoad(Pane parentPane) {
        this.parentPane = parentPane;
    }

    /**
     *  Loads the Make Button
     */
    public void createMakeButton() {
        JFXButton button = createButton(mC.getGetStartX(), mC.getGetStartY(), "Make", mC.getGetSizeX(), mC.getGetSizeY());
        parentPane.getChildren().add(button);
    }

    /**
     *  Creates the button accompanying the designs, coordinates, and listeners
     */
    private JFXButton createButton(int getX, int getY, String name, int getSizeX, int getSizeY) {
        JFXButton button = new JFXButton(name);
        MakeButtonDesigns makeButtonDesigns = new MakeButtonDesigns();
        MakeButtonListeners makeButtonListeners = new MakeButtonListeners(button);

        makeButtonDesigns.initButtonDesign(button, getX, getY, getSizeX, getSizeY); //todo check button = ...
        makeButtonListeners.setListeners(); // gets the pane at which the buttons are to be stored
        return button;
    }
}
