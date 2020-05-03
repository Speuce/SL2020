package main.java.lucia.consts.FoodConstants.Pizza;

import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.PizzaMenu;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.consts.FoodConstants.FoodConstants;

import java.util.List;

public class PizzaToppingConstants extends FoodConstants {

    private List<ToppingType> toppings = createLists();

    public PizzaToppingConstants() {
        setInitX(18);
        setInitY(5);
        setxMargin(189);
        setyMargin(90);
        setSizeX(171);
        setSizeY(80);
        setMaxX(1341);
        setMaxY(457);
    }


    public List<ToppingType> createLists() {
        PizzaMenu menuInstance = Menu.pizza;
        return menuInstance.getDefinedToppings();
    }

    public List<ToppingType> getToppingsList() {
        return toppings;
    }
}
