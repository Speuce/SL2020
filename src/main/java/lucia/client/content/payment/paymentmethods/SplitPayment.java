package main.java.lucia.client.content.payment.paymentmethods;

import main.java.lucia.client.content.payment.Payment;
import main.java.lucia.client.content.payment.PaymentType;

import java.math.BigDecimal;
import java.util.LinkedList;

/**
 * Represents a payment comprised of multiple payments
 * @author Matt kwiatkowski
 */
public class SplitPayment extends Payment {

    /**
     * The set of payments that this split payment cosists of
     */
    private LinkedList<SimplePayment> paymentSet;

    public SplitPayment(long price) {
        super(PaymentType.SPLIT, price);
        paymentSet = new LinkedList<SimplePayment>();
    }

    /**
     * Adds a payment to the split payment
     * @param p
     */
    public void addPayment(SimplePayment p){
        paymentSet.add(p);
    }
    public long getTotalTips(){
        long ret = 0;
        for(SimplePayment p: paymentSet){
            if(p.hasTip()){
               ret += p.getTip();
            }
        }
        return ret;
    }

    public long getTotalCashTips(){
        long ret = 0;
        for(SimplePayment p: paymentSet){
            if(p.hasTip() && p.getPaymentType().equals(PaymentType.CASH)){
                ret += p.getTip();
            }
        }
        return ret;
    }
    public long getTotalNonCashTips(){
        long ret = 0;
        for(SimplePayment p: paymentSet){
            if(p.hasTip() && !p.getPaymentType().equals(PaymentType.CASH)){
                ret += p.getTip();
            }
        }
        return ret;
    }


    /**
     * Calculates the remaining price for this split payment
     * @return a {@link BigDecimal} with the remaining cost of this payment
     */
    public long getRemainingPrice(){
        long remaining = getPrice();
        for(SimplePayment p: paymentSet){
            remaining -= p.getPrice();
        }
        return (remaining);
    }

    public LinkedList<SimplePayment> getPaymentSet() {
        return paymentSet;
    }
}
