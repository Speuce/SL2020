package main.java.lucia.client.content.order.discount.impl;

import main.java.lucia.client.content.order.Order;

/**
 * An attribute of a discount
 */
public abstract class DiscountAttribute {

    /**
     * Parent (owner) class of this
     */
    private CustomDiscount parent;


    public DiscountAttribute(CustomDiscount o){
        this.parent = o;
    }

    /**
     * Parent (owner) class of this
     */
    protected CustomDiscount getParent(){
        Order o;
        return parent;
    }
}
