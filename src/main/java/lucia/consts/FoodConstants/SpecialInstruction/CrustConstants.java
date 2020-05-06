package main.java.lucia.consts.FoodConstants.SpecialInstruction;

import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.PizzaMenu;
import main.java.lucia.client.content.menu.pizza.Crust;
import main.java.lucia.consts.FoodConstants.FoodConstants;

import java.util.List;

/**
 *  Constant Manager for the Crust Dynamic Loading
 */
public class CrustConstants extends FoodConstants {
    private List<Crust> crusts = createLists();

    /**
     *  Sets the constant values for the Crusts
     */
    public CrustConstants() {
        setInitX(10);
        setInitY(10);
        setyMargin(75);
        setSizeX(152);
        setSizeY(65);
        setMaxY(464);
    }

    /**
     *  Creates the lists from the json system
     */
    public List<Crust> createLists() {
        PizzaMenu menuInstance = Menu.pizza;
        return menuInstance.getDefinedCrusts();
    }

    /**
     *  ACCESSOR for the crust list
     */
    public List<Crust> getCrustList() {
        return crusts;
    }
}
