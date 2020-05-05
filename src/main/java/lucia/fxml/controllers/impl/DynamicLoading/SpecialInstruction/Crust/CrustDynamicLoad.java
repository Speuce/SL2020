package main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Crust;

import com.jfoenix.controls.JFXButton;
import main.java.lucia.client.content.menu.pizza.Crust;
import main.java.lucia.consts.FoodConstants.SpecialInstruction.CrustConstants;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

import java.util.List;

/**
 * Dynamic Loading Manager for the Crust selection in the FXML
 */
public class CrustDynamicLoad {
    private PizzaController pizzaController; // the instance of the pizzaController in order to control FXML methods
    CrustConstants crustConstants = new CrustConstants();
    CrustCoordinates cC = new CrustCoordinates();

    // list for the crusts
    private List<Crust> crustList = crustConstants.getCrustList();

    public CrustDynamicLoad(PizzaController pizzaController) {
        this.pizzaController = pizzaController;
    }

    /**
     *  Initial method to start the loading of the crust buttons
     */
    public void createCrusts() {
        JFXButton firstButton = createButton(cC.getGetStartX(), cC.getGetStartY(), crustList.get(0), cC.getGetSizeX(), cC.getGetSizeY());
        pizzaController.crustPane.getChildren().add(firstButton); // gets the pane at which the buttons are to be stored
        iterateCrusts();
    }

    /**
     *  Goes through the crust list and adds all of them
     *  given the rules are correct for the entire list
     *
     *  Will add the crust items in the list up to the point where it cannot go farther
     *
     *  todo will make into scrollpane for the ability for infinite buttons
     */
    private void iterateCrusts() {
        for(int x = 1; x < crustList.size(); x++) {
            if(cC.checkLessThanMaxY())
                cC.addToCurrY(cC.getGetYMargin());
            else System.out.println("ERROR: Too many Crusts! Perhaps add scrollpane"); //todo
            //else

            JFXButton button = createButton(cC.getGetStartX(), cC.getCurrY(), crustList.get(x), cC.getGetSizeX(), cC.getGetSizeY());
            pizzaController.pizzaButtons.getChildren().add(button); // gets the pane at which the buttons are to be stored
        }
    }

    /**
     *  Creates the button when the list is being iterated
     *
     *  Calls the design, and creates the listeners for the button
     *  Adds button to the pane, after the fact that the button is tested true that it can be placed
     */
    private JFXButton createButton(int getX, int getY, Crust name, int getSizeX, int getSizeY) {
        JFXButton button = new JFXButton(name.getDisplayName());
        CrustDesigns crustDesigns = new CrustDesigns(name);
        CrustListeners crustListeners = new CrustListeners(pizzaController, name);

        crustDesigns.initButtonDesign(button, getX, getY, getSizeX, getSizeY); //todo check button = ...
        crustListeners.setListeners(button);

        return button;
    }
}
