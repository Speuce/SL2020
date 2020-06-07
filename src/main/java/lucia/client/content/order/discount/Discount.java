package main.java.lucia.client.content.order.discount;

import main.java.lucia.client.content.utils.IDAble;
import main.java.lucia.client.content.order.Order;

import java.util.Map;

/**
 * Object representing a discount on an order
 * @author Matt Kwiatkowski
 */
public abstract class Discount extends IDAble {

    /**
     * The name of this discount
     */
    private String name;

    public Discount(String name, int id) {
        super(id);
        this.name = name;
    }

    /**
     * Check to see if this discount is compatible
     * with an order
     * @param p the order to check compatibility with
     * @return true if the order is eligible for this discount
     */
    public abstract boolean isDiscountEligible(Order p);

    /**
     * Calculates the total discount amount for an Order
     * @param p the order to get the total discount amount of.
     * @return the total amount saved by this discount
     */
    public abstract long calcDiscount(Order p);

    /**
     * Applies the discount to the given order
     * @param p the order to apply this discount to.
     */
    public abstract void applyDiscount(Order p, Map<String, Object> fields);

    /**
     * UN applies this discount from the given order.
     * @param p the order to take the discount from.
     */
    public abstract void unApplyDiscount(Order p);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
