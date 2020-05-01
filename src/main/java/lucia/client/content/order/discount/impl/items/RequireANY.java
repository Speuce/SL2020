package main.java.lucia.client.content.order.discount.impl.items;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.order.discount.impl.CustomDiscount;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Implemented Discount attribute to include ANY item
 * This would be used for something like a blanket 10% off discount
 * @author Matthew Kwiatkowski
 */
public class RequireANY extends DiscountApplicable{


    public RequireANY(CustomDiscount o) {
        super(o);
    }

    /**
     * Indicates whether or not the order can have
     * another application of this discount
     *
     * @param o the set of items to apply it to (minus items that already have this discount)
     * @return the item if it can, null otherwise
     */
    @Override
    protected Item canApply(Set<Item> o) {
        Item ret = null;
        if(!o.isEmpty()){
            ret = o.iterator().next();
        }
        return ret;
    }

    /**
     * Gets all the items from the item list that can have this discount
     *
     * @param o the set of items to check
     * @return a set of minimum size for which all requirements are met.
     */
    @Override
    public Set<Item> appliesTo(Set<Item> o) {
        return o;
    }

}
