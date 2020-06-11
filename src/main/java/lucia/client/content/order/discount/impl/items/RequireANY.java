package main.java.lucia.client.content.order.discount.impl.items;

import main.java.lucia.client.content.menu.item.Item;

import java.io.PrintStream;
import java.util.Set;

/**
 * Implemented Discount attribute to include ANY item
 * This would be used for something like a blanket 10% off discount
 * @author Matthew Kwiatkowski
 */
public class RequireANY extends DiscountApplicable{


    public RequireANY(){
    }

    /**
     * Prints out information of this attribute
     *
     * @param out the {@link PrintStream} to print to.
     */
    @Override
    public void printInfo(PrintStream out) {
        out.print(" Require ANY!");
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
