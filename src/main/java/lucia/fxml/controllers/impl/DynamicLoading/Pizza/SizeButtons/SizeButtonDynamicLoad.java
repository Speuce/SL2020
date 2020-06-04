package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.SizeButtons;

import com.jfoenix.controls.JFXButton;
import main.java.lucia.consts.FoodConstants.Pizza.PizzaSizeButtonConstants;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

import java.util.ArrayList;

/**
 * Dynamic Loading Manager for the Size Buttons for Pizzas in the FXML
 */
public class SizeButtonDynamicLoad {
    private PizzaController pizzaController; // the instance of the pizzaController in order to control FXML methods
    private PizzaSizeButtonConstants sizeButtonConstants = new PizzaSizeButtonConstants();
    private SizeButtonCoordinates sC = new SizeButtonCoordinates();
    public SizeButtonListeners sizeButtonListeners;
    private ArrayList<JFXButton> sizeButtons = new ArrayList<>(); // the list of current size buttons
    private ArrayList<SizeButtonListeners> sizeButtonListenerList = new ArrayList<>();

    // list of the size buttons initially
    private ArrayList<Integer> sizeList = sizeButtonConstants.getSizeList();

    public SizeButtonDynamicLoad(PizzaController pizzaController) {
        this.pizzaController = pizzaController;
    }

    /**
     *  Initial method to start the loading of the size buttons
     */
    public void createSizeButtons() {
        JFXButton firstButton = createButton(sC.getGetStartX(), sC.getGetStartY(), sizeList.get(0), sC.getGetSizeX(), sC.getGetSizeY());
        pizzaController.sizeButtons.getChildren().add(firstButton); // gets the pane at which the buttons are to be stored
        iterateSizes();
    }

    /**
     *  Goes through the size list and adds all of them
     *  given the rules are correct for the entire list
     *
     *  Will add the size buttons in the list up to the point where it cannot go farther
     *
     *  todo will make into scrollpane for the ability for infinite buttons
     */
    private void iterateSizes() {
        for(int x = 1; x < sizeList.size(); x++) {
            if(sC.checkLessThanMaxX())
                sC.addToCurrX(sC.getGetXMargin());
            else System.out.println("WARNING: ScrollPane for Size Buttons may get large!");

            JFXButton button = createButton(sC.getCurrX(), sC.getGetStartY(), sizeList.get(x), sC.getGetSizeX(), sC.getGetSizeY());
            sizeButtons.add(button);
            pizzaController.sizeButtons.getChildren().add(button); // gets the pane at which the buttons are to be stored
        }
    }

    /**
     *  Creates the button when the list is being iterated
     *
     *  Calls the design, and creates the listeners for the button
     *  Adds button to the pane, after the fact that the button is tested true that it can be placed
     */
    private JFXButton createButton(int getX, int getY, int name, int getSizeX, int getSizeY) {
        JFXButton button = new JFXButton(name + "");
        SizeButtonDesigns sizeButtonDesigns = new SizeButtonDesigns(name);
        sizeButtonListeners = new SizeButtonListeners(pizzaController, name, button);
        sizeButtonListenerList.add(sizeButtonListeners);

        sizeButtonDesigns.initButtonDesign(button, getX, getY, getSizeX, getSizeY); //todo check button = ...
        sizeButtonListeners.setListeners();

        return button;
    }

    /**
     * Clears the selected buttons in the GUI
     */
    public void clearSelectedButtons() {
        for(SizeButtonListeners sizeButtonListeners : sizeButtonListenerList) {
            SizeButtonDesigns sizeButtonDesigns = new SizeButtonDesigns(sizeButtonListeners.getSize());
            sizeButtonListeners.setStyle(sizeButtonDesigns.defaultStyleString);
        }
    }
}
