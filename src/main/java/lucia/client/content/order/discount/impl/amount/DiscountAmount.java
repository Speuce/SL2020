package main.java.lucia.client.content.order.discount.impl.amount;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.order.discount.impl.CustomDiscount;
import main.java.lucia.client.content.order.discount.impl.DiscountAttribute;
import main.java.lucia.client.content.order.impl.ItemList;

import java.util.Set;

/**
 * Takes care of all the amount calculations for a discount
 * @author Matthew Kwiatkowski
 */
public abstract class DiscountAmount extends DiscountAttribute {

    public DiscountAmount(CustomDiscount o) {
        super(o);
    }

    /**
     * Applies the discount to the given subset of the order
     * @param list the itemlist to apply to
     * @return the amount (in cents) saved by applying this discount
     */
    public abstract long applyDiscount(Set<Item> list);
}
