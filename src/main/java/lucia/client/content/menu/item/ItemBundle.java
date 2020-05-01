package main.java.lucia.client.content.menu.item;

import main.java.lucia.client.content.menu.item.descriptor.Descriptor;
import main.java.lucia.client.content.menu.item.descriptor.ItemBundleDescriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Item's 'bundled' together and listed together.
 * Bundle has separate price from the items inside.
 * Although the items inside will be considered in calculating total cost.
 *
 * NOTE: the price of the items inside the bundle should be set to 0
 * if they are included in the price of the bundle and any extra costs
 * (such as extra toppings) should be the total price of the constituent
 * items.
 * @author Matthew Kwiatkowski
 */
public class ItemBundle extends Item{

    /**
     * The list of all items inside of this bundle
     */
    private List<Item> items;



    /**
     * Construct a new Item Bundle with the given properties
     * @param n the descriptor of the bundle
     * @param items the list of items in the bundle
     */
    public ItemBundle(ItemBundleDescriptor n, List<Item> items) {
        super(n.getBaseName(), n.getBasePrice(), n);
        this.items = items;
    }

    /**
     * Generates the display name of the item
     */
    @Override
    public String generateDisplayName() {
        return this.getItemDescriptor().getBaseName();
    }

    /**
     * Calculates the TOTAL price of the item
     */
    @Override
    protected long calculatePrice() {
        long start = getBundlePrice();
        for(Item i: items){
            start += i.getDiscountedPrice();
        }
        return start;
    }

    public long getBundlePrice(){
        return getItemDescriptor().getBasePrice();
    }

    /**
     * Copies the item and its' contents
     */
    @Override
    public AbstractItem deepCopy() {
        return new ItemBundle(getItemDescriptor(), new ArrayList<>(items));
    }

    @Override
    public ItemBundleDescriptor getItemDescriptor(){
        return (ItemBundleDescriptor)super.getItemDescriptor();
    }

    /**
     * Get the items in this bundle
     * @return a list of items included in this bundle
     */
    public List<Item> getItems() {
        return items;
    }
}
