package main.java.lucia.client.content.order.impl.discount;

import main.java.lucia.client.content.order.Order;

/**
 * Object representing a discount on an order
 * @author Matt Kwiatkowski
 */
public abstract class Discount {

    /**
     * The name of this discount
     */
    private String name;

    public Discount(String name) {
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
    public abstract void applyDiscount(Order p);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
