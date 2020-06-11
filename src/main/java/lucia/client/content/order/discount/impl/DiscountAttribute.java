package main.java.lucia.client.content.order.discount.impl;

import java.io.PrintStream;

/**
 * An attribute of a discount
 */
public abstract class DiscountAttribute {

    public DiscountAttribute(){
    }

    /**
     * Prints out information of this attribute
     * @param out the {@link java.io.PrintStream} to print to.
     */
    public abstract void printInfo(PrintStream out);

}
