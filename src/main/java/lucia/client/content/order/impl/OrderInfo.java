package main.java.lucia.client.content.order.impl;

import main.java.lucia.Client;
import main.java.lucia.client.content.customer.Complaint;
import main.java.lucia.client.content.customer.CustomerDetails;
import main.java.lucia.client.content.employee.type.Driver;
import main.java.lucia.client.content.order.OrderType;
import main.java.lucia.client.content.order.pricing.DiscountOthersCalculator;
import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.client.content.time.TimeFormat;
import main.java.lucia.fxml.controllers.impl.main.tabs.EmployeePane;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An order with info
 * pickup? delivery? who? etc.
 * @author Matt Kwiatkowski
 * @author Brett Downey
 */
public abstract class OrderInfo extends TimedItemList implements Comparable<OrderInfo> {

    /**
     * The order's number
     */
    private int orderNumber;

    /**
     * The type of order (pickup or delivery)
     */
    private OrderType orderType;

    /**
     * The details of the customers who made this order
     */
    private CustomerDetails customerDetails = new CustomerDetails();

    /**
     * If the order has a complaint. It'll be here
     */
    private Complaint applicableComplaint;

    public OrderInfo(){
        super();
    }

    /**
     * Finalize the order
     */
    public void finalizeOrder() {
        this.setBuiltTime(new ClientTime(TimeFormat.FORMATTER_12_HOUR,
                ClientTime.getWinnipegTimeZone())); // TODO When needed check saskatoon/winnipeg etc.
        this.calculateCost();
    }

    /**
     * Finalize the order, for an EDIT
     */
    public void finalizeOrderEdit() {
        this.setBuiltTime(new ClientTime(TimeFormat.FORMATTER_12_HOUR,
                ClientTime.getWinnipegTimeZone())); // TODO When needed check saskatoon/winnipeg etc.
        this.calculateCost();
    }

    /**
     * Sets the order type to the new given order type
     *
     * @param newType The order type to change to
     */
    public void setOrderType(OrderType newType) {
        orderType = newType;
    }

    @Override
    public long calculateFees(){
        long total = 0;
        if(this.isDelivery()){
            total += DiscountOthersCalculator.getInstance().getDeliveryFee();
        }
        return total;
    }

    /**
     * Gets the customer details
     *
     * @return The customer details that are assigned to this order
     */
    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public boolean isESP() {
        if(isDelivery()) {
            if(customerDetails.getDeliveryArea().equals("ESP"))
                return true;
        }
        return false;
    }

    /**
     * Get the order time (and date), formatted for 24h time
     */
    public String getFormattedSet24Time() {
        try {
            SimpleDateFormat format = new SimpleDateFormat(TimeFormat.FORMATTER_24_HOUR.PATTERN);
            Date first = format.parse(getOrderTime().toString(TimeFormat.FORMATTER_24_HOUR));
            return EmployeePane.getTimeFormat().format(first);
        } catch (ParseException e) {
            Client.logger.error("An error occurred while parsing the given dates!", e);
            return null;
        }
    }

    /**
     * Get the order time, formatted for 12h time
     */
    public String getFormattedSet12TimeDate() {
        try {
           return getOrderTime().toString(TimeFormat.FORMATTER_12_HOUR);
        } catch (Exception e) {
            Client.logger.error("An error occurred while parsing the given dates!", e);
            return null;
        }
    }


    public Long timeSinceLongMinutes() {
        try {
            SimpleDateFormat format = new SimpleDateFormat(TimeFormat.FORMATTER_24_HOUR.PATTERN);
            Date first = format.parse(getBuiltTime().toString(TimeFormat.FORMATTER_24_HOUR));
            Date second = new Date();
            long time = second.getTime() - first.getTime();
            long secs = time / 1000L;
            long minutes = secs / 60L;
            return minutes;
        } catch (ParseException e) {
            Client.logger.error("An error occurred while parsing the given dates!", e);
            return null;
        }
    }

    public String timeSince() {
        try {
            SimpleDateFormat format = new SimpleDateFormat(TimeFormat.FORMATTER_24_HOUR.PATTERN);
            Date first = format.parse(getBuiltTime().toString(TimeFormat.FORMATTER_24_HOUR));
            Date second = new Date();
            long diff = second.getTime() - first.getTime();
            return getTimeDifferenceFormatted(diff);
        } catch (ParseException e) {
            Client.logger.error("An error occurred while parsing the given dates!", e);
            return null;
        }
    }

    public static String getTimeDifferenceFormatted(long time) {
        long secs = time / 1000L;
        if (secs < 60) {
            return "<1m";
        }
        if (secs < 3600) {
            return secs / 60L + "m";
        }
        long hours = secs / 3600L;
        long minutes = secs / 60L % 60L;
        return hours + "h, " + minutes + "m";
    }

    /**
     * Sets the customer details information to the new customer details. We do not override the
     * customer details information unless explicitly asked to do so.
     *
     * @param customer The customer details information to merge
     * @param override If this is {@code true}, then this customer details will be overridden with the
     * given parameter customer details. Else, only certain fields will be merged.
     *
     * @deprecated Should use getCustomerDetails.set...
     */
    @Deprecated()
    public void setCustomerDetails(CustomerDetails customer, boolean override) {
        if (override) {
            customerDetails = customer;
        } else {
            customerDetails.setPhoneNumber(customer.getPhoneNumber());
            customerDetails.setName(customer.getName());
            customerDetails.setAddress(customer.getAddress());
        }
    }

    public Complaint getApplicableComplaint() {
        return applicableComplaint;
    }

    public void setApplicableComplaint(Complaint applicableComplaint) {
        this.applicableComplaint = applicableComplaint;
    }

    /**
     * @return true if the order has a complaint attached to it.
     */
    public boolean hasComplaint() {
        return applicableComplaint != null;
    }

    /**
     * Call this when the order has been taken out
     */
    public void takeOrder(Driver d) {
        setServer(d.getEmployeeID());
        this.setDeliveryLeftTime(new ClientTime(TimeFormat.FORMATTER_12_HOUR,
                ClientTime.getWinnipegTimeZone()));
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    /**
     * @return the order's order number
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber the order's order number
     */
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public int compareTo(OrderInfo o) {
        if(this.isFuturePreorder() || o.isFuturePreorder() || o.isPreOrder()){
            return this.getOrderTime().toLocalDate().compareTo(o.getOrderTime().toLocalDate());
        }
        return this.getOrderNumber() - o.getOrderNumber();
    }


}
