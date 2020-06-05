package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner;

import main.java.lucia.client.content.menu.item.Item;
import main.java.lucia.client.content.menu.item.descriptor.AddonDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.Descriptor;
import main.java.lucia.client.content.menu.item.descriptor.ItemModifiableDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.SimpleItemDescriptor;
import main.java.lucia.client.content.menu.item.type.ItemModifiable;
import main.java.lucia.client.content.menu.item.type.SimpleItem;

import java.util.ArrayList;

/**
 * Instance for the dinner order listeners
 *
 * Will be updated to the 'current' item whenever an item is clicked on the GUI
 */
public class DinnerOrderManager {

    /**
     * ADDONS instance
     */
    public ArrayList<AddonDescriptor> addons;

    /**
     * CURRENT ITEM instance
     */
    public Descriptor currentItem;

    private ItemModifiable itemModifiable; // created order item when 'Make' is clicked
    private SimpleItem simpleItem; // created order item when 'Make' is clicked

    private static DinnerOrderManager dinnerOrderInstance;

    private DinnerOrderManager() {
        addons = new ArrayList<>();
        currentItem = null;
    }

    /**
     * Creates the instance if there is none, gives the instance if there is one
     *
     * @return DinnerOrderManager instance
     */
    public static DinnerOrderManager getDinnerOrderInstance() {
        if(dinnerOrderInstance == null)
            dinnerOrderInstance = new DinnerOrderManager();
        return dinnerOrderInstance;
    }

    /**
     * When 'Make' is clicked, creates the item for the Order
     *
     */
    public Item makeItem() {
        if(currentItem instanceof ItemModifiableDescriptor) {
            ItemModifiableDescriptor itemDescriptor = (ItemModifiableDescriptor) currentItem;
            itemModifiable = itemDescriptor.getAsItem();
            setItemModifiableAddons();
            System.out.println("added " + itemModifiable.getName());
            return itemModifiable;
        }
        else {
            SimpleItemDescriptor simpleItemDescriptor = (SimpleItemDescriptor)currentItem;
            simpleItem = (SimpleItem)simpleItemDescriptor.getAsItem();
            System.out.println("added " + simpleItem.getName());
            return simpleItem;
        }
    }

    /**
     * Adds Addons to the Item if there are any,
     *
     * For purpose if when 'Make' is clicked
     */
    private void setItemModifiableAddons() {
        if(!addons.isEmpty()) {
            for (AddonDescriptor addon : addons) {
                itemModifiable.addAddon(addon.getAsItem());
            }
        }
    }
}
