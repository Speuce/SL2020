package main.java.lucia.fxml.controllers.impl.main.tabs.order.enternumber;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.java.lucia.client.content.order.Order;
import org.jetbrains.annotations.Nullable;

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
    @Nullable
    private Consumer<Order> orderLoaded;

    /**
     * The order displayed in this pane.
     */
    private Order displayedOrder;

    @FXML
    void loadOrder(ActionEvent event) {
        if(orderLoaded != null){
            orderLoaded.accept(displayedOrder);
            //destroy objects for GC collection
            orderLoaded = null;
            displayedOrder = null;
        }
    }

    /**
     * Displays the given order
     * @param o the order to display
     */
    public void displayOrder(Order o){
        this.displayedOrder = o;
        this.dateLabel.setText(o.getFormattedSet12TimeDate());
    }

    /**
     * The consumer of an order when it is selected to be loaded.
     */
    public @Nullable Consumer<Order> getOrderLoaded() {
        return orderLoaded;
    }

    /**
     * The consumer of an order when it is selected to be loaded.
     */
    public void setOrderLoaded(@Nullable Consumer<Order> orderLoaded) {
        this.orderLoaded = orderLoaded;
    }
}
