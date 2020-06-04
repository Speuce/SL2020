package main.java.lucia.client.content.order.discount.impl.amount;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.order.discount.impl.AppliedDiscount;
import main.java.lucia.client.content.order.impl.ItemList;

import java.util.Set;

/**
 * Discount amount calculator that
 * Provides a fixed amount off per item
 * @author Matthew Kwiatkowski
 */
public class PriceOffPerItem extends DiscountAmount{

    /**
     * The amount (in cents) given off per item
     */
    private final long amount;

    public PriceOffPerItem(long amount) {
        this.amount = amount;
    }

    /**
     * Applies the discount to the given subset of the order
     *
     * @param list  the set of items to apply to
     * @param order the order being applied to.
     * @return the amount (in cents) saved by applying this discount
     */
    @Override
    public int applyDiscount(AppliedDiscount o, Set<Item> list, ItemList order) {
        int totalDis = 0;
        for(Item i: list){
            i.getAppledDiscounts().add(o);
            i.setDiscountedPrice(i.getDiscountedPrice() - amount);
            totalDis+=amount;
        }
        return totalDis;
    }
}
