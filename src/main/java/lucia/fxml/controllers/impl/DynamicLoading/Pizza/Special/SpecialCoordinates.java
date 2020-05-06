package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Special;

import main.java.lucia.consts.FoodConstants.Pizza.PizzaSpecialsConstants;


/**
 * Coordinate Manager for the Specialty Pizzas in FXML
 */
public class SpecialCoordinates {
    private PizzaSpecialsConstants pizzaSpecialsConstants;

    /**
     * Variables for the coordinates,
     *
     * no need for y margins, as there is only x coordinates changing
     * no need for max y value, as y coordinates do not change
     */
    private int currX; // the current coordinate at which the button is located
    private final int getSizeX; // width of button
    private final int getSizeY; // length of button
    private final int getXMargin;
    // length from where the new coordinate is to be located to where the current coordinate is located
    private final int getMaxX; // maximum value at which the buttons can be built towards
    private final int getStartX; //starting coordinates
    private final int getStartY;
    
    public SpecialCoordinates() {
        pizzaSpecialsConstants = new PizzaSpecialsConstants();
        getStartX = pizzaSpecialsConstants.getInitX();
        getStartY = pizzaSpecialsConstants.getInitY();
        currX = getStartX;
        getSizeX = pizzaSpecialsConstants.getSizeX();
        getSizeY = pizzaSpecialsConstants.getSizeY();
        getXMargin = pizzaSpecialsConstants.getxMargin();
        getMaxX = pizzaSpecialsConstants.getMaxX() - getXMargin; //maximum value at which we can place another button
    }

    /**  ACCESSORS  **/
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


    /**
     *  Once checkLessThanMaxX is true,
     *  then the current coordinate will move up as the button is created
     */
    public void addToCurrX(int x) {
        currX += x;
    }


    /**
     *  Returns true or false,
     *  depending on whether the button can be placed or not
     *  if the button does not exceed the maximum value
     */
    public boolean checkLessThanMaxX() {
        return (currX + getXMargin) <= getMaxX;
    }
}
