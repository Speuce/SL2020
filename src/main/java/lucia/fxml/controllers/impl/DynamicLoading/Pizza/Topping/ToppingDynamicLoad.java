package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Topping;

import com.jfoenix.controls.JFXButton;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.consts.FoodConstants.Pizza.PizzaToppingConstants;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

import java.util.List;

/**
 * Dynamic Loading Manager for the Topping selection in the FXML
 */
public class ToppingDynamicLoad {

    private PizzaController pizzaController; // the instance of the pizzaController in order to control FXML methods
    PizzaToppingConstants pizzaToppingConstants = new PizzaToppingConstants();
    ToppingCoordinates tC = new ToppingCoordinates();

    // list for the toppings
    private List<ToppingType> toppingsList = pizzaToppingConstants.getToppingsList();

    public ToppingDynamicLoad(PizzaController pizzaController) {
        this.pizzaController = pizzaController;
    }

    /**
     *  Initial method to start the loading of the topping buttons
     */
    public void createToppings() {
        JFXButton firstButton = createButton(tC.getGetStartX(), tC.getGetStartY(), toppingsList.get(0), tC.getGetSizeX(), tC.getGetSizeY());
        pizzaController.pizzaButtons.getChildren().add(firstButton);
        iterateToppings();
    }

    /**
     *  Goes through the toppings list and adds all of them
     *  given the rules are correct for the entire list
     *
     *  Will add the topping items in the list up to the point where it cannot go farther
     *
     *  todo will make into scrollpane for the ability for infinite buttons
     */
    private void iterateToppings() {
        for(int x = 1; x < toppingsList.size(); x++) {
            if(tC.checkLessThanMaxX())
                tC.addToCurrX(tC.getGetXMargin());
            else if(tC.checkLessThanMaxY()) {
                tC.resetCurrX();
                tC.addToCurrY(tC.getGetYMargin());
            }
            //else
            JFXButton button = createButton(tC.getCurrX(), tC.getCurrY(), toppingsList.get(x), tC.getGetSizeX(), tC.getGetSizeY());
            pizzaController.pizzaButtons.getChildren().add(button); // gets the pane at which the buttons are to be stored
        }
    }

    /**
     *  Creates the button when the list is being iterated
     *
     *  Calls the design, and creates the listeners for the button
     *  Adds button to the pane, after the fact that the button is tested true that it can be placed
     */
    private JFXButton createButton(int getX, int getY, ToppingType name, int getSizeX, int getSizeY) {
        JFXButton button = new JFXButton(name.getDefaultColor());
        ToppingDesigns toppingDesigns = new ToppingDesigns(name);
        ToppingListeners toppingListeners = new ToppingListeners(pizzaController, name);

        toppingDesigns.initButtonDesign(button, getX, getY, getSizeX, getSizeY); //todo check button = ...
        toppingListeners.setListeners(button); // gets the pane at which the buttons are to be stored
        return button;
    }
}
