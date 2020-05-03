package main.java.lucia.client.content.order.discount.impl.items;

import com.google.common.collect.Sets;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.order.discount.impl.CustomDiscount;
import main.java.lucia.client.content.order.impl.ItemList;

import java.util.Set;
import java.util.TreeSet;

/**
 * 'OR' Connective for discount item requirement
 * @author Matthew Kwiatkowski
 */
public class RequireOR extends DiscountApplicable{

    /**
     * The two requirements that one of which must be met
     */
     DiscountApplicable a, b;

    /**
     * Construct an 'or' connective
     * either a or b but be included.
     * @param a the first part that may be included
     * @param b the second part that may be included
     *
     */
    public RequireOR(DiscountApplicable a, DiscountApplicable b) {
        this.a = a;
        this.b = b;
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
        Item ret = a.canApply(o);
        if(ret == null){
            ret = b.canApply(o);
        }
        return ret;
    }

    /**
     * Gets all the items from the item list that can have this discount
     *
     * @param o the set of items to check
     * @return a
     */
    @Override
    public Set<Item> appliesTo(Set<Item> o) {
        return Sets.union(a.appliesTo(o), b.appliesTo(o));
    }

}
