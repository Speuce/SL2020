package main.java.lucia.client.content.order.discount.impl.items;

import com.google.common.collect.Sets;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.order.impl.ItemList;

import java.io.PrintStream;
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
     * @param a the first part that MUST be included
     * @param b the second part that MUSt be included
     */
    public RequireAND(DiscountApplicable a, DiscountApplicable b) {
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
        return Sets.intersection(a.appliesTo(o), b.appliesTo(o));
    }


    /**
     * Prints out information of this attribute
     *
     * @param out the {@link PrintStream} to print to.
     */
    @Override
    public void printInfo(PrintStream out) {
        out.print("(");
        a.printInfo(out);
        out.print(") AND (");
        b.printInfo(out);
        out.print(")");
    }
}
