package main.java.lucia.consts.FoodConstants.Dinner;

import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.consts.FoodConstants.FoodConstants;

import java.util.List;

/**
 *  Sets the constant values for the Dinner Items
 */
public class DinnerConstants extends FoodConstants {
    private int fullPaneValue; // value of the entire pane, dinner items go on the first half (setMaxX)

    private List<String> dinners = createLists();
    /**
     *  Sets the constant values for the Dinner Items
     */
    public DinnerConstants() {
        setInitX(30);
        setInitY(25);
        setxMargin(225);
        setyMargin(145);
        setSizeX(185);
        setSizeY(117);
        setMaxX(700);
        setMaxY(779);
        setFullPaneValue(1337);
    }

    /**
     *  Creates the lists from the json system
     */
    public List<String> createLists() {
        Menu menuInstance = Menu.get;
        return menuInstance.getLoadedSections();
        // get appliable addons
    }

    /**
     *  ACCESSORS for the dinner list
     */
    public List<String> getDinners() {
        return dinners;
    }

    public int getFullPaneValue() {
        return fullPaneValue;
    }

    /**
     *  SETTERS for the dinner list
     */
    public void setFullPaneValue(int fullPaneValue) {
        this.fullPaneValue = fullPaneValue;
    }
}
