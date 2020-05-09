package main.java.lucia.client.content.payment.paymentmethods;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.util.gson.RuntimeTypeAdapterFactory;

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
    private ClientTime time;

    /**
     * The method of payment
     */
    private PaymentType p;

    /**
     * The amount paid
     */
    private long price;

    public PaymentMethod(PaymentType p, long price, ClientTime time){
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

    public void setP(PaymentType p) {
        this.p = p;
    }
    public long getAmount() {
        return price;
    }

    public void setAmount(long price) {
        this.price = price;
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

    /**
     * Used for getting the serializer for payments
     * @return {@link Gson} that can be used to serialize payment objects
     */
    public static Gson getGson(){
        return new GsonBuilder().registerTypeAdapterFactory(getPaymentAdapterFactory()).setPrettyPrinting().serializeNulls().create();
    }

    public static long nearest5(long cents){
        return 5*Math.round(cents/5.0);
    }

}
