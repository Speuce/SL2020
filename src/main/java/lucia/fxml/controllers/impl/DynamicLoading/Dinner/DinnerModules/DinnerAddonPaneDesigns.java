package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerModules;

import javafx.scene.layout.Pane;

/**
 * Design Manager for Dinner Pane in FXML
 */
public class DinnerAddonPaneDesigns {
    private Pane pane; // information for the dinner module
    private DinnerAddonPaneCoordinates dinnerAddonPaneCoordinates; // information for the dinner addon coordinates
    private String paneStyleString;

    public DinnerAddonPaneDesigns(Pane pane) {
        this.pane = pane;
        dinnerAddonPaneCoordinates = new DinnerAddonPaneCoordinates();
        paneStyleString = "-fx-background-color: #000000";
    }

    /**
     *  Creates the initial designs for the pane that is being created
     *
     *  Was originally in the SceneBuilder features,
     *  but is being moved from fxml features to java features
     *
     *  For original design components check SceneBuilder FXMLs
     *
     *
     *  In this case, many of the designs will be hard coded as there is no custom classes
     *      except the fact that the list can be updated
     */
    public Pane initPaneDesign(Pane pane, int getX, int getY, int getSizeX, int getSizeY) {
        pane.setStyle(paneStyleString);
        pane.setLayoutX(getX);
        pane.setLayoutY(getY);
        pane.setPrefSize(getSizeX, getSizeY);

        return pane;
    }
}
