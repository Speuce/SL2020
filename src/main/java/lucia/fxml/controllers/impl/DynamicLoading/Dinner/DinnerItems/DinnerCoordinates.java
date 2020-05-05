package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerItems;

import main.java.lucia.consts.FoodConstants.Dinner.DinnerConstants;

/**
 * Coordinate Manager for the Dinner System in FXML
 */
public class DinnerCoordinates {
    DinnerConstants dinnerConstants; // calls the dinner constants

    /**
     * Variables for the coordinates,
     *
     * no need for y margins, as there is only x coordinates changing
     * no need for max y value, as y coordinates do not change
     */
    private int currX; // the current coordinate at which the button is located
    private int currY; // the current coordinate at which the button is located
    private final int getSizeX; // width of button
    private final int getSizeY; // length of button
    private final int getXMargin;
    private final int getYMargin;
    // length from where the new coordinate is to be located to where the current coordinate is located
    private final int getMaxX; // maximum value at which the buttons can be built towards
    private final int getMaxY;
    private final int getStartX; //starting coordinates
    private final int getStartY;

    public DinnerCoordinates() {
        dinnerConstants = new DinnerConstants();
        getStartX = dinnerConstants.getInitX();
        getStartY = dinnerConstants.getInitY();
        currX = getStartX;
        currY = getStartY;
        getSizeX = dinnerConstants.getSizeX();
        getSizeY = dinnerConstants.getSizeY();
        getXMargin = dinnerConstants.getxMargin();
        getYMargin = dinnerConstants.getyMargin();
        getMaxY = dinnerConstants.getMaxY() - getYMargin; // maximum value at which we can place the button
        getMaxX = dinnerConstants.getMaxX() - getXMargin;
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

    public int getGetXMargin() {
        return getXMargin;
    }

    public int getGetYMargin() {
        return getYMargin;
    }

    public int getCurrX() {
        return currX;
    }

    public int getCurrY() {
        return currY;
    }

    /**
     *  Once checkLessThanMaxX is true,
     *  then the current coordinate will move up as the button is created
     */
    public void addToCurrX(int x) {
        currX += x;
    }

    /**
     *  Once checkLessThanMaxY is true,
     *  then the current coordinate will move up as the button is created
     */
    public void addToCurrY(int y) {
        currY += y;
    }

    /**
     *  Returns true or false,
     *  depending on whether the button can be placed or not
     *  if the button does not exceed the maximum value
     */
    public boolean checkLessThanMaxX() {
        return (currX + getXMargin) <= getMaxX;
    }

    /**
     *  Returns true or false,
     *  depending on whether the button can be placed or not
     *  if the button does not exceed the maximum value
     */
    public boolean checkLessThanMaxY() {
        return (currY + getYMargin) <= getMaxY;
    }

    /**
     *  Resets the current x value to the starting point
     *
     *  Once a new row is iterated towards
     */
    public void resetCurrX() {
        currX = getStartX;
    }

}
