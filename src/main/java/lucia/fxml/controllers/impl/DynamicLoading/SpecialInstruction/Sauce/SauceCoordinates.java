package main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Sauce;

import main.java.lucia.consts.FoodConstants.SpecialInstruction.SauceConstants;

public class SauceCoordinates {
    private SauceConstants sauceConstants;

    private int currY;

    private final int getSizeX;
    private final int getSizeY;
    private final int getYMargin;
    private final int getMaxY;
    private final int getStartX;
    private final int getStartY;

    public SauceCoordinates() {
        sauceConstants = new SauceConstants();
        getStartX = sauceConstants.getInitX();
        getStartY = sauceConstants.getInitY();
        currY = getStartY;
        getSizeX = sauceConstants.getSizeX();
        getSizeY = sauceConstants.getSizeY();
        getYMargin = sauceConstants.getyMargin();
        getMaxY = sauceConstants.getMaxY() - getYMargin;
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
