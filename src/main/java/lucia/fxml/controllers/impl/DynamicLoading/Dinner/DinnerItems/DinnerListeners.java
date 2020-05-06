package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerItems;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.item.IDCaster;
import main.java.lucia.client.content.menu.item.descriptor.Descriptor;
import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;

/**
 * Listener Manager for the Dinners
 */
public class DinnerListeners {
    private PickupDeliveryPaneController pickupDeliveryPaneController;
    // the instance for the pickup delivery controllers so the fxml methods can be called
    private Descriptor item; // dinner information

    public DinnerListeners(PickupDeliveryPaneController pickupDeliveryPaneController, Descriptor item) {
        this.pickupDeliveryPaneController = pickupDeliveryPaneController;
        this.item = item;
    }

    /**
     *  Initial method to set the listeners for the button
     *
     *  Originally under "Code" in SceneBuilder
     *
     *  todo will have the designs for the buttons changed once the new design is implemented
     */
    public JFXButton setListeners(JFXButton button) {
        button.setOnMouseClicked(itemSelected(item));
     //   button.setOnMouseEntered(pizzaController::activateHover);
     //   button.setOnMouseExited(pizzaController::deactivateHover);
        return button;
    }

    /**
     *  Event Handler for when the button is 'clicked'
     *
     *  Goes to Order System
     */
    private EventHandler<? super MouseEvent> itemSelected(Descriptor item) {
        itemClicked(item.getId());
        return null; //setOnMouseClicked must be an 'Event'
    }

    /**
     *  Implements with the Order System
     */
    private void itemClicked(int id) {
        // add to order system
        ToppingType type = new IDCaster<ToppingType>().cast(id);
      //  if(pizzaController.getCurrentPizza().hasToppingType(type)){
            //add item
        //}else{
            //remove item
       // }


    }
}
