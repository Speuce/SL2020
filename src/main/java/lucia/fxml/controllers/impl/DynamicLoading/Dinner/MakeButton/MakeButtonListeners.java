package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.MakeButton;

import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.manager.impl.OrderManager;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerOrderManager;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.DynamicLoader;

/**
 *  Manager for the Listeners for the Make Button in the FXML
 */
public class MakeButtonListeners {
    private JFXButton button; // the button instance
    private MakeButtonDesigns makeButtonDesigns;

    public MakeButtonListeners(JFXButton button) {
        this.button = button;
        makeButtonDesigns = new MakeButtonDesigns();
    }

    /**
     * Initial method to set the listeners for the button
     * <p>
     * Originally under "Code" in SceneBuilder
     * <p>
     */
    public void setListeners() {
        button.setOnMouseClicked(this::makeSelected);
    }

    /**
     * Event Handler for when the button is 'clicked'
     * <p>
     * Goes to Order System
     */
    public void makeSelected(MouseEvent event) {
        DynamicLoader.dynamicLoaderInstance.getDinnerDynamicLoad().clearSelectedButtons();
        DynamicLoader.dynamicLoaderInstance.getDinnerDynamicLoad().clearSelectedButtonsSides();

        OrderManager orderManager = OrderManager.INSTANCE;
        addItemToOrder(DinnerOrderManager.getDinnerOrderInstance().makeItem());

    //    orderManager.registerOrder(order); NOT YET
    }

    /**
     * Adds the made item to the order instances
     *
     * @param item the made pizza
     */
    public void addItemToOrder(Item item) {
        Order currentOrder = DynamicLoader.dynamicLoaderInstance.pickupDeliveryPaneController.getCurrentOrder();
        DinnerOrderManager dinnerOrderManager = DinnerOrderManager.getDinnerOrderInstance();

        currentOrder.addItem(item);
        dinnerOrderManager.getCurrentOrder().addItem(item);
    }
}
