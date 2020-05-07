package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerModules;

/**
 * Coordinate Manager for the Addon Module in FXML
 */
public class DinnerAddonPaneCoordinates {

    /**
     * Variables for the coordinates of the pane
     */
    private final int getSizeX; // width of button
    private final int getSizeY; // length of button
    private final int getStartX; //starting coordinates
    private final int getStartY;

    public DinnerAddonPaneCoordinates() {
        getStartX = 711;
        getStartY = 0;
        getSizeX = 629;
        getSizeY = 766;
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
