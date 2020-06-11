package main.java.lucia.client.content.order.discount.impl.items;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.type.pizza.Pizza;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Implemented discount attribute to include a minimum
 * number of toppings
 * @author Matthew Kwiatkowski
 */
public class RequireNumToppings extends DiscountApplicable{

    /**
     * The minimum number of toppings to require
     */
    private int numToppings;

    /**
     * Construct a new minimum topping requirement
     * @param numToppings the minimum number of toppings to include
     */
    public RequireNumToppings(int numToppings) {
        this.numToppings = numToppings;
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
            if(i instanceof Pizza && ((Pizza)i).getToppings().size() >= numToppings){
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
            if(i instanceof Pizza && ((Pizza)i).getToppings().size() >= numToppings){
                ret.add(i);
            }
        }
        return ret;
    }

    /**
     * Prints out information of this attribute
     *
     * @param out the {@link PrintStream} to print to.
     */
    @Override
    public void printInfo(PrintStream out) {
        out.print(" " + numToppings + " Toppings.");
    }
}
