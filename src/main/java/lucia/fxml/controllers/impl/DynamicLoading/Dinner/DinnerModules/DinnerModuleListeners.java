package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerModules;

import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerItems.DinnerDynamicLoad;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;

/**
 * Listener Manager for the Dinner Modules
 */
public class DinnerModuleListeners {
    private PickupDeliveryPaneController pickupDeliveryPaneController;
    // the instance for the pickup delivery pane so the fxml methods can be called
    private String module; // dinner module information
    private JFXButton button;
    private DinnerModuleDesigns dinnerModuleDesigns;
    private DinnerDynamicLoad dinnerDynamicLoad;

    public DinnerModuleListeners(PickupDeliveryPaneController pickupDeliveryPaneController, DinnerDynamicLoad dinnerDynamicLoad,
                                 String module, JFXButton button) {
        this.pickupDeliveryPaneController = pickupDeliveryPaneController;
        this.module = module;
        this.button = button;
        dinnerModuleDesigns = new DinnerModuleDesigns(module);
        this.dinnerDynamicLoad = dinnerDynamicLoad;
    }

    /**
     *  Initial method to set the listeners for the button
     *
     *  Originally under "Code" in SceneBuilder
     *
     *  todo will have the designs for the buttons changed once the new design is implemented
     */
    public void setListeners() {
        button.setOnMouseClicked(this::moduleSelected);
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
    private void moduleSelected(MouseEvent event) {
        for(int x = 0; x < dinnerDynamicLoad.menuPaneModules.size(); x++) {
            if(module.equalsIgnoreCase(pickupDeliveryPaneController.pizza.getId())) {
                pickupDeliveryPaneController.pizza.toFront();
                return;
            }
            else if(module.equalsIgnoreCase(dinnerDynamicLoad.menuPaneModules.get(x).getId())) {
                dinnerDynamicLoad.menuPaneModules.get(x).toFront();
                return;
            }
            else {
                System.out.println(dinnerDynamicLoad.menuPaneModules.get(x).getId() + " Not Found on Click!");
                System.out.println(pickupDeliveryPaneController.pizza.getId());
            }
        }
        moduleClicked(module);
    }

    /**
     *  Implements with the Order System
     */
    private void moduleClicked(String id) {

    }
}
