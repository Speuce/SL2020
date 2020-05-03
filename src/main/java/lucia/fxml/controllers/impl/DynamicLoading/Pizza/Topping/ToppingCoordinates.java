package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Topping;

import main.java.lucia.consts.FoodConstants.Pizza.PizzaToppingConstants;

public class ToppingCoordinates {

    private PizzaToppingConstants pizzaToppingConstants;

    private int currX;
    private int currY;
    private final int getSizeX;
    private final int getSizeY;
    private final int getXMargin;
    private final int getYMargin;
    private final int getMaxY;
    private final int getMaxX;
    private final int getStartX;
    private final int getStartY;

    public ToppingCoordinates() {
        pizzaToppingConstants = new PizzaToppingConstants();
        getStartX = pizzaToppingConstants.getInitX();
        getStartY = pizzaToppingConstants.getInitY();
        currX = getStartX;
        currY = getStartY;
        getSizeX = pizzaToppingConstants.getSizeX();
        getSizeY = pizzaToppingConstants.getSizeY();
        getXMargin = pizzaToppingConstants.getxMargin();
        getYMargin = pizzaToppingConstants.getyMargin();
        getMaxY = pizzaToppingConstants.getMaxY() - getYMargin;
        getMaxX = pizzaToppingConstants.getMaxX() - getXMargin;
    }

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

    public void addToCurrX(int x) {
        currX += x;
    }

    public int getCurrY() {
        return currY;
    }

    public void addToCurrY(int y) {
        currY += y;
    }

    public boolean checkLessThanMaxX() {
        return (currX + getXMargin) <= getMaxX;
    }

    public boolean checkLessThanMaxY() {
        return (currY + getYMargin) <= getMaxY;
    }

    public void resetCurrX() {
        currX = getStartX;
    }

}
