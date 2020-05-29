package main.java.lucia.fxml.controllers.impl.main.tabs.order.enternumber;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.java.lucia.client.content.order.Order;

import java.util.function.Consumer;

/**
 * Controller for viewing a past order
 * @author Matthew Kwiatkowski
 */
public class PastOrderViewPaneController {

    @FXML
    private Label dateLabel;

    /**
     * The consumer of an order when it is selected to be loaded.
     */
    private Consumer<Order> orderLoaded;

    @FXML
    void loadOrder(ActionEvent event) {

    }

    /**
     * Displays the given order
     * @param o the order to display
     */
    public void displayOrder(Order o){

    }

    /**
     * The consumer of an order when it is selected to be loaded.
     */
    public Consumer<Order> getOrderLoaded() {
        return orderLoaded;
    }

    /**
     * The consumer of an order when it is selected to be loaded.
     */
    public void setOrderLoaded(Consumer<Order> orderLoaded) {
        this.orderLoaded = orderLoaded;
    }
}
