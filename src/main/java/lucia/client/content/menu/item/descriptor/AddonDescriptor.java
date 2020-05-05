package main.java.lucia.client.content.menu.item.descriptor;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.type.Addon;
import main.java.lucia.client.content.menu.item.type.SimpleItem;

/**
 * Represents the descriptor for an addon,
 * contains the additonal flag for indicating whether or not
 * the addon price becomes negative with a "no" amount/negation
 * @author Matt Kwiatkowski
 */
public class AddonDescriptor extends SimpleItemDescriptor {

    /**
     * if the item should receive a discount equal to the price
     * of this addon when this addon is removed.
     * Think of removing chicken from a chicken caeser salad.
     * Should the customer get the salad cheaper?
     * That's this field.
     */
    private boolean isDiscountable;

    /**
     * Construct a new addon descriptor
     * @param name the name of the addon
     * @param price the base price for ONE of this addon
     * @param discount true if the item should receive a discount equal to the price
     *                 of this addon when this addon is negated, false if no discount should
     *                 ever be applied.
     */
    public AddonDescriptor(int id, String name, long price, boolean discount) {
        super(id, name, price);
        this.isDiscountable = discount;
    }

    public AddonDescriptor(int id, String baseName, String defaultColor, String selectedColor, String hoverColor, String textColor, long price, boolean isDiscountable) {
        super(id, baseName, defaultColor, selectedColor, hoverColor, textColor, price);
        this.isDiscountable = isDiscountable;
    }

    /**
     * @return true if the item should receive a discount equal to the price
     *  of this addon when this addon is negated, false if no discount should
     *  ever be applied.
     */
    public boolean isDiscountable(){
        return isDiscountable;
    }

    /**
     * Get this loaded item, as an item that can be added to an order
     * @return a {@link SimpleItem} representing the item specified by this class.
     */
    public Addon getAsItem(){
        return new Addon(this.getBaseName(), this.getBasePrice(), this);
    }
}
