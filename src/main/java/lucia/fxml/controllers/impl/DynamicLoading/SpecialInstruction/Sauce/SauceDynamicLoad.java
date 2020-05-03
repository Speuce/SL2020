package main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Sauce;

import com.jfoenix.controls.JFXButton;
import main.java.lucia.client.content.menu.pizza.Crust;
import main.java.lucia.client.content.menu.pizza.Sauce;
import main.java.lucia.consts.FoodConstants.SpecialInstruction.CrustConstants;
import main.java.lucia.consts.FoodConstants.SpecialInstruction.SauceConstants;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Crust.CrustCoordinates;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Crust.CrustDesigns;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Crust.CrustListeners;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

import java.util.List;

public class SauceDynamicLoad {
    private PizzaController pizzaController;
    SauceConstants sauceConstants = new SauceConstants();
    SauceCoordinates sC = new SauceCoordinates();
    private List<Sauce> sauceList = sauceConstants.getSauceList();

    public SauceDynamicLoad(PizzaController pizzaController) {
        this.pizzaController = pizzaController;
    }

    public void createSauces() {
        JFXButton firstButton = createButton(sC.getGetStartX(), sC.getGetStartY(), sauceList.get(0), sC.getGetSizeX(), sC.getGetSizeY());
        pizzaController.pizzaButtons.getChildren().add(firstButton);
        iterateCrusts();
    }

    private void iterateCrusts() {
        for(int x = 1; x < sauceList.size(); x++) {
            if(sC.checkLessThanMaxY())
                sC.addToCurrY(sC.getGetYMargin());
            else System.out.println("ERROR: Too many Sauces! Perhaps add scrollpane"); //todo
            //else

            JFXButton button = createButton(sC.getGetStartX(), sC.getCurrY(), sauceList.get(x), sC.getGetSizeX(), sC.getGetSizeY());
            pizzaController.saucePane.getChildren().add(button);
        }
    }

    private JFXButton createButton(int getX, int getY, Sauce name, int getSizeX, int getSizeY) {
        JFXButton button = new JFXButton(name.getDisplayName());
        SauceDesigns sauceDesigns = new SauceDesigns(name);
        SauceListeners sauceListeners = new SauceListeners(pizzaController, name);

        button = sauceDesigns.initButtonDesign(button, getX, getY, getSizeX, getSizeY); //todo check button = ...
        button = sauceListeners.setListeners(button);

        return button;
    }
}
