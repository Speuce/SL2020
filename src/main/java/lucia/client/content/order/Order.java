package main.java.lucia.client.content.order;

import main.java.lucia.client.content.order.discount.impl.AppliedDiscount;
import main.java.lucia.client.content.order.impl.PrintableOrder;
import main.java.lucia.client.content.order.impl.SpecialOrderInstructions;
import main.java.lucia.client.content.time.TimeFormat;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * A representation of an order
 *
 * @author Brett Downey
 * @author Matt Kwiatkowski
 * @author Zachery Unrau
 */
public class Order extends PrintableOrder {

    /**
     * The default time format which is used by the Order class
     */
    private static final transient TimeFormat DEFAULT_TIME_FORMAT = TimeFormat.FORMATTER_12_HOUR;

    /**
     * The specific id of this pizza on the server.
     * DO NOT SET. Let Gson do that.
     */
    private int rowNum = -1;

    /**
     * Dont touch this either.
     */
    private String type = "foodOrder";

    /**
     * The list of discounts applied.
     */
    private final List<AppliedDiscount> discountList;

    /**
     * The list of special instructions an order can contain
     */
    private ArrayList<SpecialOrderInstructions> specialOrderInstructions;

    private boolean isStaffOrder = false;

    /**
     * Creates a new order, initializes important values and sets the creation date.
     */
    public Order(OrderType type) {
        super();
        setOrderType(type);
        this.specialOrderInstructions = new ArrayList<>();
        this.discountList = new ArrayList<>();
    }

    /**
     * Creates a new order, which is a pre order
     *
     * @param type       The type of order to create
     * @param year       The year for the pre order
     * @param month      The month for the pre order
     * @param dayOfMonth The day of the month for the pre order
     * @param hour       The hour for the pre order
     * @param minute     The minutes for the pre order
     */
    public Order(OrderType type, int year, Month month, int dayOfMonth, int hour, int minute) {
        this(type);
        this.setPreorder(true);
        this.setPreorderTime(year, month, dayOfMonth, hour, minute);
    }

    public int getRowNum() {
        return rowNum;
    }

    /**
     * Custom hashing for orders
     */
    @Override
    public int hashCode() {
        return this.rowNum;
    }

    /**
     * Custom equals comparision
     */
    @Override
    public boolean equals(Object c){
        if(c instanceof Order){
            if(((Order)c).getRowNum() == rowNum){
                return true;
            }
        }
        return false;
    }

    public List<AppliedDiscount> getDiscountList() {
        return discountList;
    }
}