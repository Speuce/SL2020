package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerItems;

import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.item.descriptor.Descriptor;
import main.java.lucia.client.content.menu.item.descriptor.SimpleItemDescriptor;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerOrderManager;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.Sides.SidesDynamicLoad;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.DynamicLoader;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;

/**
 * Listener Manager for the Dinners
 */
public class DinnerListeners {
    private PickupDeliveryPaneController pickupDeliveryPaneController;
    // the instance for the pickup delivery controllers so the fxml methods can be called
    private SimpleItemDescriptor item; // dinner information
    private JFXButton button;
    private DinnerDesigns dinnerDesigns;
    private SidesDynamicLoad sidesDynamicLoad;

    public DinnerListeners(PickupDeliveryPaneController pickupDeliveryPaneController, SimpleItemDescriptor item,
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
        if(!button.getStyle().equalsIgnoreCase(dinnerDesigns.getSelectedStyleString())) // if it is not selected!
            setStyle(dinnerDesigns.getHoveredStyleString());
    }

    /**
     *  Event Handler for when the button is hovered out of
     */
    public void deactivateHover(MouseEvent event) {
        //  button.setStyle(toppingDesigns.getDefaultStyleString());
        if(!button.getStyle().equalsIgnoreCase(dinnerDesigns.getSelectedStyleString())) // if it is not selected!
            setStyle(dinnerDesigns.getDefaultStyleString());
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
                break;
            }
//            else System.out.println(sidesDynamicLoad.menuSidesPanes.get(x).getId() + " Not Found On CLick! Looking For " + item.getBaseName());
            // Will happen if the item has no applicable addons
        }
        itemClicked(item.getId());
    }

    /**
     *  Implements with the Order System
     */
    private void itemClicked(int id) {
        DinnerOrderManager dinnerOrderManager = DinnerOrderManager.getDinnerOrderInstance();
        Menu menuInstance = Menu.get;

        DinnerOrderManager.getDinnerOrderInstance().addons.clear();

        if(item.equals(menuInstance.getItemFromId(id))) {
            if(dinnerOrderManager.currentItem == null) {
                dinnerOrderManager.currentItem = item;

                setStyle(dinnerDesigns.getSelectedStyleString());
                sidesDynamicLoad.loadDefaultSides(item);
            }
            else if(dinnerOrderManager.currentItem.equals(item)) {
                dinnerOrderManager.currentItem = null;
                setStyle(dinnerDesigns.getDefaultStyleString());
            } else {
                DynamicLoader.dynamicLoaderInstance.getDinnerDynamicLoad().clearSelectedButtons();
                dinnerOrderManager.currentItem = item;

                setStyle(dinnerDesigns.getSelectedStyleString());
                sidesDynamicLoad.loadDefaultSides(item);
            }
        }
        else System.out.println("Something is Wrong! Items clicked and class instance does not match!");
    }

    /**
     * Sets the style of the current button
     *
     * @param type the style to be changed to
     */
    public void setStyle(String type) {
        button.setStyle(type);
    }

    /**
     * GETTERS
     */
    public Descriptor getItem() {
        return item;
    }
}
