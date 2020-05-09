package main.java.lucia.client.content.payment;

import main.java.lucia.client.content.payment.paymentmethods.*;

/**
 * A Billable that has either already been paid for,
 * or will be paid for in the near future
 * @author Matthew Kwiatkowski
 */
public abstract class PaidBillable extends Billable{

    /**
     * The main payment
     */
    private Payment payment;

    /**
     * The total amount already paid.
     */
    private long totalPaid;

    /**
     * The tip.
     */
    private Payment tips;

    /**
     * The ID of the employee that has signed this billable out
     */
    private int server;

    public PaidBillable(int server) {
        this.payment = new Payment();
        this.tips = new Payment();
        this.server = server;
        totalPaid = 0;
    }

    /**
     * Get (display) the payment type.
     * @return the main payment method (if there is only one)
     * or SPLIT if there is more than one.
     */
    public PaymentType getPaymentType(){
        if(payment.size() == 1){
            return payment.get(0).getPaymentType();
        }else if(payment.size() > 1){
            return PaymentType.SPLIT;
        }
        return PaymentType.UNPAID;
    }

    /**
     * Check whether the billable has been paid yet
     * @return true if the billable is paid
     */
    public boolean isPaid(){
        return this.payment.isEmpty();
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

    /**
     * Pay this billable off in full with the given
     * payment method. (not GIFT CARD)
     * (also includes tip)
     *
     */
    public void payinFullSimple(PaymentType p, long tipAMt){
        payment.clear();
        tips.clear();
        totalPaid = getGrandTotal();
        addPayment(new SimplePayment(p, getGrandTotal()));
        addTip(new SimplePayment(p, tipAMt));
    }

    /**
     * Pays this billable off in full by gift card
     * @param tipAmt the amount left in tips
     * @param cardNumber
     */
    public void payinFullGift(long tipAmt, Integer cardNumber){
        payment.clear();
        tips.clear();
        totalPaid = getGrandTotal();
        addPayment(new GiftPayment(getGrandTotal(), cardNumber));
        addTip(new GiftPayment(tipAmt, cardNumber));
    }

    /**
     * Adds a payment method.
     */
    public void addPayment(PaymentMethod m){
        payment.add(m);
        totalPaid += m.getAmount();
    }

    public void removePayment(PaymentMethod m){

    }

    /**
     * Adds a tip payment method
     */
    public void addTip(PaymentMethod m){
        tips.add(m);
    }

    /**
     * Get the payment for this billable
     */
    public Payment getPayment(){
        return payment;
    }

    /**
     * Get the tip payment methods
     */
    public Payment getTips(){
        return tips;
    }

    /**
     * Calculates how much is still owed
     */
    public long calcRemaining(){
        return getGrandTotal() - getTotalPaid();
    }

    public long getTotalPaid(){

    }

    /**
     * Get how much has already been paid.
     */
    private long calcTotalPaid(){
        long paid = 0;
        for(PaymentMethod m: payment){
            paid += m.getAmount();
        }
        return paid;
    }





}
