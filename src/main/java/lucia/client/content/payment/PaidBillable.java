package main.java.lucia.client.content.payment;

import main.java.lucia.client.content.payment.paymentmethods.PaymentMethod;

/**
 * A Billable that has either already been paid for,
 * or will be paid for in the near future
 * @author Matthew Kwiatkowski
 */
public abstract class PaidBillable extends Billable implements Transaction{

    private

    /**
     * The ID of the employee that has signed this billable out
     */
    private int server;


    @Override
    public PaymentMethod getPaymentMethod() {
        return payment;
    }

    @Override
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.payment = paymentMethod;
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
