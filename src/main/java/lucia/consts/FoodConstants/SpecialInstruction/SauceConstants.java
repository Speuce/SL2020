package main.java.lucia.consts.FoodConstants.SpecialInstruction;

import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.PizzaMenu;
import main.java.lucia.client.content.menu.pizza.Sauce;
import main.java.lucia.consts.FoodConstants.FoodConstants;

import java.util.List;

public class SauceConstants extends FoodConstants {
    private List<Sauce> sauces = createLists();

    public SauceConstants() {
        setInitX(10);
        setInitY(10);
        setyMargin(75);
        setSizeX(140);
        setSizeY(65);
        setMaxY(464);
    }

    public List<Sauce> createLists() {
        PizzaMenu menuInstance = Menu.pizza;
        return menuInstance.getDefinedSauces();
    }

    public List<Sauce> getSauceList() {
        return sauces;
    }
}
