package main.java.lucia.consts.FoodConstants;

import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.PizzaMenu;
import main.java.lucia.client.content.menu.pizza.ToppingType;

import java.util.List;

public class PizzaToppingConstants extends FoodConstants {

    private List<ToppingType> toppings = createLists();

    public PizzaToppingConstants() {
        setInitX(18);
        setInitY(5);
        setxMarginTopping(189);
        setyMarginTopping(90);
        setToppingSizeX(171);
        setToppingSizeY(80);
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
