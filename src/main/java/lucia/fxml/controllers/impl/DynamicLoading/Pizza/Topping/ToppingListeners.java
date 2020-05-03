package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Topping;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.item.IDCaster;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

/**
 *  Manager for the Listeners for the Topping Selection in the FXML
 */
public class ToppingListeners {
    private PizzaController pizzaController; // the instance for the pizza controllers so the fxml methods can be called
    private ToppingType name; // topping information

    public ToppingListeners(PizzaController pizzaController, ToppingType name) {
        this.pizzaController = pizzaController;
        this.name = name;
    }

    /**
     *  Initial method to set the listeners for the button
     *
     *  Originally under "Code" in SceneBuilder
     *
     *  todo will have the designs for the buttons changed once the new design is implemented
     */
    public JFXButton setListeners(JFXButton button) {
        button.setOnMouseClicked(toppingSelected(name));
        button.setOnMouseEntered(pizzaController::activateHover);
        button.setOnMouseExited(pizzaController::deactivateHover);
        return button;
    }

    /**
     *  Event Handler for when the button is 'clicked'
     *
     *  Goes to Order System
     */
    private EventHandler<? super MouseEvent> toppingSelected(ToppingType name) {
        toppingClicked(name.getId());
        return null; //setOnMouseClicked must be an 'Event'
    }

    /**
     *  Implements with the Order System
     */
    private void toppingClicked(int id) {
        // add to order system
        ToppingType type = new IDCaster<ToppingType>().cast(id);
        if(pizzaController.getCurrentPizza().hasToppingType(type)){
            //add topping
        }else{
            //remove topping
        }


    }
}
