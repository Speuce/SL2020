package main.java.lucia.client.content.employee;

import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.payment.CashOutOfTill;
import main.java.lucia.client.content.payment.PaymentType;
import main.java.lucia.client.content.payment.Transaction;
import main.java.lucia.client.content.payment.paymentmethods.SimplePayment;
import main.java.lucia.client.content.payment.paymentmethods.SplitPayment;
import main.java.lucia.client.manager.impl.OrderManager;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A class for controlling and tracking cash-outs
 * @author Matt Kwiatkowski
 */
public class Cashout {

    /**
     * The orders in the cashout
     */
    private List<Order> myOrders;

    /**
     * Other transactions associated with the caashout
     */
    private Set<CashOutOfTill> otherOuts;

    /**
     * The total non-cash tips for the cashout
     */
    private long nonCashTips = 0;

    /**
     * The total cash tips for the cashout
     */
    private long cashTips = 0;

    /**
     * The employee that this cashout is for
     */
    private Employee employee;

    /**
     * True if the employee keeps their tips
     */
    private boolean employeeKeepsTips;

    /**
     * True if the employee collects delivery fees
     */
    private boolean deliveryFee;

    /**
     * True if the employee pay should be deducted from this cashout
     */
    private boolean payCash;

    /**
     * The delivery fees collected in cents
     */
    private long deliveryFees;

    /**
     * Float given to cashout by the store
     */
    private long cashFloat;

    /**
     * The pay for the employee (if it it cash)
     */
    private long totalPay;

    /**
     * The cash sales of this cashout
     */
    private long cashSales;

    /**
     * The cash owed to the store from this cashout
     * Negative means that the cash is being taken out from another cashout
     */
    private long cashToStore;

    /**
     * Other cash out of the till
     */
    private long otherOut;

    /**
     * If the cashout is negative, excess will be not null
     */
    private CashOutOfTill excess;


    public Cashout(List<Integer> orders, Set<CashOutOfTill> other, Employee e, long cashFloat){
        /* Map all order ids in orders to an actual order*/
        myOrders = orders.stream().map(id -> OrderManager.INSTANCE.getFromOrderNumber(id)).collect(Collectors.toList());
        this.otherOuts = other;
        this.employee = e;
        this.cashFloat = cashFloat;
        if(e instanceof  Driver){
            employeeKeepsTips = true;
            deliveryFee = true;
            payCash = true;
        }else{
            payCash = false;
            deliveryFee = false;
            employeeKeepsTips = false;
        }
    }

    /**
     * Recalculates total tips
     */
    public void updateTips(){
        cashTips = 0L;
        nonCashTips = 0L;
        for(Transaction o: myOrders){
            if(o.getPaymentType().equals(PaymentType.CASH)){
                cashTips += o.getTip(true);
            }else if(o.getPaymentType().equals(PaymentType.SPLIT)){
                cashTips += o.getTip(true);
                nonCashTips += o.getTip(false);
            }else{
                nonCashTips += o.getTip(false);
            }
        }
    }

    /**
     * Calculates applicable delivery fee
     */
    public void calculateDeliveryFees(){
        deliveryFees = 0;
        for(Order order: myOrders){
                if(order.isDelivery()){
                    //TODO check for east st paul
                    deliveryFees += 100;
                }
        }
    }

    /**
     * Calculates applicable pay in cash
     */
    public void calcPay(){
        if(payCash){
            totalPay = Math.round(employee.getPayRate()/100.0 * employee.getCurrentShift().getMinutesWorked()/60.0*100.0);
        }else{
            totalPay = 0;
        }
    }

    public void calcCashSales(){
        cashSales = 0;
        for(Order o: myOrders){
                if(o.getPaymentType().equals(PaymentType.CASH)){
                    cashSales+=o.getPaymentMethod().getPrice();
                }else if(o.getPaymentMethod() instanceof SplitPayment){
                    SplitPayment r = (SplitPayment) o.getPaymentMethod();
                    for(SimplePayment sub : r.getPaymentSet()){
                        if(sub.getPaymentType().equals(PaymentType.CASH)){
                            cashSales += sub.getPrice();
                        }
                    }
                }
        }
    }

    public void calcCashOut(){
        otherOut = 0;
        for(CashOutOfTill oth: otherOuts){
            otherOut = oth.getPaymentMethod().getPrice();
        }
    }

    public void calcCashToStore(){
        cashToStore = 0;
        // Cash float is returned to store
        cashToStore += cashFloat;
        // Cash sales is given to store
        cashToStore += cashSales;
        // Delivery fee taken away from store
        cashToStore -= deliveryFees;
        // Cash wages taken from store
        cashToStore -= totalPay;
        // Cash that has already been taken from the till
        cashToStore -= otherOut;
        // if employee keeps tips, this is taken from store too
        if(employeeKeepsTips){
            cashToStore -= cashTips;
            cashToStore -= nonCashTips;
        }else{
            cashToStore += cashTips;
        }
    }

    private void calcExcess(){
        if(cashToStore < 0){
            excess = new CashOutOfTill(employee.getShorterName() + "'s Cashout",
                    new SimplePayment(PaymentType.CASH, Math.abs(cashToStore)));
        }else{
            excess = null;
        }
    }

    /**
     * Calculates all values
     */
    public void recalculate(){
        calcCashOut();
        updateTips();
        calcCashSales();
        calculateDeliveryFees();
        calcPay();
        calcCashToStore();
        calcExcess();
    }

    /**
     * Checks whether the cashout has an excess amount that must be paid out
     * @return true if the cash to store is negative
     */
    public boolean hasExcess(){
        return excess==null;
    }

    public List<Order> getMyOrders() {
        return myOrders;
    }

    public Set<CashOutOfTill> getOtherOuts() {
        return otherOuts;
    }

    public long getNonCashTips() {
        return nonCashTips;
    }

    public long getCashTips() {
        return cashTips;
    }

    public Employee getEmployee() {
        return employee;
    }

    public boolean isEmployeeKeepsTips() {
        return employeeKeepsTips;
    }

    public boolean isDeliveryFee() {
        return deliveryFee;
    }

    public boolean isPayCash() {
        return payCash;
    }

    public long getDeliveryFees() {
        return deliveryFees;
    }

    public long getCashFloat() {
        return cashFloat;
    }

    public long getTotalPay() {
        return totalPay;
    }

    public long getCashSales() {
        return cashSales;
    }

    public long getCashToStore() {
        return cashToStore;
    }

    public long getOtherOut() {
        return otherOut;
    }

    public CashOutOfTill getExcess() {
        return excess;
    }
}
