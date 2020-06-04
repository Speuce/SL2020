package main.java.lucia.consts.FoodConstants.Pizza;

import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.PizzaMenu;
import main.java.lucia.consts.FoodConstants.FoodConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

/**
 *  Constant Manager for the Size button Pizza Dynamic Loading
 */
public class PizzaSizeButtonConstants extends FoodConstants {

    private TreeSet<Integer> sizeList = createLists();

    /**
     *  Sets the constant values for the Size Buttons
     */
    public PizzaSizeButtonConstants() {
        setInitX(17);
        setInitY(8);
        setxMargin(175);
        setSizeX(162);
        setSizeY(87);
        setMaxX(900);
        //no need for other variables! Y not moving
    }

    /**
     *  Creates the lists from the json system
     */
    public TreeSet<Integer> createLists() {
        PizzaMenu menuInstance = Menu.pizza;
        return menuInstance.getDefinedSizes();
    }

    /**
     *  ACCESSOR for the size button pizza list
     */
    public ArrayList<Integer> getSizeList() {
        Iterator<Integer> iterator = sizeList.iterator();
        ArrayList<Integer> sizes = new ArrayList<>();

        while(iterator.hasNext() ) {
            int size  = iterator.next();
            sizes.add(size);
        }
        return sizes;
    }
}
