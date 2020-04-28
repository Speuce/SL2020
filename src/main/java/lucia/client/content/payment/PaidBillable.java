package main.java.lucia.client.content.payment;

/**
 * A Billable that has either already been paid for,
 * or will be paid for in the near future
 * @author Matthew Kwiatkowski
 */
public abstract class PaidBillable extends Billable implements Transaction{

    /**
     * The payment type that the billable was paid with
     * (or will be paid with)
     */
    private PaymentType paymentType;

    /**
     * The payment for this order
     */
    private Payment payment = null;

    /**
     * The ID of the employee that has signed this billable out
     */
    private int server;


    @Override
    public Payment getPayment() {
        return payment;
    }

    @Override
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    /**
     * Check whether the billable has been paid yet
     * @return true if the billable is paid
     */
    public boolean isPaid(){
        return this.payment != null;
    }

    /**
     * Gets the employee ID of whoever signed this order out
     *
     * @return an {@code int} representing the employee id of the employee who signed this order out
     */
    public int getServer() {
        return server;
    }

    /**
     * Sets the employee id of the server that has signed it out
     *
     * @param server the employee ID of the server
     */
    public void setServer(int server) {
        this.server = server;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }


}
