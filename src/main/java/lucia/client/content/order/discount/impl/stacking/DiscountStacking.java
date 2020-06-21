package main.java.lucia.client.content.order.discount.impl.stacking;

import main.java.lucia.client.content.order.discount.impl.DiscountAttribute;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Manages whether or not a discount can be applied with another discount
 * @author Matthew Kwiatkowski
 */
public class DiscountStacking extends DiscountAttribute {

    /**
     * The discounts that can be applied with this one.
     * (by id)
     */
    private Set<Integer> stackableDiscounts;

    public DiscountStacking() {
        stackableDiscounts = new HashSet<>();
    }

    /**
     * Prints out information of this attribute
     *
     * @param out the {@link PrintStream} to print to.
     */
    @Override
    public void printInfo(PrintStream out) {

    }

    /**
     * Add a discount that should stack with this one
     * @param id the id of the discount
     */
    public void addStackable(Integer id){
        //assert(id != getParent().getId());
        stackableDiscounts.add(id);
    }

    /**
     * Removes a discount, preventing it from being stacked with this one
     * @param id the id of the discount
     */
    public void removeStackable(Integer id){
        stackableDiscounts.remove(id);
    }

    /**
     * Check if this discount is stackable with another
     * @param id the id of the other discount
     * @return true if the discounts can be stacked, false otherwise.
     */
    public boolean canStack(Integer id){
        return stackableDiscounts.contains(id);
    }
}
