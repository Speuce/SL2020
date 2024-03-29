package main.java.lucia.client.content.menu.item.type;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import main.java.lucia.client.content.menu.io.ItemGson;
import main.java.lucia.client.content.menu.io.MenuJsonConstants;
import main.java.lucia.client.content.menu.item.AbstractItem;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.descriptor.ItemBundleDescriptor;
import main.java.lucia.client.content.menu.item.type.pizza.Pizza;
import main.java.lucia.client.content.order.discount.Discount;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
public class ItemBundle extends Item implements Iterable<Item>{

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

    public ItemBundle(int rowNum, String displayName, String name, long price, long discountedPrice,
                      ItemBundleDescriptor itemDescriptor, Set<Discount> appliedDiscounts, List<Item> items) {
        super(rowNum, displayName, name, price, discountedPrice, itemDescriptor, appliedDiscounts);
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

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }

    @Override
    public void addJsonProperties(JsonObject add){
        super.addJsonProperties(add);
        add.addProperty(MenuJsonConstants.TYPE_FIELD, MenuJsonConstants.BUNDLE_TYPE);
        //serialize the bundle items and add it
        JsonArray arr = new JsonArray();
        for(Item i: items){
            if(i instanceof SimpleItem){
                arr.add(ItemGson.ITEM_GSON.toJsonTree(i, SimpleItem.class));
            }else if(i instanceof ItemModifiable){
                arr.add(ItemGson.ITEM_GSON.toJsonTree(i, ItemModifiable.class));
            }else if(i instanceof Pizza){
                arr.add(ItemGson.ITEM_GSON.toJsonTree(i, Pizza.class));
            }
        }
        add.add("items", arr);
    }
}
