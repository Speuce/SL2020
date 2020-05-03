package main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Sauce;

import main.java.lucia.consts.FoodConstants.SpecialInstruction.SauceConstants;

public class SauceCoordinates {
    private SauceConstants sauceConstants; // information for the sauce constants

    /**
     * Variables for the coordinates,
     *
     * no need for x margins, as there is only y coordinates changing
     * no need for max x value, as x coordinates do not change
     */
    private int currY; // current x value for the button
    private final int getSizeX; // current size for the buttons
    private final int getSizeY;
    private final int getYMargin; // length from where the new value is to be located where the current button is
    private final int getMaxY; // maximum y value where buttons can go
    private final int getStartX; // initial starting points for the first button
    private final int getStartY;

    public SauceCoordinates() {
        sauceConstants = new SauceConstants();
        getStartX = sauceConstants.getInitX();
        getStartY = sauceConstants.getInitY();
        currY = getStartY;
        getSizeX = sauceConstants.getSizeX();
        getSizeY = sauceConstants.getSizeY();
        getYMargin = sauceConstants.getyMargin();
        getMaxY = sauceConstants.getMaxY() - getYMargin; // maximum value at which we can place the button
    }

    /**  ACCESSORS  **/
    public int getCurrY() {
        return currY;
    }

    public int getGetSizeX() {
        return getSizeX;
    }

    public int getGetSizeY() {
        return getSizeY;
    }

    public int getGetYMargin() {
        return getYMargin;
    }

    public int getGetMaxY() {
        return getMaxY;
    }

    public int getGetStartX() {
        return getStartX;
    }

    public int getGetStartY() {
        return getStartY;
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
    public boolean checkLessThanMaxY() {
        return (currY + getYMargin) <= getMaxY;
    }
}
