package main.java.lucia.fxml.controllers.intf;

import main.java.lucia.client.content.payment.paymentmethods.PaymentType;

import java.time.LocalDateTime;

/**
 * An interface for any class that can accept preorder pane actions
 * @author Matthew Kwiatkowski
 */
public interface PreorderInterface {

    /**
     * Called when the 'back' button is hit in the preorder pane
     */
    void onPreorderBack();

    /**
     * Called when a preorder selected is chosen
     * @param time the time selected
     * @param type the payment type selected
     */
    void onPreorderSelect(LocalDateTime time, PaymentType type);
}
