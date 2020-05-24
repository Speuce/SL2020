package main.java.lucia.client.content.menu.item.descriptor;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.type.SimpleItem;

/**
 * Represents an item loaded into the menu, with a single base price
 * @author Matt Kwiatkowski
 */
public class SimpleItemDescriptor extends Descriptor {

    /**
     * The price (in cents) of this item
     */
    private long price;

    public SimpleItemDescriptor(int id, String name, long price) {
        super(id, name);
        this.price = price;
    }

    public SimpleItemDescriptor(int id, String baseName, String defaultColor, String selectedColor, String hoverColor, String textColor, long price) {
        super(id, baseName, defaultColor, selectedColor, hoverColor, textColor);
        this.price = price;
    }

    //default constructor for jsons
    public SimpleItemDescriptor() {
        super(0, "");
    }

    /**
     *
     * @return the default price of this item.
     */
    public long getBasePrice() {
        return price;
    }

    /**
     * Get this loaded item, as an item that can be added to an order
     * @return a {@link Item} representing the item specified by this class,
     * returns an Item, not a SimpleItem as an itemMotifiable does not extend
     * a simple item: To fix, cast the return type to a SimpleItem
     */
    public Item getAsItem(){
        return new SimpleItem(this.getBaseName(), this.price, this);
    }
}
