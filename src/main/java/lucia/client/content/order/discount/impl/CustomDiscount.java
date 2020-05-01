package main.java.lucia.client.content.order.discount.impl;

import main.java.lucia.client.content.menu.item.IDAble;
import main.java.lucia.client.content.menu.item.descriptor.Descriptor;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.discount.Discount;
import main.java.lucia.client.content.order.discount.impl.amount.DiscountAmount;
import main.java.lucia.client.content.order.discount.impl.items.DiscountApplicable;
import main.java.lucia.client.content.order.discount.impl.stacking.DiscountStacking;
import main.java.lucia.client.content.order.discount.impl.times.DiscountTime;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * A Discount that can be customized
 * @author Matthew Kwiatkowski
 */
public class CustomDiscount extends Discount{

    //TODO one applicable
    //TODO filter (in here) by items already with this discount before passing to subclasses.
    /**
     * The set of all item requirements
     * that an order must meet to have this discount be
     * applicable
     */
    private Set<DiscountApplicable> applicables;

    /**
     * Indicates when (time-wise) this discount applies
     */
    private DiscountTime time;

    /**
     * Indicates if this discount can be stacked with other discounts
     */
    private DiscountStacking stacking;

    /**
     * Indicates how much is saved by this discount
     */
    private DiscountAmount amount;

    /**
     * Indicates whether or not this is a one-per-order discount
     * false if one-per-order
     * true otherwise
     */
    private boolean multiplies;

    public CustomDiscount(String name, int id, Set<DiscountApplicable> applicables,
                          DiscountTime time, DiscountStacking stacking,
                          DiscountAmount amount, boolean multiplies) {
        super(name, id);
        this.applicables = applicables;
        this.time = time;
        this.stacking = stacking;
        this.amount = amount;
        this.multiplies = multiplies;
    }

    /**
     * Check to see if this discount is compatible
     * with an order
     *
     * @param p the order to check compatibility with
     * @return true if the order is eligible for this discount
     */
    @Override
    public boolean isDiscountEligible(Order p) {
        //first see if this passes time check
        if(!time.applies(LocalDateTime.now())){
            return false;
        }
        //now see if it meets all of our item requirements
        for(DiscountApplicable a: getApplicables()){
            if(!a.canApply(p)){
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates the total discount amount for an Order
     *
     * @param p the order to get the total discount amount of.
     * @return the total amount saved by this discount
     */
    @Override
    public long calcDiscount(Order p) {
        return 0;
    }

    /**
     * Applies the discount to the given order
     *
     * @param p the order to apply this discount to.
     */
    @Override
    public void applyDiscount(Order p) {

    }

    /**
     * The set of all item requirements
     * that an order must meet to have this discount be
     * applicable
     */
    public Set<DiscountApplicable> getApplicables() {
        return applicables;
    }

    /**
     * The set of all item requirements
     * that an order must meet to have this discount be
     * applicable
     */
    public void setApplicables(Set<DiscountApplicable> applicables) {
        this.applicables = applicables;
    }

    /**
     * Indicates when (time-wise) this discount applies
     */
    public DiscountTime getTime() {
        return time;
    }

    /**
     * Indicates when (time-wise) this discount applies
     */
    public void setTime(DiscountTime time) {
        this.time = time;
    }

    /**
     * Indicates if this discount can be stacked with other discounts
     */
    public DiscountStacking getStacking() {
        return stacking;
    }

    /**
     * Indicates if this discount can be stacked with other discounts
     */
    public void setStacking(DiscountStacking stacking) {
        this.stacking = stacking;
    }

    /**
     * Indicates how much is saved by this discount
     */
    public DiscountAmount getAmount() {
        return amount;
    }

    /**
     * Indicates how much is saved by this discount
     */
    public void setAmount(DiscountAmount amount) {
        this.amount = amount;
    }

    /**
     * Indicates whether or not this is a one-per-order discount
     * false if one-per-order
     * true otherwise
     */
    public boolean multiplies() {
        return multiplies;
    }

    /**
     * Indicates whether or not this is a one-per-order discount
     * false if one-per-order
     * true otherwise
     */
    public void setMultiplies(boolean multiplies) {
        this.multiplies = multiplies;
    }


}
