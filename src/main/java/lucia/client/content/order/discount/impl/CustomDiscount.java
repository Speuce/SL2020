package main.java.lucia.client.content.order.discount.impl;

import jdk.internal.jline.internal.Nullable;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.discount.Discount;
import main.java.lucia.client.content.order.discount.impl.amount.DiscountAmount;
import main.java.lucia.client.content.order.discount.impl.items.AmountRequirement;
import main.java.lucia.client.content.order.discount.impl.stacking.DiscountStacking;
import main.java.lucia.client.content.order.discount.impl.times.DiscountTime;
import main.java.lucia.client.content.order.impl.ItemList;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * A Discount that can be customized
 * @author Matthew Kwiatkowski
 */
public class CustomDiscount extends Discount{

    //TODO filter (in here) by items already with this discount before passing to subclasses.
    /**
     * The set of all item requirements
     * that an order must meet to have this discount be
     * applicable
     * The list order is relevant. First requirements will
     * be fulfilled first. so it is recommened to have requirements such as
     * ANY at the end of the list
     */
    private LinkedList<AmountRequirement> applicables;

    /**
     * Indicates when (date) this discount applies
     */
    private Set<DiscountTime> time;


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

    public CustomDiscount(String name, int id, LinkedList<AmountRequirement> applicables,
                          Set<DiscountTime> timeDay, DiscountStacking stacking,
                          DiscountAmount amount, boolean multiplies) {
        super(name, id);
        this.applicables = applicables;
        this.time = timeDay;
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
        assert(p != null);
        //first see if this passes time check(s)
        LocalDateTime now = LocalDateTime.now();
        for(DiscountTime ti: time){
            if(!ti.applies(now)){
                return false;
            }
        }
        //now see if it can be stacked
        if(!multiplies){
            for(Item i: p){
                if(i.getAppledDiscounts().contains(this)){
                    return false;
                }
            }
        }

        //now see if it meets all of our item requirements
        Set<Item> items = getStackableItems(p);
        if(items == null || items.isEmpty()){
            return false;
        }
        Set<Item> used;
        for(AmountRequirement a: getApplicables()){
            used = a.canApply(items);
            if(used == null || used.isEmpty()){
                return false;
            }
            items.removeAll(used);
        }
        return true;
    }

    /**
     * Get all items from an order whose applied discounts stack
     * with this one, i.e: they can have this discount added.
     * @param p the order to get items from
     * @return the set of items found
     */
    @Nullable
    private Set<Item> getStackableItems(ItemList p){
        Set<Item> items = new HashSet<>();
        //first remove all non-discount-stackable items
        boolean add;
        for(Item i: p){
            add = true;
            for(Discount r: i.getAppledDiscounts()){
                if(!multiplies && r == this){
                    return null;
                }
                if(!this.stacking.canStack(r.getId())){
                    add = false;
                    break;
                }
            }
            if(add){
                items.add(i);
            }
        }
        return items;
    }

    /**
     * Calculates the total discount amount for an Order
     *
     * @param p the order to get the total discount amount of.
     * @return the total amount saved by this discount
     */
    @Override
    public long calcDiscount(Order p) {
        //TODO not implemented (idk how to do it efficiently as of now)
        return 0;
    }

    /**
     * Applies the discount to the given order
     *
     * @param p the order to apply this discount to.
     */
    @Override
    public void applyDiscount(Order p) {
        //precondition: the order is actually eligible for the discount
        assert(isDiscountEligible(p));
        Set<Item> items = getStackableItems(p);
        if(items == null) return;

        //current bundle
        Set<Item> bundle;
        Set<Item> curr;
        boolean cont = true;
        while(cont && !items.isEmpty()){
            bundle = new HashSet<>();
            for(AmountRequirement r: this.getApplicables()){
                curr = r.appliesTo(items);
                if(curr == null) return;
                items.removeAll(curr);
                bundle.addAll(curr);
            }
            cont = multiplies;
            amount.applyDiscount(this, bundle, p);
        }
    }

    /**
     * The set of all item requirements
     * that an order must meet to have this discount be
     * applicable
     */
    public LinkedList<AmountRequirement> getApplicables() {
        return applicables;
    }

    /**
     * The set of all item requirements
     * that an order must meet to have this discount be
     * applicable
     */
    public void setApplicables(LinkedList<AmountRequirement> applicables) {
        this.applicables = applicables;
    }

    /**
     * Indicates when (time-wise) this discount applies
     */
    public Set<DiscountTime> getTime() {
        return time;
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
