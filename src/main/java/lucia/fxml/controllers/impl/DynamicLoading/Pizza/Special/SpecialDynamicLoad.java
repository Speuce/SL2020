package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Special;

import com.jfoenix.controls.JFXButton;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.consts.FoodConstants.Pizza.PizzaSpecialsConstants;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

import java.util.ArrayList;
import java.util.List;

/**
 * Dynamic Loading Manager for the Specialty Pizzas in the FXML
 */
public class SpecialDynamicLoad {
    private PizzaController pizzaController; // the instance of the pizzaController in order to control FXML methods
    private PizzaSpecialsConstants pizzaSpecialsConstants = new PizzaSpecialsConstants();
    private SpecialCoordinates sC = new SpecialCoordinates();
    public SpecialListeners specialListeners;
    private ArrayList<JFXButton> specialButtons = new ArrayList<>(); // the list of current specialty buttons
    private ArrayList<SpecialListeners> specialListenersList = new ArrayList<>();

    // list of the specialty pizzas
    private List<SpecialtyPizzaDescriptor> specialsList = pizzaSpecialsConstants.getSpecialPizzaList();

    public SpecialDynamicLoad(PizzaController pizzaController) {
        this.pizzaController = pizzaController;
    }

    /**
     *  Initial method to start the loading of the specialty pizza buttons
     */
    public void createSpecials() {
        JFXButton firstButton = createButton(sC.getGetStartX(), sC.getGetStartY(), specialsList.get(0), sC.getGetSizeX(), sC.getGetSizeY());
        pizzaController.specialAnchor.getChildren().add(firstButton); // gets the pane at which the buttons are to be stored
        iterateSpecials();
    }

    /**
     *  Goes through the specialty pizza list and adds all of them
     *  given the rules are correct for the entire list
     *
     *  Will add the Specials in the list up to the point where it cannot go farther
     *
     *  todo will make into scrollpane for the ability for infinite buttons
     */
    private void iterateSpecials() {
        for(int x = 1; x < specialsList.size(); x++) {
            if(sC.checkLessThanMaxX())
                sC.addToCurrX(sC.getGetXMargin());
            else System.out.println("WARNING: ScrollPane for Specialty Pizzas may get large!");

            JFXButton button = createButton(sC.getCurrX(), sC.getGetStartY(), specialsList.get(x), sC.getGetSizeX(), sC.getGetSizeY());
            specialButtons.add(button);
            pizzaController.specialAnchor.getChildren().add(button); // gets the pane at which the buttons are to be stored
        }
    }

    /**
     *  Creates the button when the list is being iterated
     *
     *  Calls the design, and creates the listeners for the button
     *  Adds button to the pane, after the fact that the button is tested true that it can be placed
     */
    private JFXButton createButton(int getX, int getY, SpecialtyPizzaDescriptor name, int getSizeX, int getSizeY) {
        JFXButton button = new JFXButton(name.getBaseName());
        SpecialDesigns specialDesigns = new SpecialDesigns(name);
        specialListeners = new SpecialListeners(pizzaController, name, button);
        specialListenersList.add(specialListeners);

        specialDesigns.initButtonDesign(button, getX, getY, getSizeX, getSizeY); //todo check button = ...
        specialListeners.setListeners();

        return button;
    }

    /**
     * Clears the selected buttons in the GUI
     */
    public void clearSelectedButtons() {
        for(SpecialListeners specialListeners : specialListenersList) {
            SpecialDesigns specialDesigns = new SpecialDesigns(specialListeners.getSpecialtyPizzaDescriptor());
            specialListeners.setStyle(specialDesigns.defaultStyleString);
        }
    }
}
