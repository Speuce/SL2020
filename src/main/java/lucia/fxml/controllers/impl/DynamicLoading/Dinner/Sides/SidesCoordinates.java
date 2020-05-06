package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.Sides;

import main.java.lucia.client.content.menu.item.descriptor.ItemModifiableDescriptor;
import main.java.lucia.consts.FoodConstants.Dinner.DinnerSidesConstants;

/**
 *  Coordinate Manager for the Sides Selection in the FXML
 */
public class SidesCoordinates {

    private DinnerSidesConstants dinnerSidesConstants; // calls the dinner constants

    private int currX; // current x value for the button
    private int currY; // current y value for the button
    private final int getSizeX; // size of the button
    private final int getSizeY;
    private final int getXMargin; // length from where the new value is to be located where the current button is
    private final int getYMargin;
    private final int getMaxY; // maximum value for the coordinates, stays in the pane that way
    private final int getMaxX;
    private final int getStartX; // initial starting coordinates for the first button
    private final int getStartY;

    public SidesCoordinates(ItemModifiableDescriptor item) {
        dinnerSidesConstants = new DinnerSidesConstants(item);
        getStartX = dinnerSidesConstants.getInitX();
        getStartY = dinnerSidesConstants.getInitY();
        currX = getStartX;
        currY = getStartY;
        getSizeX = dinnerSidesConstants.getSizeX();
        getSizeY = dinnerSidesConstants.getSizeY();
        getXMargin = dinnerSidesConstants.getxMargin();
        getYMargin = dinnerSidesConstants.getyMargin();
        getMaxY = dinnerSidesConstants.getMaxY() - getYMargin; // maximum value at which we can place the button
        getMaxX = dinnerSidesConstants.getMaxX() - getXMargin;
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

    public DinnerSidesConstants getDinnerSidesConstants() {
        return dinnerSidesConstants;
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
