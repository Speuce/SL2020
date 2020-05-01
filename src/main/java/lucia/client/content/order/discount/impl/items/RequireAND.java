package main.java.lucia.client.content.order.discount.impl.items;

import com.google.common.collect.Sets;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.order.discount.Discount;
import main.java.lucia.client.content.order.discount.impl.CustomDiscount;
import main.java.lucia.client.content.order.impl.ItemList;

import java.util.Set;

/**
 * 'AND' Connective for discount item requirement
 * @author Matthew Kwiatkowski
 */
public class RequireAND extends DiscountApplicable{

    /**
     * The two requirements that must be met
     */
     DiscountApplicable a, b;

    /**
     * Construct an 'and' connective
     * @param o the discount that this applies to
     * @param a the first part that MUST be included
     * @param b the second part that MUSt be included
     */
    public RequireAND(CustomDiscount o, DiscountApplicable a, DiscountApplicable b) {
        super(o);
    }

    /**
     * Indicates whether or not the order can have
     * another application of this discount
     *
     * @param o the {@link ItemList} to apply it to
     * @return true if it can, false otherwise
     */
    @Override
    public Item canApply(Set<Item> o) {
        Set<Item> i1 = a.appliesTo(o);
        return b.canApply(i1);
    }

    /**
     * Gets all the items from the item list that can have this discount
     *
     * @param o the set of items to check
     * @return a
     */
    @Override
    public Set<Item> appliesTo(Set<Item> o) {
        return a.appliesTo(b.appliesTo(o));
    }


}
