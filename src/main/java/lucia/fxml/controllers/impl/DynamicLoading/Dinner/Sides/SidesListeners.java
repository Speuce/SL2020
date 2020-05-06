package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.Sides;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.item.descriptor.AddonDescriptor;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;

/**
 *  Manager for the Listeners for the Sides Selection in the FXML
 */
public class SidesListeners {
    private PickupDeliveryPaneController pickupDeliveryPaneController;
    // the instance for the pickup delivery controllers so the fxml methods can be called
    private AddonDescriptor addonDescriptor; // sides information

    public SidesListeners(PickupDeliveryPaneController pickupDeliveryPaneController, AddonDescriptor addonDescriptor) {
        this.pickupDeliveryPaneController = pickupDeliveryPaneController;
        this.addonDescriptor = addonDescriptor;
    }

    /**
     *  Initial method to set the listeners for the button
     *
     *  Originally under "Code" in SceneBuilder
     *
     *  todo will have the designs for the buttons changed once the new design is implemented
     */
    public JFXButton setListeners(JFXButton button) {
        button.setOnMouseClicked(sideSelected(addonDescriptor));
     //   button.setOnMouseEntered(pizzaController::activateHover);
     //   button.setOnMouseExited(pizzaController::deactivateHover);
        return button;
    }

    /**
     *  Event Handler for when the button is 'clicked'
     *
     *  Goes to Order System
     */
    private EventHandler<? super MouseEvent> sideSelected(AddonDescriptor name) {
        sideClicked(name.getId());
        return null; //setOnMouseClicked must be an 'Event'
    }

    /**
     *  Implements with the Order System
     */
    private void sideClicked(int id) {
//        // add to order system
//        ToppingType type = new IDCaster<ToppingType>().cast(id);
//        if(pizzaController.getCurrentPizza().hasToppingType(type)){
//            //add topping
//        }else{
//            //remove topping
//        }


    }
}
