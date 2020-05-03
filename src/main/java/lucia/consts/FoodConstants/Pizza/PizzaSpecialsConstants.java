package main.java.lucia.consts.FoodConstants.Pizza;

import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.PizzaMenu;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.consts.FoodConstants.FoodConstants;

import java.util.List;

public class PizzaSpecialsConstants extends FoodConstants {

    private List<SpecialtyPizzaDescriptor> specialPizzas = createLists();

    public PizzaSpecialsConstants() {
        setInitX(5);
        setInitY(5);
        setxMarginTopping(155);
        setToppingSizeX(150);
        setToppingSizeY(68);
        setMaxX(3000);
        //no need for other variables! Y not moving
    }

    public List<SpecialtyPizzaDescriptor> createLists() {
        PizzaMenu menuInstance = Menu.pizza;
        return menuInstance.getSpecialties();
    }

    public List<SpecialtyPizzaDescriptor> getSpecialPizzas() {
        return specialPizzas;
    }
}
