package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.MakeButton;

import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.OrderType;
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

        DinnerOrderManager dinnerOrderManager = DinnerOrderManager.getDinnerOrderInstance();
        OrderManager orderManager = OrderManager.INSTANCE;
        Order order = new Order(OrderType.UNSELECTED);
        order.addItem(dinnerOrderManager.makeItem());

        orderManager.registerOrder(order);
    }
}
