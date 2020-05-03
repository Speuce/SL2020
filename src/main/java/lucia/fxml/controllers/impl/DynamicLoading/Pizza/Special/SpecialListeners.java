package main.java.lucia.fxml.controllers.impl.DynamicLoading.Pizza.Special;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.item.descriptor.SpecialtyPizzaDescriptor;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

/**
 *  Manager for the Listeners for the Specialty Pizzas in the FXML
 */
public class SpecialListeners {
    private PizzaController pizzaController; // the instance for the pizza controllers so the fxml methods can be called
    private SpecialtyPizzaDescriptor name; // specialty pizza information

    public SpecialListeners(PizzaController pizzaController, SpecialtyPizzaDescriptor name) {
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
        button.setOnMouseClicked(specialSelected(name));
        button.setOnMouseEntered(pizzaController::activateHover);
        button.setOnMouseExited(pizzaController::deactivateHover);
        return button;
    }

    /**
     *  Event Handler for when the button is 'clicked'
     *
     *  Goes to Order System
     */
    private EventHandler<? super MouseEvent> specialSelected(SpecialtyPizzaDescriptor name) {
        specialClicked(name.getId());
        return null; //setOnMouseClicked must be an 'Event'
    }

    /**
     *  Implements with the Order System
     */
    private void specialClicked(int id) {
        // add to order system
    //    ToppingType type = new IDCaster<ToppingType>().cast(id);
       // if(pizzaController.getCurrentPizza().has(type)){
            //add topping
  //      }else{
            //remove topping
        }

}
