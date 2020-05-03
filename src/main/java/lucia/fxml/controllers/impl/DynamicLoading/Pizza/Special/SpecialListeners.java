package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Special;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

public class SpecialListeners {
    private PizzaController pizzaController;
    private SpecialtyPizzaDescriptor name;

    public SpecialListeners(PizzaController pizzaController, SpecialtyPizzaDescriptor name) {
        this.pizzaController = pizzaController;
        this.name = name;
    }

    public JFXButton setListeners(JFXButton button) {
        button.setOnMouseClicked(specialSelected(name));
        button.setOnMouseEntered(pizzaController::activateHover);
        button.setOnMouseExited(pizzaController::deactivateHover);
        return button;
    }

    private EventHandler<? super MouseEvent> specialSelected(SpecialtyPizzaDescriptor name) {
        specialClicked(name.getId());
        return null; //setOnMouseClicked must be an 'Event'
    }

    // matt thing
    private void specialClicked(int id) {
        // add to order system
    //    ToppingType type = new IDCaster<ToppingType>().cast(id);
       // if(pizzaController.getCurrentPizza().has(type)){
            //add topping
  //      }else{
            //remove topping
        }

}
