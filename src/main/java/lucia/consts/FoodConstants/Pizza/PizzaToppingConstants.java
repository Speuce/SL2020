package main.java.lucia.consts.FoodConstants.Pizza;

import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.PizzaMenu;
import main.java.lucia.client.content.menu.item.descriptor.ToppingType;
import main.java.lucia.consts.FoodConstants.FoodConstants;

import java.util.List;

/**
 *  Constant Manager for the Topping Dynamic Loading
 */
public class PizzaToppingConstants extends FoodConstants {

    private List<ToppingType> toppings;

    /**
     *  Sets the constant values for the Toppings
     */
    public PizzaToppingConstants() {
        toppings = createLists();
        setInitX(18);
        setInitY(5);
        setxMargin(189);
        setyMargin(90);
        setSizeX(171);
        setSizeY(80);
        setMaxX(1341);
        setMaxY(457);
    }

    /**
     *  Creates the lists from the json system
     */
    public List<ToppingType> createLists() {
        PizzaMenu menuInstance = Menu.pizza;
        return menuInstance.getDefinedToppings();
    }

    /**
     *  ACCESSOR for the toppings list
     */
    public List<ToppingType> getToppingsList() {
        return toppings;
    }
}
