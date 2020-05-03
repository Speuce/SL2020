package main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Sauce;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.item.IDCaster;
import main.java.lucia.client.content.menu.pizza.Sauce;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

public class SauceListeners {
    private PizzaController pizzaController;
    private Sauce name;

    public SauceListeners(PizzaController pizzaController, Sauce name) {
        this.pizzaController = pizzaController;
        this.name = name;
    }

    public JFXButton setListeners(JFXButton button) {
        button.setOnMouseClicked(sauceSelected(name));
        button.setOnMouseEntered(pizzaController::activateHover);
        button.setOnMouseExited(pizzaController::deactivateHover);
        return button;
    }

    private EventHandler<? super MouseEvent> sauceSelected(Sauce name) {
        sauceClicked(name.getId());
        return null; //setOnMouseClicked must be an 'Event'
    }

    // matt thing
    private void sauceClicked(int id) {
        // add to order system
        ToppingType type = new IDCaster<ToppingType>().cast(id);
        // if(pizzaController.getCurrentPizza().hasToppingType(type)){
        //add sauce option
        //  }else{
        //remove sauce option
        //    }


    }
}
