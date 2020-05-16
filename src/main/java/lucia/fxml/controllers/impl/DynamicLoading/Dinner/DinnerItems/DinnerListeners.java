package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerItems;

import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.item.descriptor.Descriptor;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.Sides.SidesDynamicLoad;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;

/**
 * Listener Manager for the Dinners
 */
public class DinnerListeners {
    private PickupDeliveryPaneController pickupDeliveryPaneController;
    // the instance for the pickup delivery controllers so the fxml methods can be called
    private Descriptor item; // dinner information
    private JFXButton button;
    private DinnerDesigns dinnerDesigns;
    private SidesDynamicLoad sidesDynamicLoad;

    public DinnerListeners(PickupDeliveryPaneController pickupDeliveryPaneController, Descriptor item,
                           JFXButton button, DinnerDesigns dinnerDesigns, SidesDynamicLoad sidesDynamicLoad) {
        this.pickupDeliveryPaneController = pickupDeliveryPaneController;
        this.item = item;
        this.dinnerDesigns = dinnerDesigns;
        this.button = button;
        this.sidesDynamicLoad = sidesDynamicLoad;
    }

    /**
     *  Initial method to set the listeners for the button
     *
     *  Originally under "Code" in SceneBuilder
     *
     *  todo will have the designs for the buttons changed once the new design is implemented
     */
    public void setListeners() {
        button.setOnMouseClicked(this::itemSelected);
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
    private void itemSelected(MouseEvent event) {
        for(int x = 0; x < sidesDynamicLoad.menuSidesPanes.size(); x++) {
            if(sidesDynamicLoad.menuSidesPanes.get(x).getId().equalsIgnoreCase(item.getBaseName())) {
                sidesDynamicLoad.menuSidesPanes.get(x).toFront();
                return;
            }
            else System.out.println(sidesDynamicLoad.menuSidesPanes.get(x).getId() + " Not Found On CLick! Looking For " + item.getBaseName());
            // Will happen if the item has no applicable addons
        }
        itemClicked(item.getId());
    }

    /**
     *  Implements with the Order System
     */
    private void itemClicked(int id) {
        // add to order system
//        ToppingType type = new IDCaster<ToppingType>().cast(id);
      //  if(pizzaController.getCurrentPizza().hasToppingType(type)){
            //add item
        //}else{
            //remove item
       // }


    }
}
