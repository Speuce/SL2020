package main.java.lucia.client.content.payment.paymentmethods;

import java.util.ArrayList;

/**
 * Represents a payment, with any given number of payment methods.
 * @author Matthew Kwiatkowski
 */
public class Payment extends ArrayList<PaymentMethod> {

    public Payment() {
        super(1);
    }
}
