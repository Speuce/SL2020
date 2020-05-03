package main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Crust;

import main.java.lucia.consts.FoodConstants.SpecialInstruction.CrustConstants;

public class CrustCoordinates {
    private CrustConstants crustConstants; // information for the crust

    /**
     * Variables for the coordinates,
     *
     * no need for x margins, as there is only y coordinates changing
     * no need for max x value, as x coordinates do not change
     */
    private int currY; // current y value for the button
    private final int getSizeX; // size for the buttons
    private final int getSizeY;
    private final int getYMargin; // length from where the new value is to be located where the current button is
    private final int getMaxY; // maximum y value at which the buttons can go
    private final int getStartX; // starting coordinates for the first button
    private final int getStartY;

    public CrustCoordinates() {
        crustConstants = new CrustConstants();
        getStartX = crustConstants.getInitX();
        getStartY = crustConstants.getInitY();
        currY = getStartY;
        getSizeX = crustConstants.getSizeX();
        getSizeY = crustConstants.getSizeY();
        getYMargin = crustConstants.getyMargin();
        getMaxY = crustConstants.getMaxY() - getYMargin; // maximum value at which we can place the button
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
