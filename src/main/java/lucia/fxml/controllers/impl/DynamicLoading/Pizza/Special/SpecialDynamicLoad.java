package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Special;

import com.jfoenix.controls.JFXButton;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.consts.FoodConstants.Pizza.PizzaSpecialsConstants;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

import java.util.List;

public class SpecialDynamicLoad {
    private PizzaController pizzaController;
    PizzaSpecialsConstants pizzaSpecialsConstants = new PizzaSpecialsConstants();
    SpecialCoordinates sC = new SpecialCoordinates();
    private List<SpecialtyPizzaDescriptor> specialsList = pizzaSpecialsConstants.getSpecialPizzaList();

    public SpecialDynamicLoad(PizzaController pizzaController) {
        this.pizzaController = pizzaController;
    }

    public void createSpecials() {
        JFXButton firstButton = createButton(sC.getGetStartX(), sC.getGetStartY(), specialsList.get(0), sC.getGetSizeX(), sC.getGetSizeY());
        pizzaController.specialAnchor.getChildren().add(firstButton);
        iterateCrusts();
    }

    private void iterateCrusts() {
        for(int x = 1; x < specialsList.size(); x++) {
            if(sC.checkLessThanMaxX())
                sC.addToCurrX(sC.getGetXMargin());
            else System.out.println("WARNING: ScrollPane for Specialty Pizzas may get large!");

            JFXButton button = createButton(sC.getCurrX(), sC.getGetStartY(), specialsList.get(x), sC.getGetSizeX(), sC.getGetSizeY());
            pizzaController.pizzaButtons.getChildren().add(button);
        }
    }

    private JFXButton createButton(int getX, int getY, SpecialtyPizzaDescriptor name, int getSizeX, int getSizeY) {
        JFXButton button = new JFXButton(name.getBaseName());
        SpecialDesigns specialDesigns = new SpecialDesigns(name);
        SpecialListeners specialListeners = new SpecialListeners(pizzaController, name);

        button = specialDesigns.initButtonDesign(button, getX, getY, getSizeX, getSizeY); //todo check button = ...
        button = specialListeners.setListeners(button);

        return button;
    }
}
