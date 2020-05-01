package main.java.lucia.client.content.order.discount.impl.items;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.discount.impl.CustomDiscount;
import main.java.lucia.client.content.order.discount.impl.DiscountAttribute;
import main.java.lucia.client.content.order.impl.ItemList;

import java.util.List;
import java.util.Set;

/**
 * Contract for evaluating whether or not a certain order
 * contains valid items for a given discount
 * @author Matthew Kwiatkowski
 */
public abstract class DiscountApplicable extends DiscountAttribute {

    public DiscountApplicable(CustomDiscount o) {
        super(o);
    }


    /**
     * Checks to see if a set of items can have this discount applied
     * @param o the set of items to check
     * @return true if the discount can be applied here, false otherwise
     */
    public boolean canApplyTo(Set<Item> o){
        return canApply(o) != null;
    }

    /**
     * Indicates whether or not the order can have
     * another application of this discount
     * @param o the set of items to apply it to (minus items that already have this discount)
     * @return the item if it can, null otherwise
     */
    protected abstract Item canApply(Set<Item> o);
//
//    /**
//     * Apply this discount to the items in the itemlist
//     * @param o the list of items to apply to.
//     * @return the collection of items applied to
//     */
//    public abstract List<Item> apply(List<Item> o);

    /**
     * Gets all the items from the item list that can have this discount
     * @param o the set of items to check
     * @return a
     */
    public abstract Set<Item> appliesTo(Set<Item> o);


}
