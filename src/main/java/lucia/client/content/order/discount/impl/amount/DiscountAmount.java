package main.java.lucia.client.content.order.discount.impl.amount;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.order.discount.Discount;
import main.java.lucia.client.content.order.discount.impl.AppliedDiscount;
import main.java.lucia.client.content.order.discount.impl.DiscountAttribute;
import main.java.lucia.client.content.order.impl.ItemList;

import java.util.Set;

/**
 * Takes care of all the amount calculations for a discount
 * @author Matthew Kwiatkowski
 */
public abstract class DiscountAmount extends DiscountAttribute {

    /**
     * Construct a new Discount Amount calculator for the given discount
     */
    public DiscountAmount() {
    }

    /**
     * Applies the discount to the given subset of the order
     * @param o the {@link AppliedDiscount} that is being applied.
     * @param list the set of items to apply to
     * @param order the order being applied to.
     * @return the amount (in cents) saved by applying this discount
     */
    public abstract int applyDiscount(Discount o, Set<Item> list, ItemList order);
}
