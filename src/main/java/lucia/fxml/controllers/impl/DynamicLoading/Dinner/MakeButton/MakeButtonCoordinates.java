package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.MakeButton;

import main.java.lucia.consts.FoodConstants.Dinner.MakeButtonConstants;

/**
 * Coordinate Manager for the Make Button in FXML
 */
public class MakeButtonCoordinates {
    MakeButtonConstants makeButtonConstants; // calls the make button constants

    /**
     * Variables for the coordinates,
     *
     * only for one singular 'Make' Button
     */
    private final int getSizeX; // width of button
    private final int getSizeY; // length of button
    private final int getStartX; //starting coordinates
    private final int getStartY;

    public MakeButtonCoordinates() {
        makeButtonConstants = new MakeButtonConstants();
        getStartX = makeButtonConstants.getLayoutX();
        getStartY = makeButtonConstants.getLayoutY();
        getSizeX = makeButtonConstants.getSizeX();
        getSizeY = makeButtonConstants.getSizeY();
    }

    /** ACCESSORS **/
    public int getGetStartX() {
        return getStartX;
    }

    public int getGetStartY() {
        return getStartY;
    }

    public int getGetSizeX() {
        return getSizeX;
    }

    public int getGetSizeY() {
        return getSizeY;
    }
}
