package main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Crust;

import com.jfoenix.controls.JFXButton;
import main.java.lucia.client.content.menu.pizza.Crust;
import main.java.lucia.consts.FoodConstants.SpecialInstruction.CrustConstants;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

import java.util.List;

public class CrustDynamicLoad {
    private PizzaController pizzaController;
    CrustConstants crustConstants = new CrustConstants();
    CrustCoordinates cC = new CrustCoordinates();
    private List<Crust> crustList = crustConstants.getCrustList();

    public CrustDynamicLoad(PizzaController pizzaController) {
        this.pizzaController = pizzaController;
    }

    public void createCrusts() {
        JFXButton firstButton = createButton(cC.getGetStartX(), cC.getGetStartY(), crustList.get(0), cC.getGetSizeX(), cC.getGetSizeY());
        pizzaController.crustPane.getChildren().add(firstButton);
        iterateCrusts();
    }

    private void iterateCrusts() {
        for(int x = 1; x < crustList.size(); x++) {
            if(cC.checkLessThanMaxY())
                cC.addToCurrY(cC.getGetYMargin());
            else System.out.println("ERROR: Too many Crusts! Perhaps add scrollpane"); //todo
            //else

            JFXButton button = createButton(cC.getGetStartX(), cC.getCurrY(), crustList.get(x), cC.getGetSizeX(), cC.getGetSizeY());
            pizzaController.pizzaButtons.getChildren().add(button);
        }
    }

    private JFXButton createButton(int getX, int getY, Crust name, int getSizeX, int getSizeY) {
        JFXButton button = new JFXButton(name.getDisplayName());
        CrustDesigns crustDesigns = new CrustDesigns(name);
        CrustListeners crustListeners = new CrustListeners(pizzaController, name);

        button = crustDesigns.initButtonDesign(button, getX, getY, getSizeX, getSizeY); //todo check button = ...
        button = crustListeners.setListeners(button);

        return button;
    }
}
