package main.java.lucia.client.content.order.discount.impl.items;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.type.pizza.Pizza;

import java.util.HashSet;
import java.util.Set;

/**
 * Implemented Discount Attribute to include a specific
 * pizza size
 * @author Matthew Kwiatkowski
 */
public class RequirePizzaSize extends DiscountApplicable{

    /**
     * The size to be required.
     */
    private int size;

    public RequirePizzaSize(int size) {
        this.size = size;
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
        for(Item i: o){
            if(i instanceof Pizza && ((Pizza)i).getSize() == size){
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
            if(i instanceof Pizza && ((Pizza)i).getSize() == size){
                ret.add(i);
            }
        }
        return ret;
    }
}
