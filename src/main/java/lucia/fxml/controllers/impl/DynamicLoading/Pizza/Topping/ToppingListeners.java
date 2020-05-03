package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Topping;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.item.IDCaster;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

public class ToppingListeners {
    private PizzaController pizzaController;
    private ToppingType name;

    public ToppingListeners(PizzaController pizzaController, ToppingType name) {
        this.pizzaController = pizzaController;
        this.name = name;
    }

    public JFXButton setListeners(JFXButton button) {
        button.setOnMouseClicked(toppingSelected(name));
        button.setOnMouseEntered(pizzaController::activateHover);
        button.setOnMouseExited(pizzaController::deactivateHover);
        return button;
    }

    private EventHandler<? super MouseEvent> toppingSelected(ToppingType name) {
        toppingClicked(name.getId());
        return null; //setOnMouseClicked must be an 'Event'
    }

    // matt thing
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
