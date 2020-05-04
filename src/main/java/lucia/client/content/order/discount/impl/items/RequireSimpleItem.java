package main.java.lucia.client.content.order.discount.impl.items;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.order.discount.impl.CustomDiscount;
import main.java.lucia.client.content.order.impl.ItemList;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Implemented Discount Attribute to include a specific item
 * with a specific descriptor (no addons/toppings)
 * @author Matthew Kwiatkowski
 */
public class RequireSimpleItem extends DiscountApplicable{

    /**
     * The id of the descriptor, which items should have
     * to be included in the discount
     */
    private int itemId;

    public RequireSimpleItem(int itemId) {
        this.itemId = itemId;
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
        for(Item i: o){
            if(i.getItemDescriptor().getId() == itemId){
                return i;
            }
        }
        return null;
    }

    /**
     * Gets all the items from the item list that can have this discount
     *
     * @param o the set of items to check
     * @return a
     */
    @Override
    public Set<Item> appliesTo(Set<Item> o) {
        Set<Item> ret = new HashSet<>();
        for(Item i: o){
            if(i.getItemDescriptor().getId() == itemId){
                ret.add(i);
            }
        }
        return ret;
    }
}
