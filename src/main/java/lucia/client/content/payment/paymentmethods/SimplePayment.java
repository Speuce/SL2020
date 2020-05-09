package main.java.lucia.client.content.payment.paymentmethods;

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
        super(p, amount);
        //preconditions: payment type is not AR, cheque, or gift
        assert(p != PaymentType.GIFT);
        assert(p != PaymentType.CHEQUE);
    }

}
