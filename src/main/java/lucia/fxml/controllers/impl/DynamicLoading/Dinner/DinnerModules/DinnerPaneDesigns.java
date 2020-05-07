package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerModules;

import javafx.scene.layout.Pane;

/**
 * Design Manager for Dinner Pane in FXML
 */
public class DinnerPaneDesigns {
    private Pane pane; // information for the dinner module
    private DinnerPaneCoordinates dinnerPaneCoordinates; // information for the dinner module coordinates
    private String paneStyleString;

    public DinnerPaneDesigns(Pane pane) {
        this.pane = pane;
        dinnerPaneCoordinates = new DinnerPaneCoordinates();
        paneStyleString = "-fx-background-color: #ADD8E6";
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
