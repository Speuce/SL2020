package main.java.lucia.client.content.payment.paymentmethods;

import main.java.lucia.client.content.payment.Payment;
import main.java.lucia.client.content.payment.PaymentType;

/**
 * A payment with one payment type
 * @author Matt Kwiatkowski
 * For GSON usage see Payment.java
 */
public class SinglePayment extends Payment {

    /**
     * The tip given with the payment
     */
    private long tip = 0l;

    public SinglePayment(PaymentType p, long price) {
        super(p, price);
    }
    public SinglePayment(PaymentType p) {
        super(p);
    }

    public long getTip() {
        return tip;
    }

    public void setTip(long tip) {
        this.tip = tip;
    }

    public boolean hasTip(){
        return this.tip >0;
    }
}
