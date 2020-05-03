package main.java.lucia.fxml.controllers.impl.DynamicLoading.SpecialInstruction.Crust;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.item.IDCaster;
import main.java.lucia.client.content.menu.pizza.Crust;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

public class CrustListeners {
    private PizzaController pizzaController;
    private Crust name;

    public CrustListeners(PizzaController pizzaController, Crust name) {
        this.pizzaController = pizzaController;
        this.name = name;
    }

    public JFXButton setListeners(JFXButton button) {
        button.setOnMouseClicked(crustSelected(name));
        button.setOnMouseEntered(pizzaController::activateHover);
        button.setOnMouseExited(pizzaController::deactivateHover);
        return button;
    }

    private EventHandler<? super MouseEvent> crustSelected(Crust name) {
        crustClicked(name.getId());
        return null; //setOnMouseClicked must be an 'Event'
    }

    // matt thing
    private void crustClicked(int id) {
        // add to order system
        ToppingType type = new IDCaster<ToppingType>().cast(id);
       // if(pizzaController.getCurrentPizza().hasToppingType(type)){
            //add crust option
      //  }else{
            //remove crust option
    //    }


    }
}
