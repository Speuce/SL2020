package main.java.lucia.client.content.payment.paymentmethods;

import main.java.lucia.util.gson.RuntimeTypeAdapterFactory;

import java.time.LocalDateTime;

/**
 * a class representing a payment with type
 * @author Matt Kwiatkowki
 */
public abstract class PaymentMethod {

    /**
     * RowNum field for Json serialization
     * DO NOT TOUCH.
     */
    private int rowNum = -1;

    /**
     * The time that this payment was done/entered.
     */
    private LocalDateTime time;

    /**
     * The method of payment
     */
    private PaymentType p;

    /**
     * The amount paid
     */
    private long price;

    public PaymentMethod(PaymentType p, long price, LocalDateTime time){
        this.p = p;
        this.price = price;
        this.time = time;
    }

    public PaymentMethod(PaymentType p) {
        this.p = p;
    }

    public PaymentType getPaymentType() {
        return p;
    }

    public void setPaymentType(PaymentType p) {
        this.p = p;
    }

    /**
     * Get the amount paid by this payment method
     */
    public long getAmount() {
        return price;
    }

    /**
     * Set the amount paid by this payment method
     */
    public void setAmount(long price) {
        this.price = price;
    }

    /**
     * Get the original time of this payment method
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * The {@link RuntimeTypeAdapterFactory} for serializing payments
     * Stored as a singleton instance
     */
    private static RuntimeTypeAdapterFactory<PaymentMethod> paymentAdapterFactory;

    public static RuntimeTypeAdapterFactory<PaymentMethod> getPaymentAdapterFactory(){
        if(paymentAdapterFactory == null){
            paymentAdapterFactory = RuntimeTypeAdapterFactory.of(PaymentMethod.class)
                    .registerSubtype(SimplePayment.class, "simple")
                    .registerSubtype(GiftPayment.class, "gift");
        }
        return paymentAdapterFactory;
    }

    public static long nearest5(long cents){
        return 5*Math.round(cents/5.0);
    }

}
