package main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Crust;

import main.java.lucia.consts.FoodConstants.SpecialInstruction.CrustConstants;

public class CrustCoordinates {
    private CrustConstants crustConstants;

    private int currY;

    private final int getSizeX;
    private final int getSizeY;
    private final int getYMargin;
    private final int getMaxY;
    private final int getStartX;
    private final int getStartY;

    public CrustCoordinates() {
        crustConstants = new CrustConstants();
        getStartX = crustConstants.getInitX();
        getStartY = crustConstants.getInitY();
        currY = getStartY;
        getSizeX = crustConstants.getSizeX();
        getSizeY = crustConstants.getSizeY();
        getYMargin = crustConstants.getyMargin();
        getMaxY = crustConstants.getMaxY() - getYMargin;
    }
    public int getCurrY() {
        return currY;
    }

    public void addToCurrY(int y) {
        currY += y;
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

    public boolean checkLessThanMaxY() {
        return (currY + getYMargin) <= getMaxY;
    }

}
