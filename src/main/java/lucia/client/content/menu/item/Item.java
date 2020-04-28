package main.java.lucia.client.content.menu.item;

import main.java.lucia.client.content.menu.item.descriptor.Descriptor;

/**
 * Newly created Item class.
 * Directly refers to AbstractItem.
 * @author Matt Kwiatkowski
 */
public abstract class Item extends AbstractItem {
    public Item(String name, long price, Descriptor n) {
        super(name, price, n);
    }

    public Item(String name, Descriptor n) {
        super(name, n);
    }

    public Item(String displayName, String name, long price, long discountedPrice, Descriptor itemDescriptor) {
        super(displayName, name, price, discountedPrice, itemDescriptor);
    }
}
