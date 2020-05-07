package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.Sides;

import com.jfoenix.controls.JFXButton;
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
    private SidesDesigns sidesDesigns;
    private JFXButton button;

    public SidesListeners(PickupDeliveryPaneController pickupDeliveryPaneController, AddonDescriptor addonDescriptor,
                          JFXButton button, SidesDesigns sidesDesigns) {
        this.pickupDeliveryPaneController = pickupDeliveryPaneController;
        this.addonDescriptor = addonDescriptor;
        this.button = button;
        this.sidesDesigns = sidesDesigns;
    }

    /**
     *  Initial method to set the listeners for the button
     *
     *  Originally under "Code" in SceneBuilder
     *
     *  todo will have the designs for the buttons changed once the new design is implemented
     */
    public void setListeners() {
        button.setOnMouseClicked(this::sideSelected);
        button.setOnMouseEntered(this::activateHover);
        button.setOnMouseExited(this::deactivateHover);
    }

    /**
     *  Event Handler for when the button is hovered into
     */
    public void activateHover(MouseEvent event) {
        //   button.setStyle(dinnerModuleDesigns.g());
    }

    /**
     *  Event Handler for when the button is hovered out of
     */
    public void deactivateHover(MouseEvent event) {
        //  button.setStyle(toppingDesigns.getDefaultStyleString());
    }
    /**
     *  Event Handler for when the button is 'clicked'
     *
     *  Goes to Order System
     */
    private void sideSelected(MouseEvent event) {
        sideClicked(addonDescriptor.getId());
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
