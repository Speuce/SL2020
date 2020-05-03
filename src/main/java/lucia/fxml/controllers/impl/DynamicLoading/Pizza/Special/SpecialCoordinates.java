package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Special;

import main.java.lucia.consts.FoodConstants.Pizza.PizzaSpecialsConstants;

public class SpecialCoordinates {
    private PizzaSpecialsConstants pizzaSpecialsConstants;

    private int currX;
    private final int getSizeX;
    private final int getSizeY;
    private final int getXMargin;
    private final int getMaxX;
    private final int getStartX;
    private final int getStartY;
    
    public SpecialCoordinates() {
        pizzaSpecialsConstants = new PizzaSpecialsConstants();
        getStartX = pizzaSpecialsConstants.getInitX();
        getStartY = pizzaSpecialsConstants.getInitY();
        currX = getStartX;
        getSizeX = pizzaSpecialsConstants.getSizeX();
        getSizeY = pizzaSpecialsConstants.getSizeY();
        getXMargin = pizzaSpecialsConstants.getxMargin();
        getMaxX = pizzaSpecialsConstants.getMaxX() - getXMargin;
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

    public int getGetMaxX() {
        return getMaxX;
    }

    public int getGetStartX() {
        return getStartX;
    }

    public int getGetStartY() {
        return getStartY;
    }

    public int getCurrX() {
        return currX;
    }

    public void addToCurrX(int x) {
        currX += x;
    }

    public boolean checkLessThanMaxX() {
        return (currX + getXMargin) <= getMaxX;
    }


}
