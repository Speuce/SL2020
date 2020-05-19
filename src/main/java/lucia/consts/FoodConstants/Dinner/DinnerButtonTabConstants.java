package main.java.lucia.consts.FoodConstants.Dinner;

import main.java.lucia.consts.FoodConstants.FoodConstants;

import java.util.List;

/**
 *  Constant Manager for the Dinner Module Dynamic Loading
 */
public class DinnerButtonTabConstants extends FoodConstants {

    private DinnerConstants dinnerConstants = new DinnerConstants();
    private List<String> dinnerModules = dinnerConstants.getDinners();

    /**
     *  Sets the constant values for the Dinner Modules
     */
    public DinnerButtonTabConstants() {
        setInitX(15);
        setInitY(30);
        setxMargin(260);
        setSizeX(247);
        setSizeY(87);
        setMaxX(1570);
        // no need for y margin, y not changing
    }

    /**
     *  ACCESSOR for the Dinner Module list
     */
    public List<String> getDinnerModuleList() {
        return dinnerModules;
    }
}
