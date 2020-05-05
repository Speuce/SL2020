package main.java.lucia.client.content.menu.item;

import main.java.lucia.client.content.menu.item.descriptor.Descriptor;
import main.java.lucia.client.content.order.discount.Discount;

import java.util.Set;

/**
 * Newly created Item class.
 * Directly refers to AbstractItem.
 * @author Matt Kwiatkowski
 */
public abstract class Item extends AbstractItem implements Comparable<Item> {
    public Item(String name, long price, Descriptor n) {
        super(name, price, n);
    }

    public Item(String name, Descriptor n) {
        super(name, n);
    }

    public Item(String displayName, String name, long price, long discountedPrice, Descriptor itemDescriptor, Set<Discount> appliedDiscounts) {
        super(displayName, name, price, discountedPrice, itemDescriptor, appliedDiscounts);
    }

    @Override
    public int compareTo(Item item) {
        int diff = (int) (item.getPrice() - this.getPrice());
        if(diff == 0){
            return item.hashCode()-this.hashCode();
        }
        return diff;
    }
}
