package main.java.lucia.fxml.controllers.impl.DynamicLoading.Topping;

import com.jfoenix.controls.JFXButton;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.consts.FoodConstants.PizzaToppingConstants;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

import java.util.List;

public class ToppingsDynamicLoad {

    private PizzaController pizzaController;
    PizzaToppingConstants pizzaToppingConstants = new PizzaToppingConstants();
    ToppingCoordinates tC = new ToppingCoordinates();


    private List<ToppingType> toppingsList = pizzaToppingConstants.getToppingsList();

    public ToppingsDynamicLoad(PizzaController pizzaController) {
        this.pizzaController = pizzaController;
    }

    public void createToppings() {
        JFXButton firstButton = createButton(tC.getGetStartX(), tC.getGetStartY(), toppingsList.get(0), tC.getGetSizeX(), tC.getGetSizeY());
        pizzaController.pizzaButtons.getChildren().add(firstButton);
        iterateToppings();
    }

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
            pizzaController.pizzaButtons.getChildren().add(button);
        }
    }

    private JFXButton createButton(int getX, int getY, ToppingType name, int getSizeX, int getSizeY) {
        JFXButton button = new JFXButton(name.getName());
        ToppingDesigns toppingDesigns = new ToppingDesigns(name);
        ToppingListeners toppingListeners = new ToppingListeners(pizzaController, name);

        button = toppingDesigns.initButtonDesign(button, getX, getY, getSizeX, getSizeY); //todo check button = ...
        button = toppingListeners.setListeners(button);

        return button;
    }


}
