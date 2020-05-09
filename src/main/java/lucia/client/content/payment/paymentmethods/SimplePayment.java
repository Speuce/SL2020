package main.java.lucia.client.content.payment.paymentmethods;

import main.java.lucia.client.content.time.ClientTime;

/**
 * A simple payment with no added fields.
 * Either Cash, debit, mc, visa, amex, or discover.
 * @author Matt Kwiatkowski
 * For GSON usage see Payment.java
 */
public class SimplePayment extends PaymentMethod {

    /**
     * Constructs a new simple payment
     * @param p the type of payment
     * @param amount the amount paid.
     */
    public SimplePayment(PaymentType p, long amount) {
        super(p, amount, ClientTime.getCurrentLocalTime());
        //preconditions: payment type is not AR, cheque, or gift
        assert(p != PaymentType.GIFT);
        assert(p != PaymentType.CHEQUE);
    }

    public SimplePayment(PaymentType p, long price, ClientTime time) {
        super(p, price, time);
    }
}
