package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerModules;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;

/**
 * Listener Manager for the Dinner Modules
 */
public class DinnerModuleListeners {
    private PickupDeliveryPaneController pickupDeliveryPaneController;
    // the instance for the pickup delivery pane so the fxml methods can be called
    private String module; // dinner module information

    public DinnerModuleListeners(PickupDeliveryPaneController pickupDeliveryPaneController, String module) {
        this.pickupDeliveryPaneController = pickupDeliveryPaneController;
        this.module = module;
    }

    /**
     *  Initial method to set the listeners for the button
     *
     *  Originally under "Code" in SceneBuilder
     *
     *  todo will have the designs for the buttons changed once the new design is implemented
     */
    public JFXButton setListeners(JFXButton button) {
        button.setOnMouseClicked(moduleSelected(module));
      //  button.setOnMouseEntered(pizzaController::activateHover);
      //  button.setOnMouseExited(pizzaController::deactivateHover);
        return button;
    }

    /**
     *  Event Handler for when the button is 'clicked'
     *
     *  Goes to Order System
     */
    private EventHandler<? super MouseEvent> moduleSelected(String module) {
        moduleClicked(module);
        return null; //setOnMouseClicked must be an 'Event'
    }

    /**
     *  Implements with the Order System
     */
    private void moduleClicked(String id) {
        // add to order system
      //  ToppingType type = new IDCaster<ToppingType>().cast(id);
      //  if(pizzaController.getCurrentPizza().hasToppingType(type)){
            //add item
      //  }else{
            //remove item
    //    }


    }
}
