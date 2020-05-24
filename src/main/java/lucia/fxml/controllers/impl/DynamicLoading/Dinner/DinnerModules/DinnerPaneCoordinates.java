package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerModules;

/**
 * Coordinate Manager for the Dinner Module in FXML
 */
public class DinnerPaneCoordinates {

    /**
     * Variables for the coordinates of the pane
     */
    private final int getSizeX; // width of button
    private final int getSizeY; // length of button
    private final int getStartX; //starting coordinates
    private final int getStartY;

    public DinnerPaneCoordinates() {
        getStartX = 0;
        getStartY = 0;
        getSizeX = 1339;
        getSizeY = 779;
    }

    /**  ACCESSORS  **/
    public int getGetSizeX() {
        return getSizeX;
    }

    public int getGetSizeY() {
        return getSizeY;
    }

    public int getGetStartX() {
        return getStartX;
    }

    public int getGetStartY() {
        return getStartY;
    }
}
