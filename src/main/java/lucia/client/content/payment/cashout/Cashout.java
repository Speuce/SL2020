package main.java.lucia.client.content.payment.cashout;

import main.java.lucia.client.content.employee.type.CashoutHolder;
import main.java.lucia.client.content.employee.type.Employee;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.payment.paymentmethods.PaymentMethod;
import main.java.lucia.client.content.payment.paymentmethods.PaymentType;
import main.java.lucia.client.content.payment.paymentmethods.SimplePayment;
import main.java.lucia.client.manager.impl.OrderManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class for controlling and tracking cash-outs
 * @author Matt Kwiatkowski
 */
public class Cashout {

    /**
     * For gson. Please do not touch.
     */
    int rowNum = -1;

    /**
     * The orders in the cashout
     */
    private final List<Order> myOrders;

    /**
     * Other transactions associated with the caashout
     */
    private final List<CashOutOfTill> otherOuts;

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
    private CashoutHolder employee;

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
     * The amount to be paid to the store for tip-out.
     */
    private long tipout;


    /**
     * If the cashout is negative, excess will be not null
     */
    private CashOutOfTill excess;


    public Cashout(List<Integer> orders, List<CashOutOfTill> other, CashoutHolder e){
        /* Map all order ids in orders to an actual order*/
        myOrders = orders.stream().map(OrderManager.INSTANCE::getFromOrderNumber).collect(Collectors.toList());
        this.otherOuts = other;
        this.employee = e;
//        if(e instanceof Driver){
//            employeeKeepsTips = true;
//            deliveryFee = true;
//            payCash = true;
//        }else{
//            payCash = false;
//            deliveryFee = false;
//            employeeKeepsTips = false;
//        }
    }

    /**
     * Constructor for employees
     */
    public Cashout(CashoutHolder e){
        myOrders = new ArrayList<>();
        this.otherOuts = new ArrayList<>();
        this.employee = e;
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
        if(payCash && employee.getCurrentShift() != null){
            totalPay = Math.round(employee.getPayRate()/100.0 * employee.getCurrentShift().getMinutesWorked()/60.0*100.0);
        }else{
            totalPay = 0;
        }
    }

    /**
     * Calculates the total cash sales
     * (does not include tips)
     */
    public void calcCashSales(){
        cashSales = 0;
        for(Order o: myOrders){
            for(PaymentMethod m : o.getPayment()){
                if(m.getPaymentType() == PaymentType.CASH){
                    cashSales+= m.getAmount();
                }
            }
        }
    }

    /**
     * Calculates the total cash tips
     */
    public void calcCashTips(){
        cashTips = 0;
        for(Order o: myOrders){
            for(PaymentMethod m : o.getTips()){
                if(m.getPaymentType() == PaymentType.CASH){
                    cashTips+= m.getAmount();
                }
            }
        }
    }

    /**
     * Calculates the non-cash tips.
     */
    public void calcNonCashTips(){
        nonCashTips = 0;
        for(Order o: myOrders){
            for(PaymentMethod m : o.getTips()){
                if(m.getPaymentType() != PaymentType.CASH){
                    nonCashTips+= m.getAmount();
                }
            }
        }
    }

    public void calcCashOut(){
        otherOut = 0;
        for(CashOutOfTill oth: otherOuts){
            otherOut = oth.getPaymentMethod().getAmount();
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
        //updateTips();
        calcCashTips();
        calcNonCashTips();
        calcCashSales();
        calculateDeliveryFees();
        calcPay();


        employee.onCashoutCalc();

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

    public List<CashOutOfTill> getOtherOuts() {
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

    public boolean doesEmployeeKeepsTips() {
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

    /**
     * Lets the employee collect delivery fees.
     */
    public void setCollectDriverFees(){
        deliveryFee = true;
    }

    /**
     * Lets the employee keep tips.
     */
    public void setEmployeeKeepsTips(){
        employeeKeepsTips = true;
    }

    /**
     * Sets the amount that the employee pays in tipout
     * @param amt
     */
    public void setTipoutAmount(long amt){
        this.tipout = amt;
    }

    /**
     * @return the total tips earned.
     */
    public long getTotalTips(){
        return cashTips + nonCashTips;
    }
}
