package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerModules;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import main.java.lucia.consts.FoodConstants.Dinner.DinnerLineConstants;

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
        paneStyleString = "-fx-background-color:  #585858";
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
    public void initPaneDesign(Pane pane, int getX, int getY, int getSizeX, int getSizeY) {
        pane.setStyle(paneStyleString);
        pane.setLayoutX(getX);
        pane.setLayoutY(getY);
        pane.setPrefSize(getSizeX, getSizeY);
        buildLineBorder(pane);
    }

    private void buildLineBorder(Pane pane) {
        DinnerLineConstants dinnerLineConstants = new DinnerLineConstants();
        Line line = new Line();
        line.setStartX(dinnerLineConstants.getStartX());
        line.setStartY(dinnerLineConstants.getStartY());
        line.setEndX(dinnerLineConstants.getEndX());
        line.setEndY(dinnerLineConstants.getEndY());
        line.setLayoutX(dinnerLineConstants.getLayoutX());
        line.setLayoutY(dinnerLineConstants.getLayoutY());
        line.setStrokeWidth(dinnerLineConstants.getStrokeWidth());
        pane.getChildren().add(line);
    }
}
