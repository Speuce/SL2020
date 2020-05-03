package main.java.lucia.consts.FoodConstants.Pizza;

import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.PizzaMenu;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.consts.FoodConstants.FoodConstants;

import java.util.List;

/**
 *  Constant Manager for the Specialty Pizza Dynamic Loading
 */
public class PizzaSpecialsConstants extends FoodConstants {

    private List<SpecialtyPizzaDescriptor> specialPizzaList = createLists();

    /**
     *  Sets the constant values for the Specials
     */
    public PizzaSpecialsConstants() {
        setInitX(5);
        setInitY(5);
        setxMargin(155);
        setSizeX(150);
        setSizeY(68);
        setMaxX(3000);
        //no need for other variables! Y not moving
    }

    /**
     *  Creates the lists from the json system
     */
    public List<SpecialtyPizzaDescriptor> createLists() {
        PizzaMenu menuInstance = Menu.pizza;
        return menuInstance.getSpecialties();
    }

    /**
     *  ACCESSOR for the specialty pizza list
     */
    public List<SpecialtyPizzaDescriptor> getSpecialPizzaList() {
        return specialPizzaList;
    }
}
