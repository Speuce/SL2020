package main.java.lucia.client.content.order.discount.impl.items;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.order.discount.impl.CustomDiscount;
import main.java.lucia.client.content.order.impl.ItemList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

/**
 * By default, all other {@link DiscountApplicable} implementations
 * will only work for ONE item. To require 2 or more of a single
 * item, wrap the Requirement in this.
 * @author Matthew Kwiatkowski
 */
public class RequireAmount extends DiscountApplicable{

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
     * @param o the discount this is being applied to
     * @param subject the requirement that this applies to.
     * @param amt the amount of items to be required.
     */
    public RequireAmount(CustomDiscount o, DiscountApplicable subject, int amt) {
        super(o);
        this.subject = subject;
        this.amt = amt;
    }

    /**
     * Indicates whether or not the order can have
     * another application of this discount
     *
     * @param o the set of items to apply it to
     * @return true if it can, false otherwise
     */
    @Override
    public Item canApply(Set<Item> o) {
        Stack<Item> itemStack = new Stack<Item>();
        Item item;
        int i;
        //remove item x times.
        for(i = 0; i < amt; i++){
            item = subject.canApply(o);
            if(item == null){
                break;
            }
            itemStack.push(item);
            o.remove(item);
        }
        //whether or not the items could all be removed, we gotta add it back in
        Item last = null;
        while(itemStack.peek() != null){
            last = itemStack.pop();
            o.add(last);
        }
        return (i >= amt) ? last : null;
    }

    /**
     * Gets all the items from the item list that can have this discount
     *
     * @param o the set of items to check
     * @return a set of a size divisible by amt containing items
     * that satisfy the subject requirement.
     */
    @Override
    public Set<Item> appliesTo(Set<Item> o) {
        Set<Item> ret = subject.appliesTo(o);
        //remove the x smallest-priced elements so that the set is divisible
        //by amt.
        Item smol;
        for(int i = ret.size()%amt; i > 0; i--){
            if(!ret.isEmpty()){
                smol = ret.iterator().next();
                for(Item item: ret){
                    if(item.getPrice() < smol.getPrice()){
                        smol = item;
                    }
                }
                ret.remove(smol);
            }

        }
        return ret;
    }
}
