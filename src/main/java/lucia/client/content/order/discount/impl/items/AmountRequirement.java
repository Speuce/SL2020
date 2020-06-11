package main.java.lucia.client.content.order.discount.impl.items;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.order.discount.impl.DiscountAttribute;

import java.io.PrintStream;
import java.util.*;

/**
 * By default, all other {@link DiscountApplicable} implementations
 * will only work for ONE item. To require 2 or more of a single
 * item, wrap the Requirement in this.
 * @author Matthew Kwiatkowski
 */
public class AmountRequirement extends DiscountAttribute {

    /**
     * The subject of the requirement
     */
    private DiscountApplicable subject;

    /**
     * The amount to be required
     */
    private int amt;

    /**
     * Creates a new amount requirement
     * @param subject the requirement that this applies to.
     * @param amt the amount of items to be required.
     */
    public AmountRequirement(DiscountApplicable subject, int amt) {
        this.subject = subject;
        this.amt = amt;
    }

    /**
     * Indicates whether or not the order can have
     * another application of this discount
     *
     * @param o the set of items to apply it to
     * @return the set of items that represent one combination
     * that can be applied, if the application does work, null otherwise
     */
    public Set<Item> canApply(Set<Item> o) {
        Set<Item> ret = new HashSet<>();
        Item item;
        int i;
        //remove item x times.
        for(i = 0; i < amt; i++){
            item = subject.canApply(o);
            if(item == null){
                break;
            }
            ret.add(item);
            o.remove(item);
        }
        //whether or not the items could all be removed, we gotta add it back in
        o.addAll(ret);
        return (ret.size() >= amt) ? ret : null;
    }

    /**
     * Gets all the items from the item list that can have this discount
     *
     * @param o the set of items to check
     * @return a set of minimum size for which all requirements are met.
     * and null if no such set can be found
     */
    public Set<Item> appliesTo(Set<Item> o) {
        TreeSet<Item> got = new TreeSet<>();
        got.addAll(subject.appliesTo(o));
        if(got.size() >= amt){
            Set<Item> ret = new LinkedHashSet<>();
            Iterator<Item> iter = got.iterator();
            for(int i = amt; i > 0; i--){
                ret.add(iter.next());
            }
            return ret;
        }
        return null;
    }

    /**
     * Prints out information of this attribute
     *
     * @param out the {@link PrintStream} to print to.
     */
    @Override
    public void printInfo(PrintStream out) {
        out.print(" " + amt + "of {");
        subject.printInfo(out);
        out.print("}");
    }

//    /**
//     * Gets all the items from the item list that can have this discount
//     *
//     * @param o the set of items to check
//     * @return a set of a size divisible by amt containing items
//     * that satisfy the subject requirement.
//     */
//    @Override
//    public Set<Item> appliesTo(TreeSet<Item> o) {
//        Set<Item> ret = subject.appliesTo(o);
//        //remove the x smallest-priced elements so that the set is divisible
//        //by amt.
//        Item smol;
//        for(int i = ret.size()%amt; i > 0; i--){
//            if(!ret.isEmpty()){
//                smol = ret.iterator().next();
//                for(Item item: ret){
//                    if(item.getPrice() < smol.getPrice()){
//                        smol = item;
//                    }
//                }
//                ret.remove(smol);
//            }
//
//        }
//        return ret;
//    }
}
