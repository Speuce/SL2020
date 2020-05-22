package main.java.lucia.client.content.menu.item.type;

import com.google.gson.JsonObject;
import main.java.lucia.client.content.menu.io.MenuJsonConstants;
import main.java.lucia.client.content.menu.item.AbstractItem;
import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.descriptor.Descriptor;
import main.java.lucia.client.content.menu.item.descriptor.SimpleItemDescriptor;
import main.java.lucia.client.content.order.discount.Discount;

import java.util.Set;

/**
 * Represents a Simple item, no modifications or substitutions
 * or special requests.
 * I.e coleslaw, beverages, etc
 */
public class SimpleItem extends Item {

    public SimpleItem(String name, long price, SimpleItemDescriptor desc) {
        super(name, price, desc);
    }

    public SimpleItem(int rowNum, String displayName, String name, long price, long discountedPrice, Descriptor itemDescriptor, Set<Discount> appliedDiscounts) {
        super(rowNum, displayName, name, price, discountedPrice, itemDescriptor, appliedDiscounts);
    }

    /**
     * Generates the display name of the item
     */
    @Override
    public String generateDisplayName() {
        return this.getName();
    }

    /**
     * Calculates the normal price of the item (excluding any discounts)
     */
    @Override
    public long calculatePrice() {
        return this.getPrice();
    }

    @Override
    public void addJsonProperties(JsonObject add){
        super.addJsonProperties(add);
        add.addProperty(MenuJsonConstants.TYPE_FIELD, MenuJsonConstants.SIMPLE_TYPE);
    }

    /**
     * Copies the item and its' contents
     */
    @Override
    public AbstractItem deepCopy() {
        return null;
    }
}
