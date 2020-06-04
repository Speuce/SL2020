package main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Sauce;

import com.jfoenix.controls.JFXButton;
import main.java.lucia.client.content.menu.pizza.Sauce;
import main.java.lucia.consts.FoodConstants.SpecialInstruction.SauceConstants;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

import java.util.ArrayList;
import java.util.List;

/**
 * Dynamic Loading Manager for the Sauce selection in the FXML
 */
public class SauceDynamicLoad {
    private PizzaController pizzaController; // the instance of the pizzaController in order to control FXML methods
    SauceConstants sauceConstants = new SauceConstants();
    SauceCoordinates sC = new SauceCoordinates();
    SauceListeners sauceListeners;
    ArrayList<SauceListeners> sauceListenerList = new ArrayList<>();

    // list for the sauces
    private List<Sauce> sauceList = sauceConstants.getSauceList();

    public SauceDynamicLoad(PizzaController pizzaController) {
        this.pizzaController = pizzaController;
    }

    /**
     *  Initial method to start the loading of the sauce buttons
     */
    public void createSauces() {
        JFXButton firstButton = createButton(sC.getGetStartX(), sC.getGetStartY(), sauceList.get(0), sC.getGetSizeX(), sC.getGetSizeY());
        pizzaController.saucePane.getChildren().add(firstButton); // gets the pane at which the buttons are to be stored
        iterateSauces();
    }

    /**
     *  Goes through the sauce list and adds all of them
     *  given the rules are correct for the entire list
     *
     *  Will add the crust items in the list up to the point where it cannot go farther
     *
     *  todo will make into scrollpane for the ability for infinite buttons
     */
    private void iterateSauces() {
        for(int x = 1; x < sauceList.size(); x++) {
            if(sC.checkLessThanMaxY())
                sC.addToCurrY(sC.getGetYMargin());
            else System.out.println("ERROR: Too many Sauces! Perhaps add scrollpane"); //todo
            //else

            JFXButton button = createButton(sC.getGetStartX(), sC.getCurrY(), sauceList.get(x), sC.getGetSizeX(), sC.getGetSizeY());
            pizzaController.saucePane.getChildren().add(button); // gets the pane at which the buttons are to be stored
        }
    }

    /**
     *  Creates the button when the list is being iterated
     *
     *  Calls the design, and creates the listeners for the button
     *  Adds button to the pane, after the fact that the button is tested true that it can be placed
     */
    private JFXButton createButton(int getX, int getY, Sauce name, int getSizeX, int getSizeY) {
        JFXButton button = new JFXButton(name.getDisplayName());
        SauceDesigns sauceDesigns = new SauceDesigns(name);
        sauceListeners = new SauceListeners(pizzaController, name, button);
        sauceListenerList.add(sauceListeners);

        sauceDesigns.initButtonDesign(button, getX, getY, getSizeX, getSizeY); //todo check button = ...
        sauceListeners.setListeners();

        return button;
    }

    /**
     * Clears the selected buttons in the GUI
     */
    public void clearSelectedButtons() {
        for(SauceListeners sauceListeners : sauceListenerList) {
            SauceDesigns sauceDesigns = new SauceDesigns(sauceListeners.getSauce());
            sauceListeners.setStyle(sauceDesigns.getDefaultStyleString());
        }
    }
}
