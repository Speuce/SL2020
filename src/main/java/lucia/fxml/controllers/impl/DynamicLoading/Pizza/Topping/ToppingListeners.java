package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Topping;

import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.item.IDCaster;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.PizzaOrderManager;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

/**
 *  Manager for the Listeners for the Topping Selection in the FXML
 */
public class ToppingListeners {
    private PizzaController pizzaController; // the instance for the pizza controllers so the fxml methods can be called
    private ToppingType toppingType; // topping information
    private JFXButton button; // the button instance
    private ToppingDesigns toppingDesigns;
    PizzaOrderManager pizzaOrderManager;

    public ToppingListeners(PizzaController pizzaController, ToppingType toppingType, JFXButton button) {
        this.pizzaController = pizzaController;
        this.toppingType = toppingType;
        this.button = button;
        toppingDesigns = new ToppingDesigns(toppingType);
        pizzaOrderManager = PizzaOrderManager.getPizzaOrderInstance();
    }

    /**
     *  Initial method to set the listeners for the button
     *
     *  Originally under "Code" in SceneBuilder
     *
     *  todo will have the designs for the buttons changed once the new design is implemented
     */
    public void setListeners() {
        button.setOnMouseClicked(this::toppingSelected);
        button.setOnMouseEntered(this::activateHover);
        button.setOnMouseExited(this::deactivateHover);
    }

    /**
     *  Event Handler for when the button is hovered into
     */
    public void activateHover(MouseEvent event) {
        button.setStyle(toppingDesigns.getHoveredStyleString());
    }

    /**
     *  Event Handler for when the button is hovered out of
     */
    public void deactivateHover(MouseEvent event) {
        button.setStyle(toppingDesigns.getDefaultStyleString());
    }

    /**
     *  Event Handler for when the button is 'clicked'
     *
     *  Goes to Order System
     */
    public void toppingSelected(MouseEvent event) {
        toppingClicked(toppingType.getId());
    }

    /**
     *  Implements with the Order System
     */
    private void toppingClicked(int id) {
        ToppingType theTopping = new IDCaster<ToppingType>().cast(id);

        if(theTopping.equals(toppingType)) {
            if (pizzaOrderManager.currentPizza == null) {
                pizzaOrderManager.toppings.add(toppingType);
            } else if (pizzaOrderManager.toppings.contains(toppingType)) {
                pizzaOrderManager.toppings.remove(toppingType);
            } else {
                pizzaOrderManager.toppings.add(toppingType);
            }
        } else System.out.println("ERROR WHEN TOPPING CLICKED!");

        System.out.println("CHECKING TOPPING LIST: " + pizzaOrderManager.printToppings());

    }

}
